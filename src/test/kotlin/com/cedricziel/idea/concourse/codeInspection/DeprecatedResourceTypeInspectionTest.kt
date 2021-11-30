package com.cedricziel.idea.concourse.codeInspection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DeprecatedResourceTypeInspectionTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/codeInspection"
    }

    fun testHighlighting() {
        myFixture.enableInspections(DeprecatedResourceTypeInspection())
        val file = myFixture.copyFileToProject("deprecated_resource_type.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }
}
