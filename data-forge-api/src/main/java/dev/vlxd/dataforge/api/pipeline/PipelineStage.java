package dev.vlxd.dataforge.api.pipeline;

public interface PipelineStage<D, C extends PipelineStageConfig> {

    String getName();

    C getConfig();

    D execute(D data);

    boolean isSuccessful();
}
