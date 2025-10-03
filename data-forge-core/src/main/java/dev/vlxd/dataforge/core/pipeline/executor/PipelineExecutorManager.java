package dev.vlxd.dataforge.core.pipeline.executor;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.exception.UnknownPipelineExecutorException;
import dev.vlxd.dataforge.core.pipeline.BasePipeline;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.TokenOriginPipeline;
import dev.vlxd.dataforge.core.pipeline.TokenPipeline;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class PipelineExecutorManager {

    private final Map<BasePipeline, BasePipelineExecutor> pipelineExecutors;
    private final DataForgeContext context;
    private final Phaser globalPhaser;
    private final Map<BasePipelineStage, ExecutorService> stageExecutors;

    public PipelineExecutorManager(DataForgeContext context) {
        this.context = context;
        this.pipelineExecutors = new ConcurrentHashMap<>();
        this.stageExecutors = new ConcurrentHashMap<>();
        this.globalPhaser = new Phaser(1);
    }

    public BasePipelineExecutor getExecutorService(BasePipeline pipeline) {
        return pipelineExecutors.compute(pipeline, (p, executorService) -> {
            if (executorService != null) {
                return executorService;
            }

            switch (pipeline.getType()) {
                case ORIGIN -> {
                    return new TokenOriginPipelineExecutor((TokenOriginPipeline) pipeline, context.getProperties().getConcurrency(), globalPhaser);
                }
                case TOKEN -> {
                    return new TokenPipelineExecutor((TokenPipeline) pipeline, context.getProperties().getConcurrency(), globalPhaser);
                }
                default ->
                        throw new UnknownPipelineExecutorException("Unknown pipeline executor type {}", pipeline.getType());
            }
        });
    }

    public ExecutorService getExecutorService(BasePipelineStage stage) {
        return stageExecutors.compute(stage, (s, executorService) -> Executors.newFixedThreadPool(context.getProperties().getConcurrency()));
    }

    public void awaitAllExecutions() {
        globalPhaser.arriveAndAwaitAdvance();
    }

    public boolean hasActiveTasks() {
        return globalPhaser.getUnarrivedParties() > 1;
    }

    public void shutdown() {
        globalPhaser.arriveAndDeregister();
        pipelineExecutors.values().forEach(BasePipelineExecutor::shutdown);
        stageExecutors.values().forEach(ExecutorService::shutdown);
    }
}
