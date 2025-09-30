package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public interface DataSource {

    String getId();

    boolean isPrimary();

    DataSourceType getType();

    String getUri();
}
