package dev.vlxd.dataforge.api;

import java.util.Map;

public interface DataChunk<D> {

    D getData();

    ChunkIdentifier<?, ?> getIdentifier();

    DataOrigin<?, ?> getOrigin();

    Map<String, Feature<?>> getFeatures();

    DataChunk<D> addFeature(Feature<?> feature);

    <T> DataChunk<D> addFeature(String name, T value);
}
