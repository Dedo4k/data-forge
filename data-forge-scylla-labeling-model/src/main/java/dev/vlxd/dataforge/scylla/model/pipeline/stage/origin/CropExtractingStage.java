package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.Crop;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import dev.vlxd.dataforge.scylla.model.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class CropExtractingStage extends BasePipelineStage<CropOrigin, CropExtractingStageConfig> {

    public static final String NAME = "cropExtractingStage";

    private static final String SAVE_CROP_ERROR = "Failed to save crop";

    private final Map<String, List<Crop>> failedCrops = new ConcurrentHashMap<>();

    public CropExtractingStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
        CropExtractingStageConfig.CropExtractingStageConfigBuilder builder = CropExtractingStageConfig.builder();
        Object dataSource = configMap.get("data-source");
        if (dataSource instanceof String value) {
            builder.dataSource(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(CropOrigin data) {
        DataSource dataSource = context.getDataSourceManager().getDataSources().get(config.getDataSource());
        String extension = FileUtils.getExtension(data.getImagePath());

        data.loadTokens().forEach(crop -> {
            Path target = Paths.get(dataSource.getUri(), crop.getVector().getClassname().getValue());
            Path cropPath = target.resolve(UUID.randomUUID() + "." + extension);
            try {
                Files.createDirectories(target);
                ImageIO.write(crop.getToken(), extension, cropPath.toFile());
            } catch (IOException e) {
                log.warn(SAVE_CROP_ERROR, e);
                failedCrops.compute(data.getName(), (s, crops) -> {
                    if (crops == null) {
                        return new CopyOnWriteArrayList<>(List.of(crop));
                    }
                    crops.add(crop);
                    return crops;
                });
            }
        });

        accept(data);
    }

    @Override
    public void getResult() {
        super.getResult();
        log.info("Failed crops: {}", failedCrops.size());
    }
}
