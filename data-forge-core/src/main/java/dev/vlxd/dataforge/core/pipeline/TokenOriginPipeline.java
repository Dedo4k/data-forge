package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.core.constant.PipelineTypes;

import java.util.List;

public class TokenOriginPipeline<O extends TokenOrigin<?, ?>, S extends BasePipelineStage<O, ?>> extends BasePipeline<O, S> {

    public TokenOriginPipeline(String id, boolean primary, String name, List<S> stages, Class<O> dataType) {
        super(id, name, primary, stages, dataType, PipelineTypes.ORIGIN);
    }
}
