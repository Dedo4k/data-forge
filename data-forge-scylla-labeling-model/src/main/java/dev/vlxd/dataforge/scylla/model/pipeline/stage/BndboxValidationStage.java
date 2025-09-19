package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.scylla.model.Crop;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class BndboxValidationStage implements PipelineStage<Crop, BndboxValidationStageConfig> {

    public static final String NAME = "bndboxValidationStage";
    private static final String MIN_WIDTH_PARSE_ERROR = "Failed to parse config {}. minWidth must be greater or equal to 0";
    private static final String MIN_HEIGHT_PARSE_ERROR = "Failed to parse config {}. minHeight must be greater or equal to 0";
    private static final String MAX_WIDTH_PARSE_ERROR = "Failed to parse config {}. maxWidth must be greater or equal to 0";
    private static final String MAX_HEIGHT_PARSE_ERROR = "Failed to parse config {}. maxHeight must be greater or equal to 0";
    private final BndboxValidationStageConfig config;

    public BndboxValidationStage(Map<String, Object> configMap) {
        BndboxValidationStageConfig.BndboxValidationStageConfigBuilder builder = new BndboxValidationStageConfig.BndboxValidationStageConfigBuilder();
        Object minWidth = configMap.get("minWidth");
        if (minWidth instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MIN_WIDTH_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MIN_WIDTH_PARSE_ERROR, configMap);
            }
            builder.withMinWidth(value);
        }
        Object minHeight = configMap.get("minHeight");
        if (minHeight instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MIN_HEIGHT_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MIN_HEIGHT_PARSE_ERROR, configMap);
            }
            builder.withMinHeight(value);
        }
        Object maxWidth = configMap.get("maxWidth");
        if (maxWidth instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MAX_WIDTH_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MAX_WIDTH_PARSE_ERROR, configMap);
            }
            builder.withMaxWidth(value);
        }
        Object maxHeight = configMap.get("maxHeight");
        if (maxHeight instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MAX_HEIGHT_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MAX_HEIGHT_PARSE_ERROR, configMap);
            }
            builder.withMaxHeight(value);
        }
        this.config = builder.build();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public BndboxValidationStageConfig getConfig() {
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
