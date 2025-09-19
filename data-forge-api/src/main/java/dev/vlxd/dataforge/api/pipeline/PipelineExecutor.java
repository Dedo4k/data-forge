package dev.vlxd.dataforge.api.pipeline;

import dev.vlxd.dataforge.api.DataOrigin;

import java.util.List;

public interface PipelineExecutor {

    void execute(DataOrigin<?, ?> origin);

    void execute(List<DataOrigin<?, ?>> origins);
}
