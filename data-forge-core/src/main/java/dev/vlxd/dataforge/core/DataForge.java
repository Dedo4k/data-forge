package dev.vlxd.dataforge.core;

import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.model.Model;
import dev.vlxd.dataforge.core.model.ModelLoader;
import dev.vlxd.dataforge.core.pipeline.BasePipeline;
import dev.vlxd.dataforge.core.pipeline.executor.BasePipelineExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unchecked"})
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DataForge {

    private final DataForgeContext context;

    @Autowired
    public DataForge(DataForgeContext context) {
        this.context = context;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("DataForge initialization...");
        try {
            Model model = context.getModelManager().getActiveModel();

            List<DataSource> dataSources = context.getDataSourceManager().getPrimaryDataSources();

            ModelLoader loader = model.getLoader();

            List dataOrigins = loader.loadModel(dataSources);

            BasePipeline primaryPipeline = context.getPipelineManager().getPrimaryPipeline();

            BasePipelineExecutor pipelineExecutor = context.getPipelineExecutorManager().getExecutorService(primaryPipeline);

            long start = System.currentTimeMillis();

            pipelineExecutor.execute(dataOrigins);

            context.getPipelineExecutorManager().awaitAllExecutions();
            long end = System.currentTimeMillis();

            context.getPipelineExecutorManager().shutdown();

            log.info(Duration.of(end - start, ChronoUnit.MILLIS).toString());

            context.getPipelineManager().getPipelines().forEach((s, basePipeline) -> {
                log.info("Pipeline: {}", s);
                basePipeline.getStages().forEach(stage -> {
                    log.info("-----------------------------");
                    stage.getResult();
                });
                log.info("=============================");
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
