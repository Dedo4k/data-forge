package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.PipelineConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.StageConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.exception.PipelineNotFoundException;
import dev.vlxd.dataforge.core.exception.PipelineStageCreationException;
import dev.vlxd.dataforge.core.exception.PrimaryPipelineResolveException;
import dev.vlxd.dataforge.core.exception.UnknownPipelineException;
import dev.vlxd.dataforge.core.model.Model;
import dev.vlxd.dataforge.core.model.ModelManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Getter
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PipelineManager {

    private final String PIPELINE_STAGE_CREATE_ERROR = "Failed to create pipeline stage";
    private final String PIPELINE_NOT_FOUND_ERROR = "Failed to create pipeline stage. Pipeline stage with name {} not found";
    private final String UNSUPPORTED_DATA_TYPE = "Failed to create pipeline stage. Pipeline stage {} must support {} data type but {} data type defined";

    private final DataSourceManager datasourceManager;
    private final ModelManager modelManager;
    private final PipelineStageRegistry pipelineStageRegistry;
    private final List<PipelineConfigurationProperties> properties;
    private final DataForgeContext context;

    private Map<String, BasePipeline<?, ?>> pipelines;

    @Autowired
    public PipelineManager(DataForgeConfigurationProperties config,
                           DataSourceManager datasourceManager,
                           PipelineStageRegistry pipelineStageRegistry,
                           ModelManager modelManager, DataForgeContext context) {
        this.datasourceManager = datasourceManager;
        this.pipelineStageRegistry = pipelineStageRegistry;
        this.properties = config.getPipelines();
        this.modelManager = modelManager;
        this.context = context;
    }

    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("Pipelines initialization...");
        pipelines = Collections.unmodifiableMap(resolvePipelines(properties));
        log.debug("Successfully initialized {} pipelines", pipelines.size());
    }

    public <T> BasePipeline<T, ?> getPipeline(String id) {
        return (BasePipeline<T, ?>) Optional.ofNullable(pipelines.get(id))
                .orElseThrow(() -> new PipelineNotFoundException("Pipeline not found with id={}", id));
    }

    public BasePipeline getPrimaryPipeline() {
        List<BasePipeline<?, ?>> pipelines = this.pipelines.values().stream().filter(BasePipeline::isPrimary).toList();
        if (pipelines.size() != 1) {
            throw new PrimaryPipelineResolveException("Primary pipeline not defined or more than 1 primary pipeline found");
        }
        return pipelines.getFirst();
    }

    private Map<String, BasePipeline<?, ?>> resolvePipelines(List<PipelineConfigurationProperties> pipelineConfigs) {
        log.debug("Resolving pipelines...");
        return pipelineConfigs.stream().map(this::resolvePipeline).collect(Collectors.toMap(BasePipeline::getId, Function.identity()));
    }

    private BasePipeline<?, ?> resolvePipeline(PipelineConfigurationProperties pipelineConfig) {
        log.debug("Resolving pipeline: {}", pipelineConfig.getId());

        Model activeModel = modelManager.getActiveModel();

        switch (pipelineConfig.getType()) {
            case ORIGIN -> {
                List<BasePipelineStage> stages = createPipelineStages(pipelineConfig.getStages(), activeModel.getOriginType());
                return new TokenOriginPipeline<>(pipelineConfig.getId(), pipelineConfig.isPrimary(), pipelineConfig.getName(), stages, activeModel.getOriginType());
            }
            case TOKEN -> {
                List<BasePipelineStage> stages = createPipelineStages(pipelineConfig.getStages(), activeModel.getTokenType());
                return new TokenPipeline<>(pipelineConfig.getId(), pipelineConfig.isPrimary(), pipelineConfig.getName(), stages, activeModel.getTokenType());
            }
            default -> throw new UnknownPipelineException("Unknown pipeline type " + pipelineConfig.getType());
        }
    }

    private <T> List<BasePipelineStage> createPipelineStages(List<StageConfigurationProperties> stageConfigs, Class<T> dataType) {
        List<BasePipelineStage> stages = new ArrayList<>();
        BasePipelineStage prevStage = null;

        for (StageConfigurationProperties stageConfig : stageConfigs) {
            BasePipelineStage stage = createPipelineStage(stageConfig);

            if (!stage.getDataType().equals(dataType)) {
                throw new PipelineStageCreationException(UNSUPPORTED_DATA_TYPE, stage.getName(), dataType, stage.getDataType());
            }

            if (prevStage != null) {
                prevStage.setNextStage(stage);
            }

            stages.add(stage);
            prevStage = stage;
        }

        return stages;
    }

    private BasePipelineStage createPipelineStage(StageConfigurationProperties stageConfig) {
        String processor = stageConfig.getProcessor();
        Class<? extends PipelineStage<?, ?>> stageClass = pipelineStageRegistry.getStage(processor);

        if (stageClass == null) {
            log.error(PIPELINE_NOT_FOUND_ERROR, processor);
            throw new PipelineStageCreationException(PIPELINE_NOT_FOUND_ERROR, processor);
        }

        try {
            return (BasePipelineStage) stageClass
                    .getConstructor(Map.class, DataForgeContext.class)
                    .newInstance(stageConfig.getConfig(), context);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            log.error(PIPELINE_STAGE_CREATE_ERROR, e);
            throw new PipelineStageCreationException(PIPELINE_STAGE_CREATE_ERROR, e);
        } catch (InvocationTargetException e) {
            log.error(PIPELINE_STAGE_CREATE_ERROR, e.getTargetException());
            throw new PipelineStageCreationException(PIPELINE_STAGE_CREATE_ERROR, e.getTargetException());
        }
    }
}
