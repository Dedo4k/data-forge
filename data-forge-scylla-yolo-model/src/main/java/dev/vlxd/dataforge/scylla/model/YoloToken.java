package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.BaseToken;

public class YoloToken extends BaseToken<String, YoloVector, YoloDataIdentifier, YoloTokenOrigin> {

    public YoloToken(String data, YoloVector vector, YoloDataIdentifier identifier, YoloTokenOrigin origin) {
        super(data, vector, identifier, origin);
    }
}
