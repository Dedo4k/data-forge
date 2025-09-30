package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.TokenOrigin;

import java.util.List;

public class YoloTokenOrigin implements TokenOrigin<YoloToken, YoloDataIdentifier> {

    @Override
    public void loadOrigin() {

    }

    @Override
    public void unloadOrigin() {

    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public YoloToken getToken(YoloDataIdentifier identifier) {
        return null;
    }

    @Override
    public List<YoloToken> getTokens(List<YoloDataIdentifier> identifiers) {
        return List.of();
    }

    @Override
    public List<YoloToken> loadTokens() {
        return List.of();
    }
}
