package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ClassnameRoutingStageConfig implements PipelineStageConfig {

    private final List<PipelineRoute> routes;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PipelineRoute {
        private final String target;
        private final List<String> classnames;
    }
}
