package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasePipelineStage<D, C extends PipelineStageConfig> implements PipelineStage<D, C> {
    protected C config;
    protected BasePipelineStage<D, ? extends PipelineStageConfig> nextStage;
    protected PipelineContext pipelineContext;

    @Override
    public C getConfig() {
        return config;
    }
}
