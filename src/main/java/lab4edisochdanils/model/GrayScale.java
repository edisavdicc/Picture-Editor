package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

public class GrayScale implements IPixelProcessor{

    @Override
    public int[][] process(int[][] originalPixels) {
        int height = originalPixels.length;
        int width = originalPixels[0].length;
        int[][] grayPixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = originalPixels[y][x];

                int alpha = PixelConverter.getAlpha(pixel);
                int red = PixelConverter.getRed(pixel);
                int green = PixelConverter.getGreen(pixel);
                int blue = PixelConverter.getBlue(pixel);

                // beräkna medelvärde med flyttal för korrekt avrundning
                int gray = (int) Math.round((red + green + blue) / 3.0);

                // sätt alla färgkanaler till samma värde
                grayPixels[y][x] = PixelConverter.toArgbPixel(alpha, gray, gray, gray);
            }
        }
        return grayPixels;
    }
}