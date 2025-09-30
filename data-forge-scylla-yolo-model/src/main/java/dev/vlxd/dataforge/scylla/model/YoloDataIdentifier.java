package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.TokenIdentifier;

public class YoloDataIdentifier implements TokenIdentifier<YoloToken, YoloTokenOrigin> {

    @Override
    public YoloToken identify(YoloTokenOrigin origin) {
        return null;
    }
}
