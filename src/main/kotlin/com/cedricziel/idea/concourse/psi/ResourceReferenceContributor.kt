package com.cedricziel.idea.concourse.psi

import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.util.ProcessingContext
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.YAMLElementTypes
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar
import org.jetbrains.yaml.psi.YAMLValue

class ResourceReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.or(
                PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_TEXT_VALUE.get()),
                PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_PLAIN_VALUE.get()),
                PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_QUOTED_STRING.get()),
            ),
            NameReferenceProvider
        )
    }

    object NameReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
                return PsiReference.EMPTY_ARRAY
            }

            if (element.parent !is YAMLKeyValue) {
                return PsiReference.EMPTY_ARRAY
            }

            if (element is YAMLValue && ConcourseUtils.resourceSteps().contains((element.parent as YAMLKeyValue).keyText)) {
                return arrayOf(ResourceReference(element as @NotNull YAMLScalar))
            }

            return PsiReference.EMPTY_ARRAY
        }
    }
}
