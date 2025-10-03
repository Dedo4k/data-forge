package dev.vlxd.dataforge.api.pipeline;

import java.util.List;

public interface Pipeline<D, S extends PipelineStage<?, ?>> {

    String getId();

    String getName();

    boolean isPrimary();

    List<S> getStages();

    void addStage(S stage);

    void execute(D data);

    void execute(List<D> data);

    Class<D> getDataType();
}
