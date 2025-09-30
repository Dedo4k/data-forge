package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ClassnameCountingStage extends BasePipelineStage<CropOrigin, ClassnameCountingStageConfig> {

    public static final String NAME = "classnameCountingStage";

    private final Map<String, Long> classes = new ConcurrentHashMap<>();

    public ClassnameCountingStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ClassnameCountingStageConfig getConfig() {
        return null;
    }

    @Override
    public CropOrigin execute(CropOrigin data) {
        data.getAnnotation().getObjects().forEach(layoutObject ->
                classes.compute(layoutObject.getName(), (s, aLong) -> aLong == null ? 1 : aLong + 1));

        return nextStage != null ? nextStage.execute(data) : null;
    }

    @Override
    public void getResult() {
        log.info("{} result", NAME);
        classes.forEach((key, value) -> log.info("{}: {}", key, value));
        log.info("Total: {}", classes.values().stream().mapToLong(Long::longValue).sum());
    }
}
