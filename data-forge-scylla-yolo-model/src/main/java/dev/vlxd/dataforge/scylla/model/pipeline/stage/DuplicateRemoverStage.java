package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.scylla.model.YoloToken;

import java.util.Map;

public class DuplicateRemoverStage implements PipelineStage<YoloToken, DuplicateRemoverStageConfig> {

    public static final String NAME = "duplicateRemoverStage";

    private final DuplicateRemoverStageConfig config;

    public DuplicateRemoverStage(Map<String, Object> configMap) {
        this.config = null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DuplicateRemoverStageConfig getConfig() {
        return config;
    }

    @Override
    public YoloToken execute(YoloToken data) {
        return null;
    }

    @Override
    public void getResult() {

    }
}
