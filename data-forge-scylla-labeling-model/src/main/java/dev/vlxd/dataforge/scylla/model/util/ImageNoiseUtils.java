package dev.vlxd.dataforge.scylla.model.util;

import java.util.Random;

public class ImageNoiseUtils {
    private static final Random RANDOM = new Random();

    public static double gaussianNoise(double mean, double stdDev) {
        return RANDOM.nextGaussian() * stdDev + mean;
    }
}
