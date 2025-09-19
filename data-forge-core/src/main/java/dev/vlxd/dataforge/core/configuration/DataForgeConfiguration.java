package dev.vlxd.dataforge.core.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties({DataForgeConfigurationProperties.class})
public class DataForgeConfiguration {

}
