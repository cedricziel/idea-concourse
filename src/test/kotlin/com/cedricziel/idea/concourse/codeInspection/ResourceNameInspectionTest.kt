package com.cedricziel.idea.concourse.codeInspection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ResourceNameInspectionTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/codeInspection"
    }

    fun testHighlighting() {
        myFixture.enableInspections(ResourceNameInspection())
        val file = myFixture.copyFileToProject("pipeline_invalid_resource.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }
}
