package com.cedricziel.idea.concourse

import org.jetbrains.yaml.schema.YamlJsonSchemaHighlightingInspection

class ConcoursePipelineSchemaProviderTest : BaseConcoursePluginTest() {
    fun testValid() {
        myFixture.enableInspections(YamlJsonSchemaHighlightingInspection())
        myFixture.configureByFile("pipeline.yml")
        myFixture.checkHighlighting(true, false, false)
    }

    /**
    fun testInvalidProperty() {
        myFixture.enableInspections(YamlJsonSchemaHighlightingInspection())
        val file = myFixture.copyFileToProject("pipeline_schema_invalid.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting(true, false, false)
    }
    */
}
