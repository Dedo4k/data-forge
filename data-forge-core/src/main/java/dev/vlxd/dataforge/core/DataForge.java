package dev.vlxd.dataforge.core;

import dev.vlxd.dataforge.api.pipeline.Pipeline;
import dev.vlxd.dataforge.api.pipeline.PipelineStage;
import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.exception.ModelNotFoundException;
import dev.vlxd.dataforge.core.model.Model;
import dev.vlxd.dataforge.core.model.ModelLoader;
import dev.vlxd.dataforge.core.model.ModelRegistry;
import dev.vlxd.dataforge.core.pipeline.BasePipelineExecutor;
import dev.vlxd.dataforge.core.pipeline.PipelineManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unchecked"})
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
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
            String modelName = properties.getModel();

            Model model = modelRegistry.getModel(modelName);

            if (model == null) {
                throw new ModelNotFoundException("Model {} not found in registry", modelName);
            }

            List<DataSource> dataSources = datasourceManager.getPrimaryDataSources();

            ModelLoader loader = model.getLoader();

            List dataOrigins = loader.loadModel(dataSources);

            Pipeline primaryPipeline = pipelineManager.getPrimaryPipeline();

            BasePipelineExecutor pipelineExecutor = new BasePipelineExecutor(primaryPipeline, properties.getConcurrency());

            pipelineExecutor.execute(dataOrigins);

            pipelineManager.getPipelines().values().stream().map(Pipeline::getStages).flatMap(Collection::stream).forEach(PipelineStage::getResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
