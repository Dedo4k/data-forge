package dev.vlxd.dataforge.api.pipeline;

import java.util.List;

public interface PipelineExecutor<D> {

    void execute(D data);

    void execute(List<D> data);
}
