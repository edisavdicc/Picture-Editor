package lab4edisochdanils.model;

import static lab4edisochdanils.utils.PixelConverter.*;

public class InvertColors implements IPixelProcessor {

    public int[][] process(int[][] originalPixels) {

        int width = originalPixels.length;
        int height = originalPixels[0].length;
        int[][] invertedMatrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = originalPixels[x][y];
                int alpha = getAlpha(pixel); // leave alpha (opacity) unchanged
                int invRed = 255 - getRed(pixel); // invert by subtracting from max value
                int invGreen = 255 - getGreen(pixel); // dito
                int invBlue = 255 - getBlue(pixel); // dito
                // create the new pixel and add to the pixel matrix
                int invertedPixel = toArgbPixel(alpha, invRed, invGreen, invBlue);
                invertedMatrix[x][y] = invertedPixel;
            }
        }

        return invertedMatrix;
    }
}
