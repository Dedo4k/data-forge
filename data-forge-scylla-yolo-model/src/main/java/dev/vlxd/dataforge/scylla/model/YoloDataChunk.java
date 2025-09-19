package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.BaseDataChunk;

public class YoloDataChunk extends BaseDataChunk<String, YoloDataIdentifier, YoloDataOrigin> {

    public YoloDataChunk(String data, YoloDataIdentifier identifier, YoloDataOrigin origin) {
        super(data, identifier, origin);
    }
}
