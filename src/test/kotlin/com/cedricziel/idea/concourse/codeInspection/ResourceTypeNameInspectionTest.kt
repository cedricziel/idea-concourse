package com.cedricziel.idea.concourse.codeInspection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ResourceTypeNameInspectionTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/codeInspection"
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
