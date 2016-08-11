package com.t1c.gradle.jsass

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class CompileSassPluginTest {

    @Test
    public void checkAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.getPlugins().apply 'com.t1c.gradle.jsass.plugin'
        assertTrue(project.tasks.compileSass instanceof CompileSassTask)
    }

}