package com.cedricziel.idea.concourse

import com.intellij.testFramework.fixtures.BasePlatformTestCase

open class BaseConcoursePluginTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData";
    }
}
