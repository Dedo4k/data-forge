package dev.vlxd.dataforge.core.datasource;

import dev.vlxd.dataforge.core.constant.DataSourceType;

public abstract class BaseDataSource implements DataSource {

    private final String id;
    private final boolean primary;
    private final DataSourceType type;
    private final String uri;

    public BaseDataSource(String id, boolean primary, DataSourceType type, String uri) {
        this.id = id;
        this.primary = primary;
        this.type = type;
        this.uri = uri;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isPrimary() {
        return primary;
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
