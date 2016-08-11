package com.t1c.gradle.jsass;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CompileSassPlugin implements Plugin<Project> {

  @Override
  public void apply(final Project project) {
    project.getExtensions().create("sassOptions", CompileSassOptions.class);
    final CompileSassTask task = project.getTasks().create("compileSass", CompileSassTask.class);
    task.setDescription("Compile sass stylesheets to css");
  }

}
