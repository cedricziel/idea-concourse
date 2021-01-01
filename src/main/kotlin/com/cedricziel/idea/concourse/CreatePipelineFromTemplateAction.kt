package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.ConcourseIcons.CONCOURSE
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class CreatePipelineFromTemplateAction : CreateFileFromTemplateAction(
    ConcourseBundle.message("action.create_file.pipeline"),
    ConcourseBundle.message("action.create_file.pipeline.description"),
    CONCOURSE
) {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.addKind(
            ConcourseBundle.message("action.create_file.pipeline"),
            CONCOURSE,
            ConcourseBundle.message("action.create_file.pipeline")
        )
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
        return ConcourseBundle.message("action.create_file.pipeline")
    }
}
