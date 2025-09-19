package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.scylla.model.YoloDataChunk;

import java.util.Map;

public class DuplicateRemoverStage implements PipelineStage<YoloDataChunk, DuplicateRemoverStageConfig> {

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
    public YoloDataChunk execute(YoloDataChunk data) {
        return null;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }
}
