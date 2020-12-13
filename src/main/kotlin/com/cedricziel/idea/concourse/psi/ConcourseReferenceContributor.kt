package com.cedricziel.idea.concourse.psi

import com.cedricziel.idea.concourse.ConcoursePatterns
import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.util.ProcessingContext
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar
import org.jetbrains.yaml.psi.YAMLValue

class ConcourseReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            ConcoursePatterns.resourceStepValue(),
            ResourceNameReferenceProvider
        )
        registrar.registerReferenceProvider(
            ConcoursePatterns.resourceStepValue(),
            ResourceTypeReferenceProvider
        )
        registrar.registerReferenceProvider(
            ConcoursePatterns.resourceStepValue(),
            InputReferenceProvider
        )
        registrar.registerReferenceProvider(
            ConcoursePatterns.resourceStepValue(),
            OutputReferenceProvider
        )
    }

    object ResourceNameReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
                return PsiReference.EMPTY_ARRAY
            }

            if (element is YAMLValue && ConcourseUtils.resourceSteps().contains((element.parent as YAMLKeyValue).keyText)) {
                return arrayOf(ResourceReference(element as @NotNull YAMLScalar))
            }

            return PsiReference.EMPTY_ARRAY
        }
    }

    object ResourceTypeReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
                return PsiReference.EMPTY_ARRAY
            }

            if (element is YAMLValue && (element.parent as YAMLKeyValue).keyText == "type") {
                return arrayOf(ResourceTypeReference(element as @NotNull YAMLScalar))
            }

            return PsiReference.EMPTY_ARRAY
        }
    }

    object InputReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
                return PsiReference.EMPTY_ARRAY
            }

            val relevantParent = element.parent?.parent?.parent
            if (element is YAMLValue && (relevantParent is YAMLKeyValue) && relevantParent.keyText == "input_mapping") {
                return arrayOf(InputReference(element as @NotNull YAMLScalar))
            }

            return PsiReference.EMPTY_ARRAY
        }
    }

    object OutputReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
                return PsiReference.EMPTY_ARRAY
            }

            val relevantParent = element.parent?.parent?.parent
            if (element is YAMLValue && (relevantParent is YAMLKeyValue) && relevantParent.keyText == "output_mapping") {
                return arrayOf(OutputReference(element as @NotNull YAMLScalar))
            }

            return PsiReference.EMPTY_ARRAY
        }
    }
}
