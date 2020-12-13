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

    /**
     * Returns the array of String, [PsiElement] and/or [com.intellij.codeInsight.lookup.LookupElement]
     * instances representing all identifiers that are visible at the location of the reference. The contents
     * of the returned array is used to build the lookup list for basic code completion. (The list
     * of visible identifiers may not be filtered by the completion prefix string - the
     * filtering is performed later by the IDE.)
     *
     *
     * This method is default since 2018.3.
     *
     * @return the array of available identifiers.
     */
    override fun getVariants(): Array<Any> {

        return ConcourseUtils.findResourcesInFile(myElement.containingFile)
            .map {
                LookupElementBuilder.createWithSmartPointer(it.key, it.value.element!!)
                    .withIcon(ConcourseIcons.RESOURCE)
            }
            .toTypedArray()
    }
}
