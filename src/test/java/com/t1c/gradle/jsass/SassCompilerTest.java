package com.t1c.gradle.jsass;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class SassCompilerTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Test
  public void checkEmptySassDir() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new SassCompiler(new CompileSassOptions()).compile();
  }

  @Test
  public void checkEmptyCssDir() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new SassCompiler(new CompileSassOptions()).compile();
  }

  @Test
  public void checkEmptyMainFilename() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    final CompileSassOptions options = new CompileSassOptions();
    options.setMainFilename(null);
    new SassCompiler(options).compile();
  }

  @Test
  public void checkEmptyOutputStyle() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    final CompileSassOptions options = new CompileSassOptions();
    options.setOutputStyle((String) null);
    new SassCompiler(options).compile();
  }

  private String getStylesheetsContent(final File ioFolder) throws IOException {
    final File stylesheets = new File(ioFolder, "stylesheets.css");
    return FileUtils.readFileToString(stylesheets, StandardCharsets.UTF_8).trim();
  }

  private void createStylesheetsFile(final File ioFolder) throws IOException {
    final File inputScss = new File(ioFolder, "stylesheets.scss");
    final String content = "$primary-color: #444;body {color: $primary-color;}";
    FileUtils.writeStringToFile(inputScss, content, StandardCharsets.UTF_8.name());
  }

  @Test
  public void checkCompile() throws Exception {

    final File ioFolder = testFolder.newFolder();
    createStylesheetsFile(ioFolder);

    final CompileSassOptions options = new CompileSassOptions();
    options.setSassDir(ioFolder);
    options.setCssDir(ioFolder);
    options.setMainFilename("stylesheets.scss");

    new SassCompiler(options).compile();
    final String content = getStylesheetsContent(ioFolder);
    Assert.assertEquals("body{color:#444}", content);
  }

}
