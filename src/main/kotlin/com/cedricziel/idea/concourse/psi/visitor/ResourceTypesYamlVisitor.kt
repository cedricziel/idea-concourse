package com.cedricziel.idea.concourse.psi.visitor

import com.cedricziel.idea.concourse.ConcourseUtils
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import org.jetbrains.annotations.NotNull
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLPsiElement
import org.jetbrains.yaml.psi.YamlRecursivePsiElementVisitor

class ResourceTypesYamlVisitor : YamlRecursivePsiElementVisitor() {
    val resourceTypes = mutableMapOf<String, @NotNull SmartPsiElementPointer<YAMLPsiElement>>()

    override fun visitKeyValue(keyValue: YAMLKeyValue) {
        if (keyValue.key == null) {
            super.visitKeyValue(keyValue)

            return
        }

        if (!ConcourseUtils.isInResourceTypes(keyValue)) {
            super.visitKeyValue(keyValue)

            return
        }

        if (keyValue.keyText != "name") {
            super.visitKeyValue(keyValue)

            return
        }

        if (keyValue.value != null) {
            resourceTypes.putIfAbsent(keyValue.valueText, SmartPointerManager.createPointer(keyValue.value!!))
        }

        super.visitKeyValue(keyValue)
    }
}
