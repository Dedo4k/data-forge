package dev.vlxd.dataforge.scylla.model.configuration;

import dev.vlxd.dataforge.core.pipeline.PipelineStageRegistry;
import dev.vlxd.dataforge.scylla.model.pipeline.stage.DuplicateRemoverStage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
public class YoloModelConfiguration {

    private final PipelineStageRegistry registry;

    @Autowired
    public YoloModelConfiguration(PipelineStageRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        registry.registerStage(DuplicateRemoverStage.NAME, DuplicateRemoverStage.class);
    }
}
