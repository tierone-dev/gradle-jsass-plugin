package com.t1c.gradle.jsass;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

public class CompileSassTask extends DefaultTask {

  @TaskAction
  public void compile() {
    CompileSassOptions options = this.getProject().getExtensions().findByType(
        CompileSassOptions.class
    );
    if (options == null) {
      options = new CompileSassOptions();
    }
    try {
      final SassCompiler compiler = new SassCompiler(options);
      compiler.compile();
      if (options.isWatch()) {
        CompileSassTask.interruptExistingThreads();
        for (final FileToProcess file : compiler.getInputFiles()) {
          new CompileSassThread(compiler, file).start();
        }
      }
    } catch (final Exception e) {
      throw new TaskExecutionException(this, e);
    }
  }

  private static void interruptExistingThreads() {
    for (final Thread thread : Thread.getAllStackTraces().keySet()) {
      if (thread.getName().startsWith(CompileSassThread.PREFIX)) {
        thread.interrupt();
        break;
      }
    }
  }

}
