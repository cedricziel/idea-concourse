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

class ResourceTypeLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        if (!ConcourseUtils.isPipelineFile(element.containingFile) || !ConcoursePatterns.resourceStepValue().accepts(element)) {
            return
        }

        if ((element.parent as YAMLKeyValue).keyText != "type") {
            return
        }

        val resourceTypes = ConcourseUtils.findResourceTypesInFile(element.containingFile)
        val concourseResourceName = (element as YAMLScalar).textValue
        if (resourceTypes.isNotEmpty() && resourceTypes.containsKey(concourseResourceName)) {
            result.add(
                NavigationGutterIconBuilder.create(ConcourseIcons.RESOURCE)
                    .setTooltipText(ConcourseBundle.message("gutter.goto.resource_type"))
                    .setTarget(resourceTypes[concourseResourceName]!!.element)
                    .createLineMarkerInfo(element.firstChild)
            )
        }
    }
}
