package dev.vlxd.dataforge.scylla.model.configuration;

import dev.vlxd.dataforge.core.pipeline.PipelineStageRegistry;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.origin.*;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.token.NoiseApplyingStage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan({"dev.vlxd.dataforge.scylla.model.pipeline.stage", "dev.vlxd.dataforge.scylla.model"})
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LabelingModelConfiguration {

    private final PipelineStageRegistry pipelineStageRegistry;

    @Autowired
    public LabelingModelConfiguration(PipelineStageRegistry pipelineStageRegistry) {
        this.pipelineStageRegistry = pipelineStageRegistry;
    }

    @PostConstruct
    public void init() {
        pipelineStageRegistry.registerStage(BndboxValidationStage.NAME, BndboxValidationStage.class);
        pipelineStageRegistry.registerStage(ClassnameValidationStage.NAME, ClassnameValidationStage.class);
        pipelineStageRegistry.registerStage(ObjectDeletionStage.NAME, ObjectDeletionStage.class);
        pipelineStageRegistry.registerStage(ClassnameProbabilityStage.NAME, ClassnameProbabilityStage.class);
        pipelineStageRegistry.registerStage(ClassnameRoutingStage.NAME, ClassnameRoutingStage.class);
        pipelineStageRegistry.registerStage(CollectStage.NAME, CollectStage.class);
        pipelineStageRegistry.registerStage(ClassnameCountingStage.NAME, ClassnameCountingStage.class);
        pipelineStageRegistry.registerStage(CropExtractingStage.NAME, CropExtractingStage.class);
        pipelineStageRegistry.registerStage(TokenPipelineExecutionStage.NAME, TokenPipelineExecutionStage.class);
        pipelineStageRegistry.registerStage(NoiseApplyingStage.NAME, NoiseApplyingStage.class);
        pipelineStageRegistry.registerStage(dev.vlxd.dataforge.scylla.model.pipeline.stage.token.CollectStage.NAME, dev.vlxd.dataforge.scylla.model.pipeline.stage.token.CollectStage.class);
    }
}
