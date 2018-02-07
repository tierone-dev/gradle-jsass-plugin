package com.t1c.gradle.jsass;

import java.io.IOException;

public final class CompileSassThread extends Thread {

    static final String PREFIX = "SassDirWatcher";

    CompileSassThread(final SassCompiler compiler, final FileToProcess file) throws IOException {
        super(
            new SassDirWatcher(compiler, file),
            String.format("%s-%s", CompileSassThread.PREFIX, Math.random())
        );
    }

}
