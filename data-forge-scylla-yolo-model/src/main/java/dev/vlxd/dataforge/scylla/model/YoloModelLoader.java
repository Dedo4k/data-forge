package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.model.ModelLoader;

import java.util.List;

public class YoloModelLoader implements ModelLoader<YoloTokenOrigin> {

    @Override
    public List<YoloTokenOrigin> loadModel(DataSource dataSource) {
        return List.of();
    }

    @Override
    public List<YoloTokenOrigin> loadModel(List<DataSource> dataSources) {
        return List.of();
    }
}
