package com.t1c.gradle.jsass;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;
import io.bit3.jsass.context.Context;
import io.bit3.jsass.context.FileContext;
import lombok.ToString;

@ToString
final class FileToProcess {

  private final Path srcBasePath;
  private final File file;

  public FileToProcess(final Path srcBasePath, final File file) {
    this.srcBasePath = srcBasePath;
    this.file = file;
  }

  private URI toURI() {
    return file.toURI();
  }

  public Path path() {
    return file.toPath();
  }

  /**
   * Compile file to css
   *
   * @param jsassOptions
   * @return
   * @throws CompilationException
   */
  public CompiledFile compile(final Options jsassOptions) throws CompilationException {
    final Context context = new FileContext(toURI(), null, jsassOptions);
    return new CompiledFile(this, new Compiler().compile(context));
  }

  /**
   * Get path after base dir and without complete name
   *
   * @return
   */
  public String partialPath() {
    final String basePath = srcBasePath.toFile().getAbsolutePath();
    return file.getAbsolutePath().replace(basePath, "").replace(file.getName(), "");
  }

  /**
   * Get name without extension
   *
   * @return
   */
  public String name() {
    return file.getName().split("\\.")[0];
  }

}
