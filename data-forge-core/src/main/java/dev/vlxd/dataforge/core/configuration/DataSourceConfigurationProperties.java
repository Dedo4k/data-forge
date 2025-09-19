package dev.vlxd.dataforge.core.configuration;

import dev.vlxd.dataforge.core.constant.DataSourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceConfigurationProperties {
    /**
     * source unique identifier
     */
    private String id;

    /**
     * source type
     */
    private DataSourceType type;

    /**
     * source uri identifier
     */
    private String uri;
}
