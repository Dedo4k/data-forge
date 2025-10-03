package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.TokenIdentifier;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.image.BufferedImage;

@ToString
@AllArgsConstructor
public class CropIdentifier implements TokenIdentifier<Crop, CropOrigin> {

    private final int xMin;
    private final int yMin;
    private final int xMax;
    private final int yMax;

    @Override
    public Crop identify(CropOrigin origin) {
        BufferedImage subImage = origin.getImage().getSubimage(xMin, yMin, xMax - xMin, yMax - yMin);
        return new Crop(subImage, new CropVector(), this, origin);
    }
}
