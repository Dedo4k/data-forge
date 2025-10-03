package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
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

    public ClassnameValidationStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
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
    }

    @Override
    public void execute(CropOrigin data) {
        if (data.getAnnotation() != null) {
            if (data.getAnnotation().getObjects().stream()
                    .anyMatch(layoutObject -> config.getBlackList().contains(layoutObject.getName()))) {
                reject(data);
                return;
            }
        }

        accept(data);
    }

    @Override
    public void getResult() {
        super.getResult();
        log.info("Invalid data: {}", invalidOrigins.size());
    }

    @Override
    public void reject(CropOrigin data) {
        super.reject(data);
        invalidOrigins.add(data);
        context.getPipelineManager().getPipeline(config.getFallback()).execute(data);
    }
}
