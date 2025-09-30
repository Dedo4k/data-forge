package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.BaseToken;
import lombok.ToString;

import java.awt.image.BufferedImage;

@ToString
public class Crop extends BaseToken<BufferedImage, CropVector, CropIdentifier, CropOrigin> {

    public Crop(BufferedImage crop, CropVector vector, CropIdentifier identifier, CropOrigin source) {
        super(crop, vector, identifier, source);
    }
}
