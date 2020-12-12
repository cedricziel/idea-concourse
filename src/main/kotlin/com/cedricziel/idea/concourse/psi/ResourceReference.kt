package com.cedricziel.idea.concourse.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar
import org.jetbrains.yaml.psi.YAMLValue
import org.jetbrains.yaml.psi.YamlRecursivePsiElementVisitor

class ResourceReference(psiElement: @NotNull YAMLScalar) : PsiPolyVariantReferenceBase<YAMLScalar>(psiElement) {
    /**
     * Returns the results of resolving the reference.
     *
     * @param incompleteCode if true, the code in the context of which the reference is
     * being resolved is considered incomplete, and the method may return additional
     * invalid results.
     *
     * @return the array of results for resolving the reference.
     */
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val yamlFile = element.containingFile as YAMLFile
        val items = arrayListOf<YAMLValue>()

        yamlFile.accept(object : YamlRecursivePsiElementVisitor() {
            override fun visitKeyValue(keyValue: YAMLKeyValue) {
                if (keyValue.key ==  null) {
                    super.visitKeyValue(keyValue)

                    return
                }

                if (keyValue.keyText != "name") {
                    super.visitKeyValue(keyValue)

                    return
                }

                if (keyValue.valueText == myElement.textValue) {
                    keyValue.value?.let { items.add(it) }
                }

                super.visitKeyValue(keyValue)
            }
        })

        if (items.size > 0) {
            return PsiElementResolveResult.createResults(items)
        }

        return ResolveResult.EMPTY_ARRAY
    }
}
