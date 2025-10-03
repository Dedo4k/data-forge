package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.core.model.Model;

public class YoloModel implements Model<YoloToken, YoloTokenOrigin, YoloModelLoader> {

    @Override
    public String getName() {
        return "";
    }

    @Override
    public YoloModelLoader getLoader() {
        return null;
    }

    @Override
    public Class<YoloToken> getTokenType() {
        return null;
    }

    @Override
    public Class<YoloTokenOrigin> getOriginType() {
        return null;
    }
}
