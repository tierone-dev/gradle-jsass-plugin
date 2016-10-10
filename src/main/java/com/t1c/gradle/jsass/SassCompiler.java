package com.t1c.gradle.jsass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bit3.jsass.Options;

public class SassCompiler {
  private static final Logger LOG = LoggerFactory.getLogger(SassCompiler.class);

  private final CompileSassOptions options;

  public SassCompiler(final CompileSassOptions options) {
    this.options = options;
  }

  private void checkOptions() {
    if (options.getSassDir() == null || !options.getSassDir().exists()) {
      throw new IllegalArgumentException("Could not found sassDir");
    }
    if (options.getCssDir() == null || !options.getCssDir().exists()) {
      throw new IllegalArgumentException("Could not found cssDir");
    }
    if (options.getOutputStyle() == null) {
      throw new IllegalArgumentException("Found null outputStyle");
    }
  }

  private Options createJsassOptions(final CompileSassOptions options) {
    final Options result = new Options();
    result.setOutputStyle(options.getOutputStyle());
    result.setSourceComments(options.isSourceComments());
    result.setSourceMapEmbed(options.isSourceMapEmbed());
    return result;
  }

  private List<FileToProcess> getInputFiles() {
    final List<FileToProcess> result = new ArrayList<>();
    try {
      final Path sassPath = Paths.get(options.getSassDir().getAbsolutePath());
      Files.walkFileTree(sassPath, new FileToProcessVisitor(sassPath, result));
    } catch (final IOException e) {
      SassCompiler.LOG.error(e.getMessage(), e);
    }
    return result;
  }

  public void compile() throws Exception {
    SassCompiler.LOG.debug("Starting compile with options: " + options);
    checkOptions();
    final Options jsassOptions = createJsassOptions(options);
    for (final FileToProcess file : getInputFiles()) {
      file.compile(jsassOptions).saveToCss(options.getCssDir());
    }
    // TODO: save source map if specified to generate
    SassCompiler.LOG.debug("Compiled successfully");
  }

}
