package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ClassnameValidationStageConfig implements PipelineStageConfig {
    private final List<String> blackList;
    private final List<String> whiteList;
    private final String fallback;
}
