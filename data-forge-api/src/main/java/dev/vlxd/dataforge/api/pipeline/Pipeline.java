package dev.vlxd.dataforge.api.pipeline;

import java.util.List;

public interface Pipeline<D> {

    String getName();

    void addStage(PipelineStage<D, ? extends PipelineStageConfig> stage);

    void execute(D data);

    void execute(List<D> data);
}
