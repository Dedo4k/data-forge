package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.model.Model;

public class LabelingModel implements Model<LabelingModelLoader> {

    @Override
    public String getName() {
        return "scylla-labeling-model";
    }

    @Override
    public LabelingModelLoader getLoader() {
        return new LabelingModelLoader();
    }
}
