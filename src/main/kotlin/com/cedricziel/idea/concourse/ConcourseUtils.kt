package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.SmartPsiElementPointer
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLPsiElement

object ConcourseUtils {
    fun resourceSteps(): List<String> {
        return listOf("get", "put")
    }

    fun findResourcesNamesInFile(containingFile: PsiFile?): List<String> {
        if (containingFile == null) {
            return arrayListOf()
        }

        val visitor = ResourceNamesYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.resources.keys.toList()
    }

    fun findResourcesInFile(containingFile: PsiFile?): MutableMap<String, @NotNull SmartPsiElementPointer<YAMLPsiElement>> {
        if (containingFile == null) {
            return mutableMapOf()
        }

        val visitor = ResourceNamesYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.resources
    }

    fun isPipelineFile(file: PsiFile): Boolean {
        return file.name == "pipeline.yml"
    }
}
