package com.cedricziel.idea.concourse.psi

import com.intellij.psi.PsiPolyVariantReference
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.psi.YAMLScalar

class ConcourseReferenceContributorTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/psi"
    }

    fun testCanResolveResource() {
        doTest("pipeline.yml", "booklit")
    }

    fun testCanResolveResourceTypes() {
        doTest("resource_types.yml", "rss")
    }

    fun testCanResolveOutputs() {
        doTest("pipeline_output.yml", "modules-8.0-i386")
    }

    private fun doTest(filePath: String, expectedResolve: String) {
        myFixture.apply {
            val file = copyFileToProject(filePath, "pipeline.yml")
            configureFromExistingVirtualFile(file)

            val reference = getReferenceAtCaretPosition() as PsiPolyVariantReference?
            TestCase.assertNotNull(reference)

            //ContainerUtil.map(resolveResults) {
            reference?.multiResolve(true)?.map {
                it?.element.let { element ->
                    TestCase.assertNotNull(element)
                    UsefulTestCase.assertInstanceOf(element, YAMLScalar::class.java)
                    UsefulTestCase.assertEquals(expectedResolve, element?.text)
                }
            }
        }
    }
}
