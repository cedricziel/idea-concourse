package com.cedricziel.idea.concourse.lang

import com.cedricziel.idea.concourse.ConcourseIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.yaml.YAMLLanguage
import javax.swing.Icon

object ConcourseTaskFileType : LanguageFileType(YAMLLanguage.INSTANCE, true) {
    /**
     * Returns the name of the file type. The name must be unique among all file types registered in the system.
     *
     * @return The file type name.
     */
    override fun getName(): String {
        return "ConcourseTask"
    }

    /**
     * Returns the user-readable description of the file type.
     *
     * @return The file type description.
     */
    override fun getDescription(): String {
        return "Concourse CI Task"
    }

    /**
     * Returns the default extension for files of the type.
     *
     * @return The extension, *not* including the leading '.'.
     */
    override fun getDefaultExtension(): String {
        return ""
    }

    /**
     * Returns the icon used for showing files of the type.
     *
     * @return The icon instance, or `null` if no icon should be shown.
     */
    override fun getIcon(): Icon {
        return ConcourseIcons.CONCOURSE_TASK
    }
}
