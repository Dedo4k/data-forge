package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true")
public class PipelineStageRegistry {

    private final Map<String, Class<? extends PipelineStage<?, ?>>> stageRegistry = new HashMap<>();

    public Class<? extends PipelineStage<?, ?>> getStage(String name) {
        return stageRegistry.get(name);
    }

    public void registerStage(String name, Class<? extends PipelineStage<?, ?>> stageClass) {
        stageRegistry.put(name, stageClass);

        log.debug("{} registered as {} stage", stageClass.getName(), name);
    }
}
