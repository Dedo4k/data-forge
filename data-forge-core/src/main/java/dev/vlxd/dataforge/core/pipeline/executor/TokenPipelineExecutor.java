package dev.vlxd.dataforge.core.pipeline.executor;

import dev.vlxd.dataforge.api.Token;
import dev.vlxd.dataforge.core.pipeline.TokenPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Phaser;

@Slf4j
public class TokenPipelineExecutor<D extends Token<?, ?, ?, ?>> extends BasePipelineExecutor<D, TokenPipeline<D, ?>> {

    public TokenPipelineExecutor(TokenPipeline<D, ?> pipeline, int concurrency, Phaser phaser) {
        super(pipeline, concurrency, phaser);
    }

    @Override
    public void execute(D data) {
        pipeline.execute(data);
    }

    @Override
    public void execute(List<D> data) {
        phaser.bulkRegister(data.size());
        data.forEach(d -> executorService.submit(() -> {
            try {
                execute(d);
            } catch (Exception e) {
                log.error("Task execution error", e);
            } finally {
                phaser.arriveAndDeregister();
            }
        }));
    }
}
