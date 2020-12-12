package com.cedricziel.idea.concourse.psi

import com.intellij.psi.PsiPolyVariantReference
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.containers.ContainerUtil
import junit.framework.TestCase
import org.jetbrains.yaml.psi.YAMLScalar

class ResourceReferenceContributorTest : BasePlatformTestCase() {

    /**
     * Return absolute path to the test data. Not intended to be overridden in tests written as part of the IntelliJ IDEA codebase;
     * must be overridden in plugins which use the test framework.
     *
     * @see .getBasePath
     */
    override fun getTestDataPath(): String {
        return "src/test/testData";
    }

    fun testCanResolve() {
        doTest("pipeline.yml", "booklit")
    }

    private fun doTest(filePath: String, expectedResolve: String) {
        myFixture.apply {
            configureByFile(filePath)

            val reference = getReferenceAtCaretPosition() as PsiPolyVariantReference?
            TestCase.assertNotNull(reference)

            val resolveResults = reference?.multiResolve(true)
            ContainerUtil.map(resolveResults) {
                it?.element.let { element ->
                    TestCase.assertNotNull(element)
                    UsefulTestCase.assertInstanceOf(element, YAMLScalar::class.java)
                    UsefulTestCase.assertEquals(expectedResolve, element?.text)
                }
            }
        }
    }
}
