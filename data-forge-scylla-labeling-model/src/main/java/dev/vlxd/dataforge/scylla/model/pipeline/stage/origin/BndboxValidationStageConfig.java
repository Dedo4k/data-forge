package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BndboxValidationStageConfig implements PipelineStageConfig {
    private final Integer minWidth;
    private final Integer minHeight;
    private final Integer maxWidth;
    private final Integer maxHeight;
    private final String fallback;
}
