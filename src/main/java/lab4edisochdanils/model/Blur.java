package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Blur filter using 3x3 matrix with weighted averages
 */
public class Blur implements IPixelProcessor {

    /**
     * Weight matrix for blur effect.
     */
    private static final int[][] weightMatrix = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };

    /**
     * Sum of all weights (divisor for averaging).
     */
    private static final double weightSum = 16.0;

    /**
     * Applies blur effect to image.
     * @param originalPixels original pixel matrix
     * @return blurred pixel matrix
     */
    @Override
    public int[][] process(int[][] originalPixels) {
        int width = originalPixels.length;
        int height = originalPixels[0].length;
        int[][] blurredPixels = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                blurredPixels[x][y] = blurPixel(originalPixels, x, y, width, height);
            }
        }

        return blurredPixels;
    }

    /**
     * Blurs a single pixel using weighted average of neighbors.
     * @param pixels pixel matrix
     * @param x pixel x-coordinate
     * @param y pixel y-coordinate
     * @param width image width
     * @param height image height
     * @return blurred pixel (ARGB)
     */
    private int blurPixel(int[][] pixels, int x, int y, int width, int height) {
        double redSum = 0, greenSum = 0, blueSum = 0;

        // Loop through 3x3 area around pixel
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Calculate neighbor's position
                int nx = x + dx;// Neighbor's x-coordinate
                int ny = y + dy;// Neighbor's y-coordinate

                // Check if neighbor is within image bounds
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    int weight = weightMatrix[dx + 1][dy + 1];  // Convert offset to matrix index
                    int pixel = pixels[nx][ny];

                    // Add weighted color values
                    redSum += PixelConverter.getRed(pixel) * weight;
                    greenSum += PixelConverter.getGreen(pixel) * weight;
                    blueSum += PixelConverter.getBlue(pixel) * weight;
                }
            }
        }

        // Calculate weighted averages
        int red = (int) Math.round(redSum / weightSum);
        int green = (int) Math.round(greenSum / weightSum);
        int blue = (int) Math.round(blueSum / weightSum);

        // alpha channel
        int alpha = PixelConverter.getAlpha(pixels[x][y]);

        return PixelConverter.toArgbPixel(alpha, red, green, blue);
    }
}