package com.cedricziel.idea.concourse.codeInspection

import com.cedricziel.idea.concourse.BaseConcoursePluginTest

class DeprecatedResourceTypeInspectionTest : BaseConcoursePluginTest() {
    override fun getTestDataPath(): String {
        return super.getTestDataPath() + "/codeInspection"
    }

    fun testHighlighting() {
        myFixture.enableInspections(DeprecatedResourceTypeInspection())
        val file = myFixture.copyFileToProject("deprecated_resource_type.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }
}
