package dev.vlxd.dataforge.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "data-forge")
public class DataForgeConfigurationProperties {

    /**
     * Enables/disables DataForge initialization
     */
    private boolean enabled = true;

    /**
     * Model name
     */
    private String model;

    /**
     * Number of pipeline executors
     */
    private int concurrency = 1;

    /**
     * List of  data sources
     */
    private List<DataSourceConfigurationProperties> dataSources;

    /**
     * List of pipelines
     */
    private List<PipelineConfigurationProperties> pipelines;
}
