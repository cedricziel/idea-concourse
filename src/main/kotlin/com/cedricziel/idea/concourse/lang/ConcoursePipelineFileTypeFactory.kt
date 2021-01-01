package com.cedricziel.idea.concourse.lang

import com.intellij.openapi.fileTypes.ExactFileNameMatcher
import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory
import com.intellij.openapi.fileTypes.WildcardFileNameMatcher

@Suppress("DEPRECATION")
class ConcoursePipelineFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(ConcoursePipelineFileType, ExactFileNameMatcher("pipeline.yml"))
        consumer.consume(ConcoursePipelineFileType, ExactFileNameMatcher("pipeline.yaml"))
        consumer.consume(ConcoursePipelineFileType, WildcardFileNameMatcher("pipeline_*.yml"))
        consumer.consume(ConcoursePipelineFileType, WildcardFileNameMatcher("pipeline_*.yaml"))
        consumer.consume(ConcoursePipelineFileType, WildcardFileNameMatcher("**/pipeline/*.yml"))
        consumer.consume(ConcoursePipelineFileType, WildcardFileNameMatcher("**/pipeline/*.yaml"))
    }
}
