package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Sharpens edges using unsharp masking.
 */
public class Sharpening implements IPixelProcessor {

    private Blur blur;

    public Sharpening() {
        this.blur = new Blur();
    }

    /**
     * Sharpens the image.
     * @param originalPixels original pixel matrix
     * @return sharpened pixel matrix
     */
    @Override
    public int[][] process(int[][] originalPixels) {
        int width = originalPixels.length;
        int height = originalPixels[0].length;

        // Create blurred version
        int[][] blurredPixels = blur.process(originalPixels);

        // Apply sharpening formula: original + (original - blurred)
        int[][] sharpenedPixels = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                sharpenedPixels[x][y] = sharpenPixel(
                        originalPixels[x][y],
                        blurredPixels[x][y]
                );
            }
        }

        return sharpenedPixels;
    }

    /**
     * Sharpens a single pixel.
     * @param originalPixel original pixel
     * @param blurredPixel blurred pixel
     * @return sharpened pixel
     */
    private int sharpenPixel(int originalPixel, int blurredPixel) {
        // Extract original colors
        int origRed = PixelConverter.getRed(originalPixel);
        int origGreen = PixelConverter.getGreen(originalPixel);
        int origBlue = PixelConverter.getBlue(originalPixel);
        int alpha = PixelConverter.getAlpha(originalPixel);

        // Extract blurred colors
        int blurRed = PixelConverter.getRed(blurredPixel);
        int blurGreen = PixelConverter.getGreen(blurredPixel);
        int blurBlue = PixelConverter.getBlue(blurredPixel);

        // Calculate difference
        int diffRed = origRed - blurRed;
        int diffGreen = origGreen - blurGreen;
        int diffBlue = origBlue - blurBlue;

        // Apply sharpening
        int sharpRed = origRed + diffRed;
        int sharpGreen = origGreen + diffGreen;
        int sharpBlue = origBlue + diffBlue;
        
        if (sharpRed < 0) sharpRed = 0;
        if (sharpRed > 255) sharpRed = 255;

        if (sharpGreen < 0) sharpGreen = 0;
        if (sharpGreen > 255) sharpGreen = 255;

        if (sharpBlue < 0) sharpBlue = 0;
        if (sharpBlue > 255) sharpBlue = 255;

        return PixelConverter.toArgbPixel(alpha, sharpRed, sharpGreen, sharpBlue);
    }
}