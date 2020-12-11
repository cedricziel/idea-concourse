package com.cedricziel.idea.concourse.psi

import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.containers.ContainerUtil
import junit.framework.TestCase

class TaskNameReferenceContributorTest : BasePlatformTestCase() {

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
        doTest("pipeline.yml")
    }

    private fun doTest(filePath: String, vararg expectedResolve: String) {
        myFixture.apply {
            configureByFile(filePath)

            val reference = getReferenceAtCaretPosition() as PsiPolyVariantReference?
            TestCase.assertNotNull(reference)

            val rootFile = file.containingDirectory.virtualFile
            val resolveResults = reference?.multiResolve(true)
            val actualResolve = ContainerUtil.map(resolveResults) {
                it?.element.let { element ->
                    TestCase.assertNotNull(element)
                    UsefulTestCase.assertInstanceOf(element, PsiFileSystemItem::class.java)
                    val fileSystemItem = element as PsiFileSystemItem?
                    fileSystemItem?.let { file -> VfsUtilCore.getRelativePath(file.virtualFile, rootFile, '/') }
                }
            }

            UsefulTestCase.assertContainsElements(actualResolve, *expectedResolve)
        }
    }
}
