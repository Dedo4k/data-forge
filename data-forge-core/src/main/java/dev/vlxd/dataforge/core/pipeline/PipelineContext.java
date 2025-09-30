package dev.vlxd.dataforge.core.pipeline;

import dev.vlxd.dataforge.core.datasource.DataSourceManager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PipelineContext {

    private final PipelineManager pipelineManager;
    private final DataSourceManager dataSourceManager;

    @Autowired
    public PipelineContext(PipelineManager pipelineManager, DataSourceManager dataSourceManager) {
        this.pipelineManager = pipelineManager;
        this.dataSourceManager = dataSourceManager;
    }
}
