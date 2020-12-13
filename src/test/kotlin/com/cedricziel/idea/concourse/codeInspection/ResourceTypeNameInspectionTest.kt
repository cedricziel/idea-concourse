package com.cedricziel.idea.concourse.codeInspection

import com.cedricziel.idea.concourse.BaseConcoursePluginTest

class ResourceTypeNameInspectionTest : BaseConcoursePluginTest() {
    override fun getTestDataPath(): String {
        return super.getTestDataPath() + "/codeInspection"
    }

    fun testInvalidHighlighting() {
        myFixture.enableInspections(ResourceTypeNameInspection())
        val file = myFixture.copyFileToProject("pipeline_invalid_resource_type.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }

    fun testValidHighlighting() {
        myFixture.enableInspections(ResourceTypeNameInspection())
        val file = myFixture.copyFileToProject("pipeline_valid_resource_type.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }
}
