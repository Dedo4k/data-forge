package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.PipelineConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.StageConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.exception.PipelineStageCreationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true")
public class PipelineManager {

    private final String PIPELINE_STAGE_CREATE_ERROR = "Failed to create pipeline stage";
    private final String PIPELINE_NOT_FOUND_ERROR = "Failed to create pipeline stage. Pipeline stage with name {} not found";

    private final DataSourceManager datasourceManager;
    private final PipelineStageRegistry pipelineStageRegistry;
    private final List<PipelineConfigurationProperties> properties;
    private final Map<String, DataChunkPipeline<?>> pipelines = new HashMap<>();

    @Autowired
    public PipelineManager(DataForgeConfigurationProperties config,
                           DataSourceManager datasourceManager,
                           PipelineStageRegistry pipelineStageRegistry) {
        this.datasourceManager = datasourceManager;
        this.pipelineStageRegistry = pipelineStageRegistry;
        this.properties = config.getPipelines();
    }

    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("Pipelines initialization...");
        resolvePipelines(properties);
    }

    public DataChunkPipeline<?> getPipeline(String id) {
        return pipelines.get(id);
    }

    private void resolvePipelines(List<PipelineConfigurationProperties> pipelinesConfigs) {
        log.debug("Resolving pipelines...");
        pipelinesConfigs.forEach(pipelineConfig -> {
            DataChunkPipeline<?> pipeline = resolvePipeline(pipelineConfig);
            pipelines.put(pipeline.getId(), pipeline);
        });
        log.debug("Pipelines resolved: {}", pipelines.size());
    }

    private DataChunkPipeline<?> resolvePipeline(PipelineConfigurationProperties pipelineConfig) {
        List<StageConfigurationProperties> stagesConfigs = pipelineConfig.getStages();

        List<? extends PipelineStage<?, ?>> stages = stagesConfigs.stream().map(stageConfig -> {
            Class<? extends PipelineStage<?, ?>> stage = pipelineStageRegistry.getStage(stageConfig.getProcessor());
            if (stage != null) {
                try {
                    return (PipelineStage<?, ?>) stage.getConstructor(Map.class).newInstance(stageConfig.getConfig());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    log.error(PIPELINE_STAGE_CREATE_ERROR, e);
                    throw new PipelineStageCreationException(PIPELINE_STAGE_CREATE_ERROR, e);
                } catch (InvocationTargetException e) {
                    log.error(PIPELINE_STAGE_CREATE_ERROR, e.getTargetException());
                    throw new PipelineStageCreationException(PIPELINE_STAGE_CREATE_ERROR, e.getTargetException());
                }
            } else {
                log.error(PIPELINE_NOT_FOUND_ERROR, stageConfig.getProcessor());
                throw new PipelineStageCreationException(PIPELINE_NOT_FOUND_ERROR, stageConfig.getProcessor());
            }
        }).toList();

        return new DataChunkPipeline(pipelineConfig.getId(), pipelineConfig.getName(), stages);
    }
}
