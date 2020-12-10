package com.github.cedricziel.ideaconcourse.services

import com.intellij.openapi.project.Project
import com.github.cedricziel.ideaconcourse.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
