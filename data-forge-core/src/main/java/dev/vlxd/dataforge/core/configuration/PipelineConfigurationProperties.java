package dev.vlxd.dataforge.core.configuration;

import dev.vlxd.dataforge.core.constant.PipelineTypes;
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
     * pipeline type
     */
    private PipelineTypes type;

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
