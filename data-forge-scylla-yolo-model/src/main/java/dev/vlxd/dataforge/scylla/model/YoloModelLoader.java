package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.datasource.DataSource;
import dev.vlxd.dataforge.core.model.ModelLoader;

import java.util.List;

public class YoloModelLoader implements ModelLoader<YoloDataOrigin> {

    @Override
    public List<YoloDataOrigin> loadModel(DataSource dataSource) {
        return List.of();
    }
}
