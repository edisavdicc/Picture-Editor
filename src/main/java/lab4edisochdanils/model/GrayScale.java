package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Grayscale conversion for images.
 * Converts a color image to grayscale by calculating the average of red, green and blue
 * for each pixel and setting all color channels to this average.
 * 
 * @author Edis and Danils
 * @version 1.0
 */
public class GrayScale implements IPixelProcessor{

    /**
     * Converts the image to grayscale.
     * Calculates the average of RGB values for each pixel using floating-point arithmetic
     * for correct rounding, and sets all color channels to the same value.
     * 
     * @param originalPixels the original pixel matrix
     * @return a new pixel matrix where the image is converted to grayscale
     */
    @Override
    public int[][] process(int[][] originalPixels) {
        int width = originalPixels.length;
        int height = originalPixels[0].length;
        int[][] grayPixels = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = originalPixels[x][y];

                int alpha = PixelConverter.getAlpha(pixel);
                int red = PixelConverter.getRed(pixel);
                int green = PixelConverter.getGreen(pixel);
                int blue = PixelConverter.getBlue(pixel);

                // beräkna medelvärde med flyttal för korrekt avrundning
                int gray = (int) Math.round((red + green + blue) / 3.0);

                // sätt alla färgkanaler till samma värde
                grayPixels[x][y] = PixelConverter.toArgbPixel(alpha, gray, gray, gray);
            }
        }
        return grayPixels;
    }
}