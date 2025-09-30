package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public class FolderDataSource extends BaseDataSource {

    public FolderDataSource(String id, boolean primary, String uri) {
        super(id, primary, DataSourceType.FOLDER, uri);
    }
}
