package dev.vlxd.dataforge.api.pipeline;

import dev.vlxd.dataforge.api.TokenOrigin;

import java.util.List;

public interface PipelineExecutor {

    void execute(TokenOrigin<?, ?> origin);

    void execute(List<TokenOrigin<?, ?>> origins);
}
