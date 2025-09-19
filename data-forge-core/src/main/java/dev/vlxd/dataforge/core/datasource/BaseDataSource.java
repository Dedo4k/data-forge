package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public abstract class BaseDataSource implements DataSource {

    private String id;
    private DataSourceType type;
    private String uri;

    public BaseDataSource(String id, DataSourceType type, String uri) {
        this.id = id;
        this.type = type;
        this.uri = uri;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DataSourceType getType() {
        return type;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
