package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class BasePipelineExecutor implements PipelineExecutor {

    private final Pipeline pipeline;
    private final ExecutorService executorService;

    public BasePipelineExecutor(Pipeline pipeline, int concurrency) {
        this.pipeline = pipeline;
        this.executorService = Executors.newFixedThreadPool(concurrency);
    }

    @Override
    public void execute(TokenOrigin<?, ?> origin) {
        if (!origin.isLoaded()) {
            origin.loadOrigin();
        }

        pipeline.execute(origin);

        origin.unloadOrigin();
    }

    @Override
    public void execute(List<TokenOrigin<?, ?>> origins) {
        long start = System.currentTimeMillis();
        try {
            executorService.invokeAll(origins.stream().map(this::prepareTask).toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
        long end = System.currentTimeMillis();
        System.out.println(Duration.of(end - start, ChronoUnit.MILLIS));
    }

    private Callable<Void> prepareTask(TokenOrigin<?, ?> origin) {
        return () -> {
            execute(origin);
            return null;
        };
    }
}
