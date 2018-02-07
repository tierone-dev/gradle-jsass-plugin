package com.t1c.gradle.jsass;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.gradle.internal.impldep.com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SassDirWatcher implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SassDirWatcher.class);

    private final SassCompiler compiler;
    private final FileToProcess file;
    private final WatchService watcher;

    SassDirWatcher(final SassCompiler comp, final FileToProcess file) throws IOException {
        this.compiler = comp;
        this.file = file;
        this.watcher = this.file.path().getFileSystem().newWatchService();
    }

    private void takeAction(final WatchEvent<?> event) {
        final Path context = (Path) event.context();
        if (context.getFileName().endsWith(".scss")) {
            try {
                SassDirWatcher.LOG.info("Recompiling sass: found modification in {}", context.toString());
                this.compiler.compile();
            } catch (final Exception ex) {
                SassDirWatcher.LOG.error("Error compiling", ex);
            }
        }
    }

    @Override
    public void run() {
        SassDirWatcher.LOG.info(
            "Start watching for {}", this.file.path().toString()
        );
        try {
            this.file.path().register(
                this.watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY
            );
            WatchKey key = this.watcher.take();
            while (key != null) {
                for (final WatchEvent<?> event : key.pollEvents()) {
                    this.takeAction(event);
                }
                key.reset();
                key = this.watcher.take();
            }
        } catch (final InterruptedException ex) {
            Log.info("Interrupted");
        } catch (final IOException ex) {
            Log.error("IOException", ex);
        }
        SassDirWatcher.LOG.info(
            "Stopping watch for {}", this.file.path().toString()
        );
    }

}
