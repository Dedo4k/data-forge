package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public class DatabaseDataSource extends BaseDataSource {

    public DatabaseDataSource(String id, boolean primary, String uri) {
        super(id, primary, DataSourceType.DATABASE, uri);
    }
}
