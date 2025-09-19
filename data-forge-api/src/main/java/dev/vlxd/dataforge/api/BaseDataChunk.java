package dev.vlxd.dataforge.api;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDataChunk<D, I extends ChunkIdentifier<?, ?>, O extends DataOrigin<?, ?>> implements DataChunk<D> {

    protected D data;
    protected I identifier;
    protected O origin;
    protected final Map<String, Feature<?>> features = new HashMap<>();

    public BaseDataChunk(D data, I identifier, O origin) {
        this.data = data;
        this.identifier = identifier;
        this.origin = origin;
    }

    @Override
    public D getData() {
        return data;
    }

    @Override
    public I getIdentifier() {
        return identifier;
    }

    @Override
    public O getOrigin() {
        return origin;
    }

    @Override
    public Map<String, Feature<?>> getFeatures() {
        return features;
    }

    @Override
    public DataChunk<D> addFeature(Feature<?> feature) {
        features.put(feature.getName(), feature);
        return this;
    }

    @Override
    public <T> DataChunk<D> addFeature(String name, T value) {
        features.put(name, new BaseFeature<>(name, value));
        return this;
    }
}
