package dev.vlxd.dataforge.scylla.model.pipeline.stage.origin;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.exception.PipelineStageExecutionException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import dev.vlxd.dataforge.scylla.model.configuration.JaxbConfiguration;
import dev.vlxd.dataforge.scylla.model.util.FileUtils;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CollectStage extends BasePipelineStage<CropOrigin, CollectStageConfig> {

    public static final String NAME = "collectStage";

    public CollectStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, CropOrigin.class);
        CollectStageConfig.CollectStageConfigBuilder builder = CollectStageConfig.builder();
        Object dataSource = configMap.get("data-source");
        if (dataSource instanceof String value) {
            builder.dataSource(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(CropOrigin data) {
        DataSource dataSource = context.getDataSourceManager().getDataSources().get(config.getDataSource());

        Path imgSrc = data.getImagePath();
        Path imgTarget = Paths.get(dataSource.getUri(), imgSrc.getFileName().toString());

        try {
            if (data.getImage() != null) {
                ImageIO.write(data.getImage(), FileUtils.getExtension(imgSrc), imgTarget.toFile());
            }
        } catch (IOException e) {
            throw new PipelineStageExecutionException("Failed to write image", e);
        }

        Path annotationSrc = data.getAnnotationPath();
        Path annotationTarget = Paths.get(dataSource.getUri(), annotationSrc.getFileName().toString());

        try {
            Marshaller marshaller = new JaxbConfiguration().jaxbMarshaller();
            marshaller.marshal(data.getAnnotation(), annotationTarget.toFile());
        } catch (JAXBException e) {
            throw new PipelineStageExecutionException("Failed to write annotation", e);
        }

        accept(data);
    }
}
