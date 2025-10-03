package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.YoloToken;

import java.util.Map;

public class DuplicateRemoverStage extends BasePipelineStage<YoloToken, DuplicateRemoverStageConfig> {

    public static final String NAME = "duplicateRemoverStage";

    public DuplicateRemoverStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, YoloToken.class);
    }

    @Override
    public void execute(YoloToken data) {
        accept(data);
    }
}
