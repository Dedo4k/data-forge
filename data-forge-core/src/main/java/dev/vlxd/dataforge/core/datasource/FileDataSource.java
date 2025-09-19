package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public class FileDataSource extends BaseDataSource {

    public FileDataSource(String id, String uri) {
        super(id, DataSourceType.FILE, uri);
    }
}
