package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import dev.vlxd.dataforge.core.DataForgeContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public abstract class BasePipelineStage<D, C extends PipelineStageConfig> implements PipelineStage<D, C> {
    protected final String name;
    protected final DataForgeContext context;
    protected final Class<D> dataType;
    protected C config;
    protected BasePipelineStage<D, ? extends PipelineStageConfig> nextStage;
    private AtomicLong acceptCounter;
    private AtomicLong cancelCounter;

    public BasePipelineStage(String name, DataForgeContext context, Class<D> dataType) {
        this.name = name;
        this.context = context;
        this.dataType = dataType;
        this.acceptCounter = new AtomicLong();
        this.cancelCounter = new AtomicLong();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public C getConfig() {
        return config;
    }

    @Override
    public void execute(D data) {
        accept(data);
    }

    @Override
    public void getResult() {
        log.info("STAGE {} result", name);
        log.info("Data accepted: {}, canceled: {}", acceptCounter.get(), cancelCounter.get());
    }

    public void accept(D data) {
        acceptCounter.getAndIncrement();
        if (nextStage != null) {
            nextStage.execute(data);
        }
    }

    public void reject(D data) {
        cancelCounter.getAndIncrement();
    }
}
