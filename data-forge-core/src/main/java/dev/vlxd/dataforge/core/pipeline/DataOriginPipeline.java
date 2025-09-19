package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.DataOrigin;
import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.Getter;

import java.util.List;

@Getter
public class DataOriginPipeline<O extends DataOrigin<?, ?>> implements Pipeline<O> {

    private final String id;
    private final String name;
    private final List<? extends PipelineStage<O, ?>> stages;

    public DataOriginPipeline(String id, String name, List<? extends PipelineStage<O, ?>> stages) {
        this.id = id;
        this.name = name;
        this.stages = stages;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addStage(PipelineStage<O, ? extends PipelineStageConfig> stage) {

    }

    @Override
    public void execute(O data) {

    }

    @Override
    public void execute(List<O> data) {

    }
}
