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

class ResourceTypeNameInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        if (!ConcourseUtils.isPipelineFile(holder.file)) {
            return object : YamlPsiElementVisitor() {}
        }

        return object : YamlPsiElementVisitor() {
            override fun visitScalar(scalar: YAMLScalar) {
                if (!ConcoursePatterns.resourceStepValue().accepts(scalar) || !ConcourseUtils.isInResources(scalar)) {
                    super.visitScalar(scalar)
                    return
                }

                val parent = scalar.parent as YAMLKeyValue
                if (parent.keyText != "type") {
                    super.visitScalar(scalar)
                    return
                }

                val resourceTypeName = parent.valueText
                if (!ConcourseUtils.isLocalOrCoreResourceType(scalar.containingFile, resourceTypeName)) {
                    holder.registerProblem(
                        scalar,
                        ConcourseBundle.message("inspection.resource_type.name.invalid"),
                        ProblemHighlightType.WARNING
                    )
                }

                super.visitScalar(scalar)
            }
        }
    }
}
