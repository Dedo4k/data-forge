package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ObjectDeletionStage extends BasePipelineStage<CropOrigin, ObjectDeletionStageConfig> {

    public final static String NAME = "objectDeletionStage";

    private final static String CLASSNAMES_PARSE_ERROR = "Failed to parse config {}. classnames should consist of strings";

    public ObjectDeletionStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
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
    }

    @Override
    public void execute(CropOrigin data) {
        accept(data);
    }
}
