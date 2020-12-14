package com.cedricziel.idea.concourse

import com.intellij.psi.PsiManager
import junit.framework.TestCase

class ConcourseUtilsTest : BaseConcoursePluginTest() {
    fun testCanFindOutputs() {
        val file = myFixture.copyFileToProject("pipeline.yml", "pipeline.yml")
        val psiFile = PsiManager.getInstance(myFixture.project).findFile(file)

        TestCase.assertTrue(ConcourseUtils.findOutputsInFile(psiFile).containsKey("modules-8.0-i386"))
    }

    fun testCanFindInputs() {
        val file = myFixture.copyFileToProject("pipeline.yml", "pipeline.yml")
        val psiFile = PsiManager.getInstance(myFixture.project).findFile(file)

        TestCase.assertTrue(ConcourseUtils.findInputsInFile(psiFile).containsKey("modules-8.0-i386"))
    }

    fun testCanRecognizePipelineFiles_byName() {
        val file = myFixture.copyFileToProject("pipeline.yml", "pipeline_out.yml")
        val psiFile = PsiManager.getInstance(myFixture.project).findFile(file)

        TestCase.assertTrue(ConcourseUtils.isPipelineFile(psiFile!!))
    }

    fun testCanRecognizePipelineFiles_byKeys() {
        val file = myFixture.copyFileToProject("pipeline.yml", "foo.yml")
        val psiFile = PsiManager.getInstance(myFixture.project).findFile(file)

        TestCase.assertTrue(ConcourseUtils.isPipelineFile(psiFile!!))
    }
}
