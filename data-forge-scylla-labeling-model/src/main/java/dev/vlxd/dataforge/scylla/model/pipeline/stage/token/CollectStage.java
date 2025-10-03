package dev.vlxd.dataforge.scylla.model.pipeline.stage.token;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.exception.PipelineStageExecutionException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.Crop;
import dev.vlxd.dataforge.scylla.model.util.FileUtils;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

public class CollectStage extends BasePipelineStage<Crop, CollectStageConfig> {

    public static final String NAME = "tokenCollectStage";

    public CollectStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, Crop.class);
        CollectStageConfig.CollectStageConfigBuilder builder = CollectStageConfig.builder();
        Object dataSource = configMap.get("data-source");
        if (dataSource instanceof String value) {
            builder.dataSource(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(Crop data) {
        DataSource dataSource = context.getDataSourceManager().getDataSources().get(config.getDataSource());

        String extension = FileUtils.getExtension(data.getOrigin().getImagePath());

        Path imgTarget = Paths.get(dataSource.getUri(), UUID.randomUUID() + "." + extension);

        try {
            ImageIO.write(data.getToken(), extension, imgTarget.toFile());
        } catch (IOException e) {
            throw new PipelineStageExecutionException("Failed to write image", e);
        }

        accept(data);
    }
}
