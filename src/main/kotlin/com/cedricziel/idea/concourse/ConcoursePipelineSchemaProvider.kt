package com.cedricziel.idea.concourse

import com.cedricziel.idea.concourse.lang.ConcoursePipelineFileType
import com.cedricziel.idea.concourse.lang.ConcourseTaskFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType
import java.net.URL

class ConcoursePipelineSchemaProvider : JsonSchemaProviderFactory {
    override fun getProviders(project: Project): List<JsonSchemaFileProvider> {
        return listOf(ConcoursePipelineSchemaProvider, ConcourseTaskSchemaProvider)
    }

    object ConcoursePipelineSchemaProvider : JsonSchemaFileProvider {
        override fun isAvailable(file: VirtualFile): Boolean {
            return file.fileType is ConcoursePipelineFileType
        }

        override fun getName(): String {
            return "Concourse CI pipeline"
        }

        override fun getSchemaFile(): VirtualFile? {
            val url: URL = ResourceUtil.getResource(javaClass, "schema", "pipeline.json")

            return VfsUtil.findFileByURL(url)
        }

        override fun getSchemaType(): SchemaType {
            return SchemaType.embeddedSchema
        }
    }

    object ConcourseTaskSchemaProvider : JsonSchemaFileProvider {
        override fun isAvailable(file: VirtualFile): Boolean {
            return file.fileType is ConcourseTaskFileType
        }

        override fun getName(): String {
            return "Concourse CI task definition"
        }

        override fun getSchemaFile(): VirtualFile? {
            val url: URL = ResourceUtil.getResource(javaClass, "schema", "task.json")

            return VfsUtil.findFileByURL(url)
        }

        override fun getSchemaType(): SchemaType {
            return SchemaType.embeddedSchema
        }
    }
}
