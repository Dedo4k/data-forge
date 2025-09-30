package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.PipelineConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.StageConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.exception.PipelineStageCreationException;
import dev.vlxd.dataforge.core.exception.PrimaryPipelineResolveException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Getter
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PipelineManager {

    private final String PIPELINE_STAGE_CREATE_ERROR = "Failed to create pipeline stage";
    private final String PIPELINE_NOT_FOUND_ERROR = "Failed to create pipeline stage. Pipeline stage with name {} not found";

    private final DataSourceManager datasourceManager;
    private final PipelineStageRegistry pipelineStageRegistry;
    private final List<PipelineConfigurationProperties> properties;
    private final Map<String, Pipeline<?, ?>> pipelines = new HashMap<>();
    private final PipelineContext pipelineContext;

    @Autowired
    public PipelineManager(DataForgeConfigurationProperties config,
                           DataSourceManager datasourceManager,
                           PipelineStageRegistry pipelineStageRegistry) {
        this.datasourceManager = datasourceManager;
        this.pipelineStageRegistry = pipelineStageRegistry;
        this.properties = config.getPipelines();
        this.pipelineContext = new PipelineContext(this, datasourceManager);
    }

    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("Pipelines initialization...");
        resolvePipelines(properties);
    }

    public <T> Pipeline<T, ?> getPipeline(String id) {
        return (Pipeline<T, ?>) pipelines.get(id);
    }

    public Pipeline<?, ?> getPrimaryPipeline() {
        List<Pipeline<?, ?>> pipelines = this.pipelines.values().stream().filter(Pipeline::isPrimary).toList();
        if (pipelines.size() != 1) {
            throw new PrimaryPipelineResolveException("Primary pipeline not defined or more than 1 primary pipeline found");
        }
        return pipelines.getFirst();
    }

    private void resolvePipelines(List<PipelineConfigurationProperties> pipelinesConfigs) {
        log.debug("Resolving pipelines...");
        pipelinesConfigs.forEach(pipelineConfig -> {
            Pipeline<?, ?> pipeline = resolvePipeline(pipelineConfig);
            pipelines.put(pipeline.getId(), pipeline);
        });
        log.debug("Pipelines resolved: {}", pipelines.size());
    }

    private Pipeline<?, ?> resolvePipeline(PipelineConfigurationProperties pipelineConfig) {
        List<StageConfigurationProperties> stagesConfigs = pipelineConfig.getStages();
        List<PipelineStage> stages = new ArrayList<>();
        BasePipelineStage prevStage = null;
        for (StageConfigurationProperties stageConfig : stagesConfigs) {
            Class<? extends PipelineStage<?, ?>> stageClass = pipelineStageRegistry.getStage(stageConfig.getProcessor());
            if (stageClass != null) {
                try {
                    BasePipelineStage stage = (BasePipelineStage) stageClass.getConstructor(Map.class, PipelineContext.class).newInstance(stageConfig.getConfig(), pipelineContext);
                    if (prevStage != null) {
                        prevStage.setNextStage(stage);
                    }
                    prevStage = stage;
                    stages.add(stage);
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
        }

        return new TokenOriginPipeline<>(pipelineConfig.getId(), pipelineConfig.isPrimary(), pipelineConfig.getName(), stages);
    }
}
