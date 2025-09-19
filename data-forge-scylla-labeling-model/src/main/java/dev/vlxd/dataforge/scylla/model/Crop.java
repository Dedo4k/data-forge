package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.BaseDataChunk;

import java.awt.image.BufferedImage;

public class Crop extends BaseDataChunk<BufferedImage, CropIdentifier, CropOrigin> {

    public Crop(BufferedImage crop, CropIdentifier identifier, CropOrigin source) {
        super(crop, identifier, source);
    }

    @Override
    public String toString() {
        return "Crop{" +
                "data=" + data +
                ", identifier=" + identifier +
                ", origin=" + origin +
                ", features=" + features +
                '}';
    }
}
