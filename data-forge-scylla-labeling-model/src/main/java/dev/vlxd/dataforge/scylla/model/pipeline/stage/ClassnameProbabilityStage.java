package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;

import java.math.BigDecimal;
import java.util.Map;

public class ClassnameProbabilityStage extends BasePipelineStage<CropOrigin, ClassnameProbabilityStageConfig> {

    public static final String NAME = "classnameProbabilityStage";

    public ClassnameProbabilityStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        ClassnameProbabilityStageConfig.ClassnameProbabilityStageConfigBuilder builder = ClassnameProbabilityStageConfig.builder();
        Object classname = configMap.get("probability");
        if (classname instanceof String value) {
            builder.classname(value);
        }
        Object probability = configMap.get("probability");
        if (probability instanceof BigDecimal value) {
            builder.probability(value);
        }
        Object dataSource = configMap.get("data-source");
        if (dataSource instanceof String value) {
            builder.dataSource(value);
        }
        this.config = builder.build();
        this.pipelineContext = pipelineContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ClassnameProbabilityStageConfig getConfig() {
        return config;
    }

    @Override
    public CropOrigin execute(CropOrigin data) {
        return nextStage != null ? nextStage.execute(data) : null;
    }

    @Override
    public void getResult() {

    }
}
