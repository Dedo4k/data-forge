package dev.vlxd.dataforge.api;

public interface ChunkIdentifier<D extends DataChunk<?>, O extends DataOrigin<?, ?>> {

    D identify(O origin);
}
