package dev.vlxd.dataforge.scylla.model.pipeline.stage.token;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoiseApplyingStageConfig implements PipelineStageConfig {
    private final Double stdDev;
    private final Double mean;
}
