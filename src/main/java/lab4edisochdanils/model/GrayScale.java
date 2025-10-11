package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

public class GrayScale implements IPixelProcessor{

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