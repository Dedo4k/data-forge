package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public class FolderDataSource extends BaseDataSource {

    public FolderDataSource(String id, String uri) {
        super(id, DataSourceType.FOLDER, uri);
    }
}
