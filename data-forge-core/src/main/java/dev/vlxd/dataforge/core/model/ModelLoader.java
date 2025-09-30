package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.api.TokenOrigin;
import dev.vlxd.dataforge.core.datasource.DataSource;

import java.util.List;

public interface ModelLoader<O extends TokenOrigin<?, ?>> {

    List<O> loadModel(DataSource dataSource);

    List<O> loadModel(List<DataSource> dataSources);
}
