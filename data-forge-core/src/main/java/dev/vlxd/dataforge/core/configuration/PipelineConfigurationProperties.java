package dev.vlxd.dataforge.core.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PipelineConfigurationProperties {
    /**
     * pipeline unique identifier
     */
    private String id;

    /**
     * pipeline name
     */
    private String name;

    /**
     * error pipeline id
     */
    private String errorPipeline;

    /**
     * list of stage configurations
     */
    private List<StageConfigurationProperties> stages;
}
