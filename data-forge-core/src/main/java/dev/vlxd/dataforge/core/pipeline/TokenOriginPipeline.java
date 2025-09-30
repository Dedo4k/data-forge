package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenOriginPipeline<O extends TokenOrigin<?, ?>, S extends PipelineStage<O, ?>> implements Pipeline<O, S> {

    private final String id;
    private final boolean primary;
    private final String name;
    private final List<S> stages;

    public TokenOriginPipeline(String id, boolean primary, String name, List<S> stages) {
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
    public List<S> getStages() {
        return stages;
    }

    @Override
    public void addStage(S stage) {
        stages.add(stage);
    }

    @Override
    public void execute(O data) {
        stages.getFirst().execute(data);
    }

    @Override
    public void execute(List<O> data) {
        for (O o : data) {
            execute(o);
        }
    }
}
