package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.DataSourceConfigurationProperties;
import dev.vlxd.dataforge.core.exception.ResourceValidationException;
import dev.vlxd.dataforge.core.exception.UnknownResourceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true")
public class DataSourceManager {

    private List<DataSourceConfigurationProperties> properties;
    private final Map<String, DataSource> resources = new HashMap<>();

    @Autowired
    public DataSourceManager(DataForgeConfigurationProperties config) {
        this.properties = config.getDataSources();
    }

    @Order(0)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("Data sources initialization...");
        resolveDataSources(properties);
    }

    private void resolveDataSources(List<DataSourceConfigurationProperties> resourcesConfigs) {
        log.debug("Resolving data sources...");
        resourcesConfigs.forEach(resourceConfig -> {
            DataSource datasource = resolveDataSource(resourceConfig);
            validateResource(datasource);
            resources.put(datasource.getId(), datasource);
        });
        log.debug("Data sources resolved: {}", resources.size());
    }

    private DataSource resolveDataSource(DataSourceConfigurationProperties resourceConfig) {
        switch (resourceConfig.getType()) {
            case FILE -> {
                return new FileDataSource(resourceConfig.getId(), resourceConfig.getUri());
            }
            case FOLDER -> {
                return new FolderDataSource(resourceConfig.getId(), resourceConfig.getUri());
            }
            case DATABASE -> {
                return new DatabaseDataSource(resourceConfig.getId(), resourceConfig.getUri());
            }
            default -> throw new UnknownResourceException("Unknown resource type " + resourceConfig.getType());
        }
    }

    private void validateResource(DataSource datasource) {
        final String uri = datasource.getUri();
        switch (datasource.getType()) {
            case FILE -> {
                if (Files.notExists(Path.of(uri))) {
                    throw new ResourceValidationException("File " + uri + " not exists");
                }
                if (!Files.isRegularFile(Path.of(uri))) {
                    throw new ResourceValidationException("Not a regular file " + uri);
                }
            }
            case FOLDER -> {
                if (Files.notExists(Path.of(uri))) {
                    throw new ResourceValidationException("Folder " + uri + " not exists");
                }
                if (!Files.isDirectory(Path.of(uri))) {
                    throw new ResourceValidationException("Not a folder " + uri);
                }
            }
            default -> {
            }
        }
    }
}
