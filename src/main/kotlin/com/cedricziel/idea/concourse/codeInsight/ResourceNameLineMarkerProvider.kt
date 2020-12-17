package com.cedricziel.idea.concourse.codeInsight

import com.cedricziel.idea.concourse.ConcourseBundle
import com.cedricziel.idea.concourse.ConcourseIcons
import com.cedricziel.idea.concourse.ConcoursePatterns
import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar

class ResourceNameLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (!ConcourseUtils.isPipelineFile(element.containingFile) || !ConcoursePatterns.resourceStepValue().accepts(element)) {
            return
        }

        if (!ConcourseUtils.resourceSteps().contains((element.parent as YAMLKeyValue).keyText)) {
            return
        }

        val findResourcesInFile = ConcourseUtils.findResourcesInFile(element.containingFile)
        val concourseResourceName = (element as YAMLScalar).textValue
        if (findResourcesInFile.isNotEmpty() && findResourcesInFile.containsKey(concourseResourceName)) {
            result.add(
                NavigationGutterIconBuilder.create(ConcourseIcons.RESOURCE)
                    .setTooltipText(ConcourseBundle.message("gutter.goto.resource"))
                    .setTarget(findResourcesInFile[concourseResourceName]!!.element)
                    .createLineMarkerInfo(element.firstChild)
            )
        }
    }
}
