package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.Token;
import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenPipeline<D extends Token<?, ?, ?, ?>, S extends PipelineStage<D, ?>> implements Pipeline<D, S> {

    private final String id;
    private final boolean primary;
    private final String name;
    private final List<S> stages;

    public TokenPipeline(String id, boolean primary, String name, List<S> stages) {
        this.id = id;
        this.primary = primary;
        this.name = name;
        this.stages = stages;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public void addStage(S stage) {
        stages.add(stage);
    }

    @Override
    public void execute(D data) {
        stages.getFirst().execute(data);
    }

    @Override
    public void execute(List<D> data) {
        for (D d : data) {
            execute(d);
        }
    }
}
