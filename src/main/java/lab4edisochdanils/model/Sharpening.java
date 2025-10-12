package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Sharpening-filter som gör kanter i bilden skarpare.
 *
 * Algoritm:
 * 1. Skapa en suddig version av bilden (blur)
 * 2. Beräkna skillnadsbilden (original - blurred)
 * 3. Addera skillnadsbilden till originalet (original + difference)
 * 4. Resultat: sharpened = original + (original - blurred) = 2*original - blurred
 */
public class Sharpening implements IPixelProcessor {

    private Blur blur;

    public Sharpening() {
        this.blur = new Blur();
    }

    /**
     * Applicerar sharpening-effekten på en pixelmatris.
     *
     * @param originalPixels den ursprungliga pixelmatrisen
     * @return en ny pixelmatris med sharpening-effekt applicerad
     */
    @Override
    public int[][] process(int[][] originalPixels) {
        int width = originalPixels.length;
        int height = originalPixels[0].length;

        // Steg 1: Skapa suddig version av bilden
        int[][] blurredPixels = blur.process(originalPixels);

        // Steg 2-4: För varje pixel, beräkna: original + (original - blurred)
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
     * Beräknar det skärpta värdet för en enskild pixel.
     * Formel: sharpened = original + (original - blurred)
     *
     * @param originalPixel originalbildens pixel
     * @param blurredPixel den suddiga bildens pixel
     * @return den skärpta pixeln som ett ARGB-värde
     */
    private int sharpenPixel(int originalPixel, int blurredPixel) {
        // Extrahera färgkomponenter från original
        int origRed = PixelConverter.getRed(originalPixel);
        int origGreen = PixelConverter.getGreen(originalPixel);
        int origBlue = PixelConverter.getBlue(originalPixel);
        int alpha = PixelConverter.getAlpha(originalPixel);

        // Extrahera färgkomponenter från blurred
        int blurRed = PixelConverter.getRed(blurredPixel);
        int blurGreen = PixelConverter.getGreen(blurredPixel);
        int blurBlue = PixelConverter.getBlue(blurredPixel);

        // Beräkna skillnad (original - blurred)
        int diffRed = origRed - blurRed;
        int diffGreen = origGreen - blurGreen;
        int diffBlue = origBlue - blurBlue;

        // Addera skillnaden till originalet (original + difference)
        int sharpRed = origRed + diffRed;      // = 2*origRed - blurRed
        int sharpGreen = origGreen + diffGreen;  // = 2*origGreen - blurGreen
        int sharpBlue = origBlue + diffBlue;    // = 2*origBlue - blurBlue

        // Begränsa värden till [0, 255] med if-satser
        if (sharpRed < 0) sharpRed = 0;
        if (sharpRed > 255) sharpRed = 255;

        if (sharpGreen < 0) sharpGreen = 0;
        if (sharpGreen > 255) sharpGreen = 255;

        if (sharpBlue < 0) sharpBlue = 0;
        if (sharpBlue > 255) sharpBlue = 255;

        return PixelConverter.toArgbPixel(alpha, sharpRed, sharpGreen, sharpBlue);
    }
}