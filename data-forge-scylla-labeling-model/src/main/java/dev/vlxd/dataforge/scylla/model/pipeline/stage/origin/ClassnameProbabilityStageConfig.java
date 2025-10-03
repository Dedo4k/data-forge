package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClassnameProbabilityStageConfig implements PipelineStageConfig {
    private final String classname;
    private final Double probability;
    private final String dataSource;
}
