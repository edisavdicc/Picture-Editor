package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Window/Level contrast adjustment.
 */
public class WindowLevel implements IPixelProcessor {

    private int level;
    private int window;
    
    public WindowLevel() {
        this.level = 0;
        this.window = 0;
    }

    /**
     * Constructor with parameters.
     * @param level lower ceiling (0-255)
     * @param window range size (1-255)
     */
    public WindowLevel(int level, int window) {
        this.level = level;
        this.window = window;
    }

    /**
     * Sets level value.
     * @param level value (0-255)
     */
    public void setLevel(int level) {
        if (level < 0) level = 0;
        if (level > 255) level = 255;
        this.level = level;
    }

    /**
     * Sets window value.
     * @param window value (1-255)
     */
    public void setWindow(int window) {
        if (window < 1) window = 1;
        if (window > 255) window = 255;
        this.window = window;
    }

    /**
     * Gets level value.
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets window value.
     * @return window
     */
    public int getWindow() {
        return window;
    }

    /**
     * Applies window/level adjustment.
     * @param originalPixels original pixel matrix
     * @return adjusted pixel matrix
     */
    @Override
    public int[][] process(int[][] originalPixels) {
        int width = originalPixels.length;
        int height = originalPixels[0].length;
        int[][] adjustedPixels = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                adjustedPixels[x][y] = adjustPixel(originalPixels[x][y]);
            }
        }

        return adjustedPixels;
    }

    /**
     * Adjusts a single pixel.
     * @param pixel original pixel
     * @return adjusted pixel
     */
    private int adjustPixel(int pixel) {
        // Extract colors
        int red = PixelConverter.getRed(pixel);
        int green = PixelConverter.getGreen(pixel);
        int blue = PixelConverter.getBlue(pixel);
        int alpha = PixelConverter.getAlpha(pixel);

        // Calculate grayscale intensity
        int intensity = (red + green + blue) / 3;

        // Apply window/level mapping
        int newIntensity = applyWindowLevel(intensity);

        return PixelConverter.toArgbPixel(alpha, newIntensity, newIntensity, newIntensity);
    }

    /**
     * Applies window/level mapping.
     * @param intensity original intensity (0-255)
     * @return mapped intensity (0-255)
     */
    private int applyWindowLevel(int intensity) {
        if (intensity < level) {
            return 0;
        }

        int upperBound = level + window;
        if (intensity > upperBound) {
            return 255;
        }
        
        double k = 255.0 / window;
        double output = k * (intensity - level);

        return (int) Math.round(output);
    }
}