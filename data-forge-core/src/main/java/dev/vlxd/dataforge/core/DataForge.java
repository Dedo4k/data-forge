package dev.vlxd.dataforge.core;

import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.model.Model;
import dev.vlxd.dataforge.core.model.ModelLoader;
import dev.vlxd.dataforge.core.model.ModelRegistry;
import dev.vlxd.dataforge.core.pipeline.BasePipelineExecutor;
import dev.vlxd.dataforge.core.pipeline.DataChunkPipeline;
import dev.vlxd.dataforge.core.pipeline.PipelineManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true")
public class DataForge {

    private final ModelRegistry modelRegistry;
    private final DataSourceManager datasourceManager;
    private final PipelineManager pipelineManager;
    private final DataForgeConfigurationProperties properties;

    @Autowired
    public DataForge(ModelRegistry modelRegistry,
                     DataSourceManager datasourceManager,
                     PipelineManager pipelineManager,
                     DataForgeConfigurationProperties properties) {
        this.modelRegistry = modelRegistry;
        this.datasourceManager = datasourceManager;
        this.pipelineManager = pipelineManager;
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("DataForge initialization...");
        try {
            Model model = modelRegistry.getModel("scylla-labeling-model");

            DataSource datasource = datasourceManager.getResources().get("dataset-src");

            ModelLoader loader = model.getLoader();

            List dataOrigins = loader.loadModel(datasource);

            DataChunkPipeline startPipeline = pipelineManager.getPipeline(properties.getEntrypoint());

            BasePipelineExecutor pipelineExecutor = new BasePipelineExecutor(startPipeline, properties.getConcurrency());

            pipelineExecutor.execute(dataOrigins);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
