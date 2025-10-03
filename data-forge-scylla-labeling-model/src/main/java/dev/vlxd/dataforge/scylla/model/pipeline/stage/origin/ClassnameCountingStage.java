package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ClassnameCountingStage extends BasePipelineStage<CropOrigin, ClassnameCountingStageConfig> {

    public static final String NAME = "classnameCountingStage";

    private final Map<String, Long> classes = new ConcurrentHashMap<>();

    public ClassnameCountingStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
    }

    @Override
    public void execute(CropOrigin data) {
        if (data.getAnnotation() != null) {
            data.getAnnotation().getObjects().forEach(layoutObject ->
                    classes.compute(layoutObject.getName(), (s, aLong) -> aLong == null ? 1 : aLong + 1));
        }

        accept(data);
    }

    @Override
    public void getResult() {
        super.getResult();
        log.info("Classes found:");
        classes.forEach((key, value) -> log.info("{}: {}", key, value));
        log.info("Total: {}", classes.values().stream().mapToLong(Long::longValue).sum());
    }
}
