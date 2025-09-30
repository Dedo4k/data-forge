package dev.vlxd.dataforge.core.configuration;

import dev.vlxd.dataforge.core.constant.DataSourceLoadStrategy;
import dev.vlxd.dataforge.core.constant.DataSourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceConfigurationProperties {

    /**
     * data source unique identifier
     */
    private String id;

    /**
     * primary data source is loaded on start up
     */
    private boolean primary;

    /**
     * data source type
     */
    private DataSourceType type;

    /**
     * data source uri identifier
     */
    private String uri;

    /**
     * load strategy
     */
    private DataSourceLoadStrategy loadStrategy = DataSourceLoadStrategy.VALIDATE;
}
