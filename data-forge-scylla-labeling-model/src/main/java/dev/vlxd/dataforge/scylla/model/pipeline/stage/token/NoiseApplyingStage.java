package dev.vlxd.dataforge.scylla.model.pipeline.stage.token;

import dev.vlxd.dataforge.core.DataForgeContext;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.scylla.model.Crop;
import dev.vlxd.dataforge.scylla.model.util.ImageNoiseUtils;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class NoiseApplyingStage extends BasePipelineStage<Crop, NoiseApplyingStageConfig> {

    public static final String NAME = "noiseApplyingStage";

    public NoiseApplyingStage(Map<String, Object> configMap, DataForgeContext context) {
        super(NAME, context, Crop.class);
        NoiseApplyingStageConfig.NoiseApplyingStageConfigBuilder builder = NoiseApplyingStageConfig.builder();
        Object stdDev = configMap.get("stdDev");
        if (stdDev instanceof Double value) {
            builder.stdDev(value);
        }
        Object mean = configMap.get("mean");
        if (mean instanceof Double value) {
            builder.mean(value);
        }
        this.config = builder.build();
    }

    @Override
    public void execute(Crop data) {
        BufferedImage image = data.getToken();

        BufferedImage noisyImage = new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType()
        );

        ExecutorService executorService = Executors.newFixedThreadPool(128);
        Phaser phaser = new Phaser(1);

        long totalPixels = (long) image.getWidth() * image.getHeight();
        int maxBlocks = 65535;
        int minBlockSize = 1;
        int blockSize = Math.max(minBlockSize, (int) Math.ceil((double) totalPixels / maxBlocks));
        int blocksX = (image.getWidth() + blockSize - 1) / blockSize;
        int blocksY = (image.getHeight() + blockSize - 1) / blockSize;
        for (int blockY = 0; blockY < blocksY; blockY++) {
            for (int blockX = 0; blockX < blocksX; blockX++) {
                int startX = blockX * blockSize;
                int startY = blockY * blockSize;
                int endX = Math.min(startX + blockSize, image.getWidth());
                int endY = Math.min(startY + blockSize, image.getHeight());

                phaser.register();
                executorService.submit(() -> {
                    try {
                        processBlock(image, noisyImage, startX, startY, endX, endY);
                    } finally {
                        phaser.arriveAndDeregister();
                    }
                });
            }
        }

        phaser.arriveAndAwaitAdvance();
        executorService.shutdown();

        accept(new Crop(noisyImage, data.getVector(), data.getIdentifier(), data.getOrigin()));
    }

    private void processBlock(BufferedImage source, BufferedImage target,
                              int startX, int startY, int endX, int endY) {
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                task(source, target, x, y);
            }
        }
    }

    void task(BufferedImage image, BufferedImage noisyImage, int x, int y) {
        int rgb = image.getRGB(x, y);

        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        double stdDev = config.getStdDev();
        double mean = config.getMean();
        red = Math.clamp(red + Math.round(ImageNoiseUtils.gaussianNoise(mean, stdDev)), 0, 255);
        green = Math.clamp(green + Math.round(ImageNoiseUtils.gaussianNoise(mean, stdDev)), 0, 255);
        blue = Math.clamp(blue + Math.round(ImageNoiseUtils.gaussianNoise(mean, stdDev)), 0, 255);

        int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        noisyImage.setRGB(x, y, newRgb);
    }
}
