package dev.vlxd.dataforge.core;

import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import dev.vlxd.dataforge.core.model.ModelManager;
import dev.vlxd.dataforge.core.pipeline.PipelineManager;
import dev.vlxd.dataforge.core.pipeline.executor.PipelineExecutorManager;
import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DataForgeContext {

    private final DataForgeConfigurationProperties properties;
    private final ObjectProvider<DataSourceManager> dataSourceManager;
    private final ObjectProvider<PipelineManager> pipelineManager;
    private final ObjectProvider<ModelManager> modelManager;
    private final ObjectProvider<PipelineExecutorManager> pipelineExecutorManager;

    @Autowired
    public DataForgeContext(DataForgeConfigurationProperties properties,
                            ObjectProvider<DataSourceManager> dataSourceManager,
                            ObjectProvider<PipelineManager> pipelineManager,
                            ObjectProvider<ModelManager> modelManager,
                            ObjectProvider<PipelineExecutorManager> pipelineExecutorManager) {
        this.properties = properties;
        this.dataSourceManager = dataSourceManager;
        this.pipelineManager = pipelineManager;
        this.modelManager = modelManager;
        this.pipelineExecutorManager = pipelineExecutorManager;
    }

    public DataSourceManager getDataSourceManager() {
        return dataSourceManager.getIfAvailable();
    }

    public PipelineManager getPipelineManager() {
        return pipelineManager.getIfAvailable();
    }

    public ModelManager getModelManager() {
        return modelManager.getIfAvailable();
    }

    public PipelineExecutorManager getPipelineExecutorManager() {
        return pipelineExecutorManager.getIfAvailable();
    }
}
