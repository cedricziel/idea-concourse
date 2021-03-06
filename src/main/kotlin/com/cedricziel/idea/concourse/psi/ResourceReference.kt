package com.cedricziel.idea.concourse.psi

import com.cedricziel.idea.concourse.ConcourseIcons
import com.cedricziel.idea.concourse.ConcourseUtils
import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLScalar

class ResourceReference(psiElement: @NotNull YAMLScalar) : PsiPolyVariantReferenceBase<YAMLScalar>(psiElement) {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val yamlFile = element.containingFile as YAMLFile

        val visitor = ResourceNamesYamlVisitor()
        yamlFile.accept(visitor)

        if (visitor.resources.isNotEmpty() && visitor.resources.containsKey(myElement.textValue)) {
            return PsiElementResolveResult.createResults(listOf(visitor.resources[myElement.textValue]!!.element))
        }

        return ResolveResult.EMPTY_ARRAY
    }

    override fun getVariants(): Array<Any> {

        return ConcourseUtils.findResourcesInFile(myElement.containingFile)
            .map {
                LookupElementBuilder.createWithSmartPointer(it.key, it.value.element!!)
                    .withIcon(ConcourseIcons.RESOURCE)
            }
            .toTypedArray()
    }
}
