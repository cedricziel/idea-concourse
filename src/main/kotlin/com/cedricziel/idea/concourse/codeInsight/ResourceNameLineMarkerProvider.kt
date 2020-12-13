package com.cedricziel.idea.concourse.codeInsight

import com.cedricziel.idea.concourse.ConcourseBundle
import com.cedricziel.idea.concourse.ConcoursePatterns
import com.cedricziel.idea.concourse.ConcourseUtils
import com.cedricziel.idea.concourse.psi.visitor.ResourceNamesYamlVisitor
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar

class ResourceNameLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        if (!ConcourseUtils.isPipelineFile(element.containingFile)) {
            return
        }

        if (!ConcoursePatterns.resourceStepValue().accepts(element)) {
            return
        }

        if (!ConcourseUtils.resourceSteps().contains((element.parent as YAMLKeyValue).keyText)) {
            return
        }

        val visitor = ResourceNamesYamlVisitor()
        element.containingFile.accept(visitor)

        val concourseResourceName = (element as YAMLScalar).textValue
        if (visitor.resources.isNotEmpty() && visitor.resources.containsKey(concourseResourceName)) {
            result.add(
                NavigationGutterIconBuilder.create(AllIcons.FileTypes.Yaml)
                    .setTooltipText(ConcourseBundle.message("gutter.goto.resource"))
                    .setTarget(visitor.resources[concourseResourceName]!!.element)
                    .createLineMarkerInfo(element.firstChild)
            )
        }
    }
}
