package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.DataOrigin;

import java.util.List;

public class YoloDataOrigin implements DataOrigin<YoloDataChunk, YoloDataIdentifier> {

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
    public YoloDataChunk getChunk(YoloDataIdentifier identifier) {
        return null;
    }

    @Override
    public List<YoloDataChunk> getChunks(List<YoloDataIdentifier> identifiers) {
        return List.of();
    }

    @Override
    public List<YoloDataChunk> loadChunks() {
        return List.of();
    }
}
