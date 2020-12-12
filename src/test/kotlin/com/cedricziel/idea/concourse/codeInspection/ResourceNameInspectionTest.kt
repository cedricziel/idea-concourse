package com.cedricziel.idea.concourse.codeInspection

import com.cedricziel.idea.concourse.BaseConcoursePluginTest

class ResourceNameInspectionTest : BaseConcoursePluginTest() {
    override fun getTestDataPath(): String {
        return super.getTestDataPath() + "/codeInspection"
    }

    fun testHighlighting() {
        myFixture.enableInspections(ResourceNameInspection())
        val file = myFixture.copyFileToProject("pipeline_invalid_resource.yml", "pipeline.yml")
        myFixture.configureFromExistingVirtualFile(file)
        myFixture.checkHighlighting()
    }
}
