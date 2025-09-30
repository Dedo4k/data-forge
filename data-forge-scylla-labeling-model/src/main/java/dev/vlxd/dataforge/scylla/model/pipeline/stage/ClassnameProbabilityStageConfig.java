package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ClassnameProbabilityStageConfig implements PipelineStageConfig {
    private final String classname;
    private final BigDecimal probability;
    private final String dataSource;
}
