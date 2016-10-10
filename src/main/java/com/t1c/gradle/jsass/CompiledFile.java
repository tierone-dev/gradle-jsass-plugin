package com.t1c.gradle.jsass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.bit3.jsass.Output;
import lombok.ToString;

@ToString
class CompiledFile {

  private final FileToProcess file;
  private final Output output;

  public CompiledFile(final FileToProcess file, final Output output) {
    this.file = file;
    this.output = output;
  }

  private void write(final Path dest, final String name) throws IOException {
    final File destDir = dest.toFile();
    if (!destDir.exists()) {
      destDir.mkdirs();
    }
    try (final BufferedWriter bw = new BufferedWriter(new FileWriter(new File(destDir, name)))) {
      bw.write(output.getCss());
    }
  }

  public void saveToCss(final File cssBaseDir) throws IOException {
    final String partialPath = file.partialPath();
    final String name = file.name();
    write(Paths.get(cssBaseDir.getAbsolutePath(), partialPath), String.format("%s.css", name));
  }

}
