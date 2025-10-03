package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.CropOrigin;

import java.util.Map;

public class ClassnameProbabilityStage extends BasePipelineStage<CropOrigin, ClassnameProbabilityStageConfig> {

    public static final String NAME = "classnameProbabilityStage";

    public ClassnameProbabilityStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
        ClassnameProbabilityStageConfig.ClassnameProbabilityStageConfigBuilder builder = ClassnameProbabilityStageConfig.builder();
        Object classname = configMap.get("classname");
        if (classname instanceof String value) {
            builder.classname(value);
        }
        Object probability = configMap.get("probability");
        if (probability instanceof Double value) {
            builder.probability(value);
        }
        Object dataSource = configMap.get("data-source");
        if (dataSource instanceof String value) {
            builder.dataSource(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(CropOrigin data) {
        accept(data);
    }
}
