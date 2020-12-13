package com.cedricziel.idea.concourse

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.YAMLElementTypes
import org.jetbrains.yaml.psi.YAMLScalar

object ConcoursePatterns {
    fun resourceStepValue(): @NotNull ElementPattern<PsiElement> {
        return PlatformPatterns.or(
            PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_TEXT_VALUE.get()).withParent(resourceStep()),
            PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_PLAIN_VALUE.get()).withParent(resourceStep()),
            PlatformPatterns.psiElement(YAMLElementTypes::SCALAR_QUOTED_STRING.get()).withParent(resourceStep()),
        )
    }

    private fun resourceStep(): @NotNull ElementPattern<PsiElement> {
        return PlatformPatterns.psiElement(YAMLElementTypes.KEY_VALUE_PAIR)
            .withFirstChild(
                PlatformPatterns.psiElement(YAMLScalar::class.java)
            )
    }
}
