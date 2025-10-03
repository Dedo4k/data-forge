package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.core.constant.PipelineTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public abstract class BasePipeline<D, S extends BasePipelineStage<D, ?>> implements Pipeline<D, S> {
    protected final String id;
    protected final String name;
    protected final Boolean primary;
    protected final List<S> stages;
    protected final Class<D> dataType;
    protected final PipelineTypes type;

    protected BasePipeline(String id, String name, Boolean primary, List<S> stages, Class<D> dataType, PipelineTypes type) {
        this.id = id;
        this.name = name;
        this.primary = primary;
        this.stages = stages;
        this.dataType = dataType;
        this.type = type;
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
    public void execute(D data) {
        stages.getFirst().execute(data);
    }

    @Override
    public void execute(List<D> data) {
        for (D d : data) {
            execute(d);
        }
    }

    @Override
    public Class<D> getDataType() {
        return dataType;
    }
}
