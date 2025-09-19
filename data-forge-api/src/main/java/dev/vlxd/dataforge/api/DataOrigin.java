package dev.vlxd.dataforge.api;

import java.util.List;

public interface DataOrigin<D extends DataChunk<?>, I extends ChunkIdentifier<?, ?>> {

    void loadOrigin();

    void unloadOrigin();

    boolean isLoaded();

    D getChunk(I identifier);

    List<D> getChunks(List<I> identifiers);

    List<D> loadChunks();
}
