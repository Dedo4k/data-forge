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
     * primary pipeline is the entrypoint for model processing
     */
    private boolean primary;

    /**
     * pipeline name
     */
    private String name;

    /**
     * list of stage configurations
     */
    private List<StageConfigurationProperties> stages;
}
