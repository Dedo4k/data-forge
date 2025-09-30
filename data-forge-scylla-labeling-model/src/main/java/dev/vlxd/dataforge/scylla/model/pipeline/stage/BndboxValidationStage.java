package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import dev.vlxd.dataforge.scylla.model.mapping.BndBox;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class BndboxValidationStage extends BasePipelineStage<CropOrigin, BndboxValidationStageConfig> {

    public static final String NAME = "bndboxValidationStage";

    private static final String MIN_WIDTH_PARSE_ERROR = "Failed to parse config {}. minWidth must be greater or equal to 0";
    private static final String MIN_HEIGHT_PARSE_ERROR = "Failed to parse config {}. minHeight must be greater or equal to 0";
    private static final String MAX_WIDTH_PARSE_ERROR = "Failed to parse config {}. maxWidth must be greater or equal to 0";
    private static final String MAX_HEIGHT_PARSE_ERROR = "Failed to parse config {}. maxHeight must be greater or equal to 0";

    private final List<CropOrigin> invalidOrigins = new CopyOnWriteArrayList<>();

    public BndboxValidationStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        BndboxValidationStageConfig.BndboxValidationStageConfigBuilder builder = BndboxValidationStageConfig.builder();
        Object minWidth = configMap.get("minWidth");
        if (minWidth instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MIN_WIDTH_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MIN_WIDTH_PARSE_ERROR, configMap);
            }
            builder.minWidth(value);
        }
        Object minHeight = configMap.get("minHeight");
        if (minHeight instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MIN_HEIGHT_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MIN_HEIGHT_PARSE_ERROR, configMap);
            }
            builder.minHeight(value);
        }
        Object maxWidth = configMap.get("maxWidth");
        if (maxWidth instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MAX_WIDTH_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MAX_WIDTH_PARSE_ERROR, configMap);
            }
            builder.maxWidth(value);
        }
        Object maxHeight = configMap.get("maxHeight");
        if (maxHeight instanceof Integer value) {
            if ((value).compareTo(0) < 0) {
                log.error(MAX_HEIGHT_PARSE_ERROR, configMap);
                throw new PipelineStageConfigParseException(MAX_HEIGHT_PARSE_ERROR, configMap);
            }
            builder.maxHeight(value);
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
    public BndboxValidationStageConfig getConfig() {
        return config;
    }

    @Override
    public CropOrigin execute(CropOrigin data) {
        if (data.getAnnotation().getObjects().stream().anyMatch(layoutObject -> !isBndBoxValid(layoutObject.getBndbox()))) {
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

    private boolean isBndBoxValid(BndBox bndBox) {
        int width = bndBox.getXMax() - bndBox.getXMin();
        int height = bndBox.getYMax() - bndBox.getYMin();
        if (config.getMaxWidth() != null && width > config.getMaxWidth()) {
            return false;
        }
        if (config.getMaxHeight() != null && height > config.getMaxHeight()) {
            return false;
        }
        if (config.getMinWidth() != null && width < config.getMinWidth()) {
            return false;
        }
        if (config.getMinHeight() != null && height < config.getMinHeight()) {
            return false;
        }
        return true;
    }
}
