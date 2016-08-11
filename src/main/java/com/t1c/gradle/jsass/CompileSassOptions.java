package com.t1c.gradle.jsass;

import io.bit3.jsass.OutputStyle;

import java.io.File;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CompileSassOptions {

  /**
   * The source directory where you keep .scss and/or .sass files.
   */
  private File sassDir;

  /**
   * The target directory to output compiled CSS.
   */
  private File cssDir;

  /**
   * Name of main sass file.
   */
  private String mainFilename = "main.scss";

  /**
   * Output style for the generated css code.
   */
  private OutputStyle outputStyle = OutputStyle.COMPRESSED;

  /**
   * If you want inline source comments.
   */
  private boolean sourceComments = false;

  /**
   * Embed sourceMappingUrl as data uri.
   */
  private boolean sourceMapEmbed = false;

  public void setOutputStyle(final OutputStyle outputStyle) {
    this.outputStyle = outputStyle;
  }

  public void setOutputStyle(final String outputStyle) {
    setOutputStyle(outputStyle != null ? OutputStyle.valueOf(outputStyle.toUpperCase()) : null);
  }

}
