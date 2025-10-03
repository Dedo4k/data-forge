package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.Token;
import dev.vlxd.dataforge.core.constant.PipelineTypes;

import java.util.List;

public class TokenPipeline<D extends Token<?, ?, ?, ?>, S extends BasePipelineStage<D, ?>> extends BasePipeline<D, S> {

    public TokenPipeline(String id, boolean primary, String name, List<S> stages, Class<D> dataType) {
        super(id, name, primary, stages, dataType, PipelineTypes.TOKEN);
    }
}
