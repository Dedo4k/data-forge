package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ObjectDeletionStage extends BasePipelineStage<CropOrigin, ObjectDeletionStageConfig> {

    public final static String NAME = "objectDeletionStage";

    private final static String CLASSNAMES_PARSE_ERROR = "Failed to parse config {}. classnames should consist of strings";

    public ObjectDeletionStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        ObjectDeletionStageConfig.ObjectDeletionStageConfigBuilder builder = ObjectDeletionStageConfig.builder();
        Object classnames = configMap.get("classnames");
        if (classnames instanceof Map<?, ?> map) {
            builder.classnames(map.values().stream()
                    .map(value -> {
                        if (value instanceof String str) {
                            return str;
                        } else {
                            throw new PipelineStageConfigParseException(CLASSNAMES_PARSE_ERROR, configMap);
                        }
                    }).toList());
        }
        this.config = builder.build();
        this.pipelineContext = pipelineContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ObjectDeletionStageConfig getConfig() {
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
