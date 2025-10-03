package dev.vlxd.dataforge.scylla.model;


import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.core.exception.OriginLoadException;
import dev.vlxd.dataforge.scylla.model.configuration.JaxbConfiguration;
import dev.vlxd.dataforge.scylla.model.mapping.Annotation;
import dev.vlxd.dataforge.scylla.model.mapping.BndBox;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Builder
@ToString
@AllArgsConstructor
public class CropOrigin implements TokenOrigin<Crop, CropIdentifier> {

    private final String name;
    private final Path imagePath;
    private final Path annotationPath;

    @Nullable
    private BufferedImage image;
    @Nullable
    private Annotation annotation;

    public CropOrigin(String name, Path imagePath, Path annotationPath) {
        this.name = name;
        this.imagePath = imagePath;
        this.annotationPath = annotationPath;
    }

    @Override
    public void loadOrigin() {
        try {
            Unmarshaller unmarshaller;
            unmarshaller = new JaxbConfiguration().jaxbUnmarshaller();
            if (imagePath != null) {
                this.image = ImageIO.read(imagePath.toFile());
            }
            if (annotationPath != null) {
                this.annotation = (Annotation) unmarshaller.unmarshal(annotationPath.toFile());
            }
        } catch (JAXBException | IOException e) {
            throw new OriginLoadException("Failed to load data origin " + name, e);
        }
    }

    @Override
    public void unloadOrigin() {
        this.annotation = null;
        this.image = null;
    }

    @Override
    public boolean isLoaded() {
        return image != null || annotation != null;
    }

    @Override
    public Crop getToken(CropIdentifier identifier) {
        return identifier.identify(this);
    }

    @Override
    public List<Crop> getTokens(List<CropIdentifier> identifiers) {
        return identifiers.stream().map(this::getToken).toList();
    }

    @Override
    public List<Crop> loadTokens() {
        if (!isLoaded()) {
            loadOrigin();
        }
        if (annotation != null) {
            return annotation.getObjects().stream()
                    .map(layoutObject -> {
                        BndBox bndbox = layoutObject.getBndbox();
                        CropIdentifier cropIdentifier = new CropIdentifier(bndbox.getXMin(), bndbox.getYMin(), bndbox.getXMax(), bndbox.getYMax());

                        try {
                            Crop crop = getToken(cropIdentifier);

                            crop.getVector()
                                    .classname(layoutObject.getName())
                                    .difficult(layoutObject.getDifficult())
                                    .pose(layoutObject.getPose())
                                    .truncated(layoutObject.getTruncated());

                            return crop;
                        } catch (RasterFormatException e) {
                            log.warn("Failed to load data chunk {}", name, e);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
        return Collections.emptyList();
    }
}
