package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.scylla.model.Crop;

import java.util.Map;

public class ClassnameValidationStage implements PipelineStage<Crop, ClassnameValidationStageConfig> {

    public static final String NAME = "classnameValidationStage";
    private static final String BLACKLIST_PARSE_ERROR = "Failed to parse config {}. blacklist should consist of strings";
    private static final String WHITELIST_PARSE_ERROR = "Failed to parse config {}. whitelist should consist of strings";

    private final ClassnameValidationStageConfig config;

    public ClassnameValidationStage(Map<String, Object> configMap) {
        ClassnameValidationStageConfig.ClassnameValidationStageConfigBuilder builder = new ClassnameValidationStageConfig.ClassnameValidationStageConfigBuilder();
        Object blacklist = configMap.get("blacklist");
        if (blacklist instanceof Map<?, ?> map) {
            builder.withBlackList(map.values().stream()
                    .map(value -> {
                        if (value instanceof String str) {
                            return str;
                        } else {
                            throw new PipelineStageConfigParseException(BLACKLIST_PARSE_ERROR, configMap);
                        }
                    }).toList());
        }
        Object whitelist = configMap.get("whitelist");
        if (whitelist instanceof Map<?, ?> map) {
            builder.withWhiteList(map.values().stream()
                    .map(value -> {
                        if (value instanceof String str) {
                            return str;
                        } else {
                            throw new PipelineStageConfigParseException(WHITELIST_PARSE_ERROR, configMap);
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
    public ClassnameValidationStageConfig getConfig() {
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
