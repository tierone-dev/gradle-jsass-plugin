package com.t1c.gradle.jsass

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class CompileSassTaskTest {

    @Test
    public void checkAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('compileSass', type: CompileSassTask)
        assertTrue(task instanceof CompileSassTask)
    }

}