package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.datasource.FolderDataSource;
import dev.vlxd.dataforge.core.model.ModelLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LabelingModelLoader implements ModelLoader<CropOrigin> {

    private final List<String> imageExtensions = List.of(".jpg", ".jpeg", ".png");
    private final List<String> annotationExtensions = List.of(".xml");

    @Override
    public List<CropOrigin> loadModel(DataSource source) {
        if (source instanceof FolderDataSource) {
            try (Stream<Path> paths = Files.walk(Path.of(source.getUri()))) {
                return paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.groupingBy(this::removeExtension, Collectors.collectingAndThen(Collectors.toList(), pathList -> {
                            CropOrigin.CropOriginBuilder builder = CropOrigin.builder();

                            Path first = pathList.getFirst();
                            builder.name(removeExtension(first));

                            for (Path path : pathList) {
                                String ext = getExtension(path);

                                if (imageExtensions.contains(ext)) {
                                    builder.imagePath(path);
                                } else if (annotationExtensions.contains(ext)) {
                                    builder.annotationPath(path);
                                }
                            }

                            return builder.build();
                        })))
                        .values().stream()
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return Collections.emptyList();
    }

    private String removeExtension(Path path) {
        String filename = path.toString();
        int index = filename.lastIndexOf('.');
        return index > 0 ? filename.substring(0, index) : "";
    }

    private String getExtension(Path path) {
        String filename = path.getFileName().toString();
        int index = filename.lastIndexOf('.');
        return index > 0 ? filename.substring(index).toLowerCase() : "";
    }


}
