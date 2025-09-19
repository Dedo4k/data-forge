package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.api.DataOrigin;
import dev.vlxd.dataforge.core.datasource.DataSource;

import java.util.List;

public interface ModelLoader<O extends DataOrigin<?, ?>> {

    List<O> loadModel(DataSource dataSource);
}
