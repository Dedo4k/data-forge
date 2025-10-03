package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.model.Model;
import dev.vlxd.dataforge.core.model.ModelRegistry;
import org.springframework.stereotype.Component;

@Component
public class LabelingModel implements Model<Crop, CropOrigin, LabelingModelLoader> {

    public LabelingModel(ModelRegistry modelRegistry) {
        modelRegistry.addModel(this);
    }

    @Override
    public String getName() {
        return "scylla-labeling-model";
    }

    @Override
    public LabelingModelLoader getLoader() {
        return new LabelingModelLoader();
    }

    @Override
    public Class<Crop> getTokenType() {
        return Crop.class;
    }

    @Override
    public Class<CropOrigin> getOriginType() {
        return CropOrigin.class;
    }
}
