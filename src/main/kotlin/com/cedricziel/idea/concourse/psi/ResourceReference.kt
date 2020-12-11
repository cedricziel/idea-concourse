package com.cedricziel.idea.concourse.psi

import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLValue

class ResourceReference(psiElement: @NotNull YAMLValue) : PsiPolyVariantReferenceBase<YAMLValue>(psiElement) {
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
        return ResolveResult.EMPTY_ARRAY
    }
}
