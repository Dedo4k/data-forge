package dev.vlxd.dataforge.core.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StageConfigurationProperties {
    /**
     * pipeline stage name
     */
    private String name;

    /**
     * stage processor name
     */
    private String processor;

    /**
     * stage config options
     */
    private Map<String, Object> config;
}
