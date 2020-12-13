package com.cedricziel.idea.concourse.codeInspection

import com.cedricziel.idea.concourse.ConcourseBundle
import com.cedricziel.idea.concourse.ConcoursePatterns
import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar
import org.jetbrains.yaml.psi.YamlPsiElementVisitor

class ResourceNameInspection : LocalInspectionTool() {
    /**
     * Override the method to provide your own inspection visitor.
     * Created visitor must not be recursive (e.g. it must not inherit [PsiRecursiveElementVisitor])
     * since it will be fed with every element in the file anyway.
     * Visitor created must be thread-safe since it might be called on several elements concurrently.
     *
     * @param holder     where visitor will register problems found.
     * @param isOnTheFly true if inspection was run in non-batch mode
     * @return not-null visitor for this inspection.
     * @see PsiRecursiveVisitor
     */
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        if (!ConcourseUtils.isPipelineFile(holder.file)) {
            return object : YamlPsiElementVisitor() {}
        }

        return object : YamlPsiElementVisitor() {
            override fun visitScalar(scalar: YAMLScalar) {
                if (!ConcoursePatterns.resourceStepValue().accepts(scalar)) {
                    super.visitScalar(scalar)
                    return
                }

                val parent = scalar.parent as YAMLKeyValue
                if (!ConcourseUtils.resourceSteps().contains(parent.keyText)) {
                    super.visitScalar(scalar)
                    return
                }

                val resourceName = parent.valueText
                if (!ConcourseUtils.findResourcesNamesInFile(scalar.containingFile).contains(resourceName)) {
                    holder.registerProblem(scalar, ConcourseBundle.message("inspection.resource.name.invalid"), ProblemHighlightType.WARNING)
                }

                super.visitScalar(scalar)
            }
        }
    }
}
