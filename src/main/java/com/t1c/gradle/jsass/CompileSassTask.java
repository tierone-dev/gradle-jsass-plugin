package com.t1c.gradle.jsass;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

public class CompileSassTask extends DefaultTask {

  @TaskAction
  public void compile() {
    CompileSassOptions options = getProject().getExtensions().findByType(CompileSassOptions.class);
    if (options == null) {
      options = new CompileSassOptions();
    }
    try {
      new SassCompiler(options).compile();
    } catch (final Exception e) {
      throw new TaskExecutionException(this, e);
    }
  }

}
