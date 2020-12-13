package com.cedricziel.idea.concourse.psi

import com.cedricziel.idea.concourse.ConcourseIcons
import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLScalar

class InputReference(psiElement: @NotNull YAMLScalar) : PsiPolyVariantReferenceBase<YAMLScalar>(psiElement) {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val outputsInFile = ConcourseUtils.findOutputsInFile(element.containingFile)

        if (outputsInFile.isNotEmpty() && outputsInFile.containsKey(myElement.textValue)) {
            return PsiElementResolveResult.createResults(listOf(outputsInFile[myElement.textValue]!!.element))
        }

        return ResolveResult.EMPTY_ARRAY
    }

    override fun getVariants(): Array<Any> {

        return ConcourseUtils.findOutputsInFile(myElement.containingFile)
            .map {
                LookupElementBuilder.createWithSmartPointer(it.key, it.value.element!!)
                    .withIcon(ConcourseIcons.INPUT)
            }
            .toTypedArray()
    }
}
