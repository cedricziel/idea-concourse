package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.ConcourseIcons.CONCOURSE_TASK
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class CreateTaskFromTemplateAction : CreateFileFromTemplateAction(
    ConcourseBundle.message("action.create_file.task"),
    ConcourseBundle.message("action.create_file.task.description"),
    CONCOURSE_TASK
) {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.addKind(
            ConcourseBundle.message("action.create_file.task"),
            CONCOURSE_TASK,
            ConcourseBundle.message("action.create_file.task")
        )
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
        return ConcourseBundle.message("action.create_file.task")
    }
}
