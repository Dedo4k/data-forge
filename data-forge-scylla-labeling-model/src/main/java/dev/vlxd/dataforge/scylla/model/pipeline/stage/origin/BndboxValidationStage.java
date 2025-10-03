package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
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

    public BndboxValidationStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
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
    }

    @Override
    public void execute(CropOrigin data) {
        if (data.getAnnotation() != null) {
            if (data.getAnnotation().getObjects().stream().anyMatch(layoutObject -> !isBndBoxValid(layoutObject.getBndbox()))) {
                reject(data);
                return;
            }
        }

        accept(data);
    }

    @Override
    public void reject(CropOrigin data) {
        super.reject(data);
        invalidOrigins.add(data);
        context.getPipelineManager().getPipeline(config.getFallback()).execute(data);
    }

    @Override
    public void getResult() {
        super.getResult();
        log.info("Invalid data: {}", invalidOrigins.size());
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
