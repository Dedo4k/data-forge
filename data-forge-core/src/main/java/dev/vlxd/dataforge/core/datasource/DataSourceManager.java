package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.configuration.DataSourceConfigurationProperties;
import dev.vlxd.dataforge.core.constant.DataSourceLoadStrategy;
import dev.vlxd.dataforge.core.exception.DataSourceValidationException;
import dev.vlxd.dataforge.core.exception.UnknownResourceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceManager {

    private final List<DataSourceConfigurationProperties> properties;

    private Map<String, DataSource> dataSources;

    @Autowired
    public DataSourceManager(DataForgeConfigurationProperties config) {
        this.properties = config.getDataSources();
    }

    @Order(0)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.debug("Data sources initialization...");
        dataSources = Collections.unmodifiableMap(resolveDataSources(properties));
    }

    private Map<String, DataSource> resolveDataSources(List<DataSourceConfigurationProperties> configs) {
        Map<String, DataSource> dataSources = new HashMap<>();
        log.debug("Resolving data sources...");
        configs.forEach(resourceConfig -> {
            DataSource dataSource = resolveDataSource(resourceConfig);
            validateDataSource(dataSource, resourceConfig);
            dataSources.put(dataSource.getId(), dataSource);
        });
        log.debug("Data sources resolved: {}", dataSources.size());
        return dataSources;
    }

    public List<DataSource> getPrimaryDataSources() {
        return dataSources.values().stream().filter(DataSource::isPrimary).toList();
    }

    private DataSource resolveDataSource(DataSourceConfigurationProperties config) {
        boolean primary = config.isPrimary();
        switch (config.getType()) {
            case FILE -> {
                return new FileDataSource(config.getId(), primary, config.getUri());
            }
            case FOLDER -> {
                return new FolderDataSource(config.getId(), primary, config.getUri());
            }
            case DATABASE -> {
                return new DatabaseDataSource(config.getId(), primary, config.getUri());
            }
            default -> throw new UnknownResourceException("Unknown data source type " + config.getType());
        }
    }

    private void validateDataSource(DataSource dataSource, DataSourceConfigurationProperties config) {
        final String uri = dataSource.getUri();
        final Path path = Path.of(uri);
        switch (dataSource.getType()) {
            case FILE -> {
                if (Files.notExists(path)) {
                    if (DataSourceLoadStrategy.VALIDATE_CREATE.equals(config.getLoadStrategy())) {
                        try {
                            Files.createFile(path);
                        } catch (IOException e) {
                            throw new DataSourceValidationException("Failed to create file {}", path.toString(), e);
                        }
                    } else {
                        throw new DataSourceValidationException("File " + uri + " not exists");
                    }
                }
                if (!Files.isRegularFile(path)) {
                    throw new DataSourceValidationException("Not a regular file " + uri);
                }
            }
            case FOLDER -> {
                if (Files.notExists(path)) {
                    if (DataSourceLoadStrategy.VALIDATE_CREATE.equals(config.getLoadStrategy())) {
                        try {
                            Files.createDirectories(path);
                        } catch (IOException e) {
                            throw new DataSourceValidationException("Failed to create directories {}", path.toString(), e);
                        }
                    } else {
                        throw new DataSourceValidationException("Folder " + uri + " not exists");
                    }
                }
                if (!Files.isDirectory(path)) {
                    throw new DataSourceValidationException("Not a folder " + uri);
                }
            }
            default -> {
            }
        }
    }
}
