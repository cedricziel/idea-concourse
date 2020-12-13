package com.cedricziel.idea.concourse.psi

import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLScalar

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

        val visitor = ResourceNamesYamlVisitor()
        yamlFile.accept(visitor)

        if (visitor.resources.isNotEmpty() && visitor.resources.containsKey(myElement.textValue)) {
            return PsiElementResolveResult.createResults(listOf(visitor.resources[myElement.textValue]!!.element))
        }

        return ResolveResult.EMPTY_ARRAY
    }
}
