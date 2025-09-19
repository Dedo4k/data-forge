package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.DataChunk;
import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.Getter;

import java.util.List;

@Getter
public class DataChunkPipeline<D extends DataChunk<?>> implements Pipeline<D> {

    private final String id;
    private final String name;
    private final List<? extends PipelineStage<D, ?>> stages;

    public DataChunkPipeline(String id, String name, List<? extends PipelineStage<D, ?>> stages) {
        this.id = id;
        this.name = name;
        this.stages = stages;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addStage(PipelineStage<D, ? extends PipelineStageConfig> stage) {

    }

    @Override
    public void execute(D data) {
        for (PipelineStage<D, ?> stage : stages) {
            stage.execute(data);
        }
    }

    @Override
    public void execute(List<D> data) {
        for (D d : data) {
            execute(d);
        }
    }
}
