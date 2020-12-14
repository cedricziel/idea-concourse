package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.psi.visitor.InputsYamlVisitor
import com.cedricziel.idea.concourse.psi.visitor.OutputsYamlVisitor
import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.cedricziel.idea.concourse.psi.visitor.ResourceTypesYamlVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.SmartPsiElementPointer
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.YAMLUtil
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLPsiElement

object ConcourseUtils {
    val DEPRECATED_RESOURCE_TYPES = mapOf(
        Pair("docker-image", "registry-image")
    )

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

    fun findInputsInFile(containingFile: PsiFile?): MutableMap<String, @NotNull SmartPsiElementPointer<YAMLPsiElement>> {
        if (containingFile == null) {
            return mutableMapOf()
        }

        val visitor = InputsYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.inputs
    }

    fun findOutputsInFile(containingFile: PsiFile?): MutableMap<String, @NotNull SmartPsiElementPointer<YAMLPsiElement>> {
        if (containingFile == null) {
            return mutableMapOf()
        }

        val visitor = OutputsYamlVisitor()
        visitor.visitFile(containingFile)

        return visitor.outputs
    }

    fun isPipelineFile(file: PsiFile): Boolean {
        if (file !is YAMLFile) {
            return false
        }

        if (file.name == "pipeline.yml") {
            return true
        }

        val resourceTypes = YAMLUtil.getQualifiedKeyInFile(file, "resource_types")
        val resources = YAMLUtil.getQualifiedKeyInFile(file, "resources")
        val plan = YAMLUtil.getQualifiedKeyInFile(file, "plan")

        return resourceTypes != null || resources != null || plan != null
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

    fun isInOutputMappings(element: YAMLPsiElement): Boolean {
        return YAMLUtil.getConfigFullName(element).contains("output_mapping")
    }

    fun isInInputMappings(element: YAMLPsiElement): Boolean {
        return YAMLUtil.getConfigFullName(element).contains("input_mapping")
    }
}
