package dev.vlxd.dataforge.core.pipeline.executor;

import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.core.pipeline.TokenOriginPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Phaser;

@Slf4j
public class TokenOriginPipelineExecutor<O extends TokenOrigin<?, ?>> extends BasePipelineExecutor<O, TokenOriginPipeline<O, ?>> {

    public TokenOriginPipelineExecutor(TokenOriginPipeline<O, ?> pipeline, int concurrency, Phaser phaser) {
        super(pipeline, concurrency, phaser);
    }

    @Override
    public void execute(O origin) {
        if (!origin.isLoaded()) {
            origin.loadOrigin();
        }

        pipeline.execute(origin);

        origin.unloadOrigin();
    }

    @Override
    public void execute(List<O> origins) {
        phaser.bulkRegister(origins.size());
        origins.forEach(o -> executorService.submit(() -> {
            try {
                execute(o);
            } catch (Exception e) {
                log.error("Task execution error", e);
            } finally {
                phaser.arriveAndDeregister();
            }
        }));
    }
}
