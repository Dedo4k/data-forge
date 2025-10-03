package dev.vlxd.dataforge.api.pipeline;

public interface PipelineStage<D, C extends PipelineStageConfig> {

    String getName();

    C getConfig();

    void execute(D data);

    void getResult();

    Class<D> getDataType();
}
