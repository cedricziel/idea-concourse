package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.intellij.psi.PsiFile

class ConcourseUtils {
    companion object {
        fun resourceSteps(): List<String> {
            return listOf("get", "put")
        }

        fun findResourcesInFile(containingFile: PsiFile?): List<String> {
            if (containingFile == null) {
                return arrayListOf()
            }

        val visitor = ResourceNamesYamlVisitor()
        visitor.visitFile(containingFile)

            return visitor.resources.keys.toList()
        }

        fun isPipelineFile(file: PsiFile): Boolean {
            return file.name == "pipeline.yml"
        }
    }
}
