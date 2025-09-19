package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.ChunkIdentifier;

public class YoloDataIdentifier implements ChunkIdentifier<YoloDataChunk, YoloDataOrigin> {

    @Override
    public YoloDataChunk identify(YoloDataOrigin origin) {
        return null;
    }
}
