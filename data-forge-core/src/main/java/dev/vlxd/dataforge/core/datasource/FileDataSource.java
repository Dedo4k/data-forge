package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public class FileDataSource extends BaseDataSource {

    public FileDataSource(String id, boolean primary, String uri) {
        super(id, primary, DataSourceType.FILE, uri);
    }
}
