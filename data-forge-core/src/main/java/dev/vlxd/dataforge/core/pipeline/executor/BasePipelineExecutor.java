package dev.vlxd.dataforge.core.pipeline.executor;

import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BasePipelineExecutor<D, P extends Pipeline<D, ?>> implements PipelineExecutor<D> {

    protected final P pipeline;
    protected final ExecutorService executorService;
    protected final Phaser phaser;

    public BasePipelineExecutor(P pipeline, int concurrency, Phaser phaser) {
        this.pipeline = pipeline;
        this.executorService = Executors.newFixedThreadPool(concurrency);
        this.phaser = phaser;
    }

    public void shutdown() {
        log.debug("Pipeline executor SHUTDOWN...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.debug("Pipeline executor FORCE SHUTDOWN...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.debug("Pipeline executor ERROR SHUTDOWN...");
            executorService.shutdownNow();
        }
    }
}
