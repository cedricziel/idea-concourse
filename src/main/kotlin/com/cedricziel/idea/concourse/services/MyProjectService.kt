package com.cedricziel.idea.concourse.services

import com.cedricziel.idea.concourse.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
