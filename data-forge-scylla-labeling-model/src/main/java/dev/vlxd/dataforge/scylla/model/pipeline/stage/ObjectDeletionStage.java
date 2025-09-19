package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.scylla.model.Crop;

import java.util.Map;

public class ObjectDeletionStage implements PipelineStage<Crop, ObjectDeletionStageConfig> {

    public final static String NAME = "objectDeletionStage";
    private final String CLASSNAMES_PARSE_ERROR = "Failed to parse config {}. classnames should consist of strings";

    private final ObjectDeletionStageConfig config;

    public ObjectDeletionStage(Map<String, Object> configMap) {
        ObjectDeletionStageConfig.ObjectDeletionStageConfigBuilder builder = new ObjectDeletionStageConfig.ObjectDeletionStageConfigBuilder();
        Object classnames = configMap.get("classnames");
        if (classnames instanceof Map<?, ?> map) {
            builder.withClassnames(map.values().stream()
                    .map(value -> {
                        if (value instanceof String str) {
                            return str;
                        } else {
                            throw new PipelineStageConfigParseException(CLASSNAMES_PARSE_ERROR, configMap);
                        }
                    }).toList());
        }
        this.config = builder.build();
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
    public Crop execute(Crop data) {
        return data;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }
}
