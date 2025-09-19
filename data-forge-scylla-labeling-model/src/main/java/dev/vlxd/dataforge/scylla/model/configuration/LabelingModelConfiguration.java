package dev.vlxd.dataforge.scylla.model.configuration;

import dev.vlxd.dataforge.core.model.ModelRegistry;
import dev.vlxd.dataforge.core.pipeline.PipelineStageRegistry;
import dev.vlxd.dataforge.scylla.model.LabelingModel;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.BndboxValidationStage;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.ClassnameValidationStage;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.ObjectDeletionStage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
public class LabelingModelConfiguration {

    private final PipelineStageRegistry pipelineStageRegistry;
    private final ModelRegistry modelRegistry;

    @Autowired
    public LabelingModelConfiguration(PipelineStageRegistry pipelineStageRegistry,
                                      ModelRegistry modelRegistry) {
        this.pipelineStageRegistry = pipelineStageRegistry;
        this.modelRegistry = modelRegistry;
    }

    @Bean
    public LabelingModel labelingModel() {
        LabelingModel labelingModel = new LabelingModel();

        modelRegistry.addModel(labelingModel);

        pipelineStageRegistry.registerStage(BndboxValidationStage.NAME, BndboxValidationStage.class);
        pipelineStageRegistry.registerStage(ClassnameValidationStage.NAME, ClassnameValidationStage.class);
        pipelineStageRegistry.registerStage(ObjectDeletionStage.NAME, ObjectDeletionStage.class);

        return labelingModel;
    }
}
