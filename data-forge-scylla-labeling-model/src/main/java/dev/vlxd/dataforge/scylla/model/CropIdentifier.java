package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.ChunkIdentifier;
import lombok.ToString;

import java.awt.image.BufferedImage;

@ToString
public class CropIdentifier implements ChunkIdentifier<Crop, CropOrigin> {

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    public CropIdentifier(int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    @Override
    public Crop identify(CropOrigin origin) {
        BufferedImage subImage = origin.getImage().getSubimage(xMin, yMin, xMax - xMin, yMax - yMin);
        return new Crop(subImage, this, origin);
    }
}
