package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.cedricziel.idea.concourse.psi.visitor.ResourceTypesYamlVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.SmartPsiElementPointer
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.YAMLUtil
import org.jetbrains.yaml.psi.YAMLPsiElement

object ConcourseUtils {
    val CORE_RESOURCE_TYPES = listOf(
        "bosh-io-release",
        "bosh-io-stemcell",
        "concourse-pipeline",
        "datadog-event",
        "docker-image",
        "git",
        "github-release",
        "hg",
        "mock",
        "pool",
        "registry-image",
        "s3",
        "semver",
        "time",
        "tracker"
    )

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

    fun findResourceTypeNamesInFile(containingFile: PsiFile?): List<String> {
        if (containingFile == null) {
            return arrayListOf()
        }

        val visitor = ResourceTypesYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.resourceTypes.keys.toList()
    }

    fun findResourceTypesInFile(containingFile: PsiFile?): MutableMap<String, @NotNull SmartPsiElementPointer<YAMLPsiElement>> {
        if (containingFile == null) {
            return mutableMapOf()
        }

        val visitor = ResourceTypesYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.resourceTypes
    }

    fun isPipelineFile(file: PsiFile): Boolean {
        return file.name == "pipeline.yml"
    }

    fun isInResources(element: YAMLPsiElement): Boolean {
        return YAMLUtil.getConfigFullName(element).startsWith("resources[")
    }

    fun isInResourceTypes(element: YAMLPsiElement): Boolean {
        return YAMLUtil.getConfigFullName(element).startsWith("resource_types[")
    }

    fun isLocalOrCoreResourceType(file: PsiFile, resourceTypeName: String): Boolean {
        return CORE_RESOURCE_TYPES.contains(resourceTypeName) || findResourceTypeNamesInFile(file).contains(resourceTypeName)
    }
}
