package com.t1c.gradle.jsass;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

public final class FileToProcessVisitor implements FileVisitor<Path> {

  private final Path srcBasePath;
  private final Collection<FileToProcess> container;

  public FileToProcessVisitor(final Path srcBasePath, final Collection<FileToProcess> container) {
    this.srcBasePath = srcBasePath;
    this.container = container;
  }

  @Override
  public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
      throws IOException {
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFile(final Path filePath, final BasicFileAttributes attrs)
      throws IOException {

    final File file = filePath.toFile();
    final String name = file.getName();

    if (!name.startsWith("_") && (name.endsWith(".sass") || name.endsWith(".scss"))) {
      container.add(new FileToProcess(srcBasePath, file));
    }

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(final Path file, final IOException exc)
      throws IOException {
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult postVisitDirectory(final Path dir, final IOException exc)
      throws IOException {
    return FileVisitResult.CONTINUE;
  }

}
