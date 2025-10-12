package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Blur-filter som gör en bild suddigare.
 * Använder en 3x3 matris med viktade medelvärden
 * för att blanda varje pixel med sina 8 närmaste grannar.
 */
public class Blur implements IPixelProcessor {

    /**
     * Viktmatris för blur-effekten.
     * Varje pixel påverkas av sina grannar enligt dessa vikter:
     * - Centrum (pixeln själv): vikt 4
     * - Direkt intill (upp/ner/vänster/höger): vikt 2
     * - Diagonalt (hörnen): vikt 1
     */
    private static final int[][] weighedMatrix = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };

    /**
     * Summan av alla vikter i matrisen (1+2+1+2+4+2+1+2+1 = 16).
     * Används som divisor för att beräkna medelvärdet.
     */
    private static final double weighedAverage = 16.0;

    /**
     * Applicerar blur-effekten på en pixelmatris.
     *
     * @param originalPixels den ursprungliga pixelmatrisen
     * @return en ny pixelmatris med blur-effekt applicerad
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
     * Beräknar det suddiga värdet för en enskild pixel genom att
     * vikta och blanda den med sina 8 närmaste grannar.
     *
     * @param pixels den ursprungliga pixelmatrisen
     * @param x pixelns x-koordinat
     * @param y pixelns y-koordinat
     * @param width bildens bredd
     * @param height bildens höjd
     * @return den nya, suddiga pixeln som ett ARGB-värde
     */
    private int blurPixel(int[][] pixels, int x, int y, int width, int height) {
        double redSum = 0, greenSum = 0, blueSum = 0;

        // Gå igenom ett 3x3 område runt pixeln
        for (int dx = -1; dx <= 1; dx++) {        // dx: förflyttning i x-led (-1, 0, 1)
            for (int dy = -1; dy <= 1; dy++) {    // dy: förflyttning i y-led (-1, 0, 1)

                // Beräkna grannpixelns position
                int nx = x + dx;  // neighbor x
                int ny = y + dy;  // neighbor y

                // Kontrollera att grannpixeln är inom bildgränserna
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    // Hämta vikten från matrisen (konvertera dx/dy till matrisindex)
                    int weight = weighedMatrix[dx + 1][dy + 1];
                    int pixel = pixels[nx][ny];

                    // Addera viktade färgvärden till summorna
                    redSum += PixelConverter.getRed(pixel) * weight;
                    greenSum += PixelConverter.getGreen(pixel) * weight;
                    blueSum += PixelConverter.getBlue(pixel) * weight;
                }
            }
        }

        // Beräkna medelvärden genom att dividera med summan av vikterna
        // Math.round ger korrekt avrundning till närmaste heltal
        int red = (int) Math.round(redSum / weighedAverage);
        int green = (int) Math.round(greenSum / weighedAverage);
        int blue = (int) Math.round(blueSum / weighedAverage);

        // Behåll originalbildens alpha-kanal (transparens)
        int alpha = PixelConverter.getAlpha(pixels[x][y]);

        // Skapa och returnera den nya pixeln
        return PixelConverter.toArgbPixel(alpha, red, green, blue);
    }
}