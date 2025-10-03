package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.pipeline.BasePipeline;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.executor.BasePipelineExecutor;
import dev.vlxd.dataforge.scylla.model.CropOrigin;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TokenPipelineExecutionStage extends BasePipelineStage<CropOrigin, TokenPipelineExecutionStageConfig> {

    public static final String NAME = "tokenPipelineExecutionStage";

    private static final String TARGET_PIPELINE_RESOLVE_ERROR = "Failed to resolve target pipeline. Target pipeline must support {} type but {} provided";

    public TokenPipelineExecutionStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
        TokenPipelineExecutionStageConfig.TokenPipelineExecutionStageConfigBuilder builder = TokenPipelineExecutionStageConfig.builder();
        Object target = configMap.get("target");
        if (target instanceof String value) {
            builder.target(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(CropOrigin data) {
        BasePipeline pipeline = context.getPipelineManager().getPipeline(config.getTarget());

        BasePipelineExecutor pipelineExecutor = context.getPipelineExecutorManager().getExecutorService(pipeline);

        pipelineExecutor.execute(data.loadTokens());

        accept(data);
    }
}
