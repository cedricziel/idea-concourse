package com.cedricziel.idea.concourse.lang

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory
import com.intellij.openapi.fileTypes.WildcardFileNameMatcher

@Suppress("DEPRECATION")
class ConcourseTaskFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(ConcourseTaskFileType, WildcardFileNameMatcher("*task.yml"))
        consumer.consume(ConcourseTaskFileType, WildcardFileNameMatcher("*task.yaml"))
        consumer.consume(ConcourseTaskFileType, WildcardFileNameMatcher("**/tasks/*.yml"))
        consumer.consume(ConcourseTaskFileType, WildcardFileNameMatcher("**/tasks/*.yaml"))
    }
}
