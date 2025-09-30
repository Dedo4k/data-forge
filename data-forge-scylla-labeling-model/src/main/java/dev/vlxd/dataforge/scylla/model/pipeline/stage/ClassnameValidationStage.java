package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ClassnameValidationStage extends BasePipelineStage<CropOrigin, ClassnameValidationStageConfig> {

    public static final String NAME = "classnameValidationStage";

    private static final String BLACKLIST_PARSE_ERROR = "Failed to parse config {}. blacklist should consist of strings";
    private static final String WHITELIST_PARSE_ERROR = "Failed to parse config {}. whitelist should consist of strings";

    private final List<CropOrigin> invalidOrigins = new CopyOnWriteArrayList<>();

    public ClassnameValidationStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        ClassnameValidationStageConfig.ClassnameValidationStageConfigBuilder builder = ClassnameValidationStageConfig.builder();
        Object blacklist = configMap.get("blacklist");
        if (blacklist instanceof Map<?, ?> map) {
            builder.blackList(map.values().stream()
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
            builder.whiteList(map.values().stream()
                    .map(value -> {
                        if (value instanceof String str) {
                            return str;
                        } else {
                            throw new PipelineStageConfigParseException(WHITELIST_PARSE_ERROR, configMap);
                        }
                    }).toList());
        }
        Object fallback = configMap.get("fallback");
        if (fallback instanceof String value) {
            builder.fallback(value);
        }
        this.config = builder.build();
        this.pipelineContext = pipelineContext;
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
    public CropOrigin execute(CropOrigin data) {
        if (data.getAnnotation().getObjects().stream().anyMatch(layoutObject -> config.getBlackList().contains(layoutObject.getName()))) {
            invalidOrigins.add(data);
            pipelineContext.getPipelineManager().getPipeline(config.getFallback()).execute(data);
        }

        return nextStage != null ? nextStage.execute(data) : null;
    }

    @Override
    public void getResult() {
        log.info("{} result", NAME);
        invalidOrigins.forEach(cropOrigin -> log.info(cropOrigin.getName()));
    }
}
