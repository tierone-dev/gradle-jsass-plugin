package com.t1c.gradle.jsass;

import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import io.bit3.jsass.context.Context;
import io.bit3.jsass.context.FileContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    if (options.getMainFilename() == null || options.getMainFilename().trim().isEmpty()) {
      throw new IllegalArgumentException("Found empty main filename");
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

  private File getInputFile(final CompileSassOptions options) {
    return Paths.get(options.getSassDir().getAbsolutePath(), options.getMainFilename()).toFile();
  }

  private String getOutputName(final String filename) {
    return String.format("%s.css", filename.split("\\.")[0]);
  }

  private File getOutputCssFile(final CompileSassOptions options) {
    return Paths.get(options.getCssDir().getAbsolutePath(),
        getOutputName(options.getMainFilename())).toFile();
  }

  private void writeCssFile(final CompileSassOptions options, final Output output)
      throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(getOutputCssFile(options)))) {
      bw.write(output.getCss());
    }
  }

  private FileContext createContext(final CompileSassOptions options) {
    return new FileContext(getInputFile(options).toURI(), null, createJsassOptions(options));
  }

  public void compile() throws Exception {
    SassCompiler.LOG.debug("Starting compile with options: " + options);
    checkOptions();
    final Context context = createContext(options);
    final Output output = new Compiler().compile(context);
    writeCssFile(options, output);
    // TODO: save source map if specified to generate
    SassCompiler.LOG.debug("Compiled successfully");
  }

}
