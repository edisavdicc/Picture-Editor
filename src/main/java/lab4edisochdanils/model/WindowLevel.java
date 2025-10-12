package lab4edisochdanils.model;

import lab4edisochdanils.utils.PixelConverter;

/**
 * Window/Level kontrast-justering för medicinska gråskalebilder.
 *
 * Metoden förbättrar synligheten av strukturer inom ett specifikt intensitetsintervall
 * genom att använda en linjär mappning enligt kurvan:
 *
 *   Output intensity (255) ┐     ┌────────
 *                          │    /
 *                          │   /
 *                          │  /
 *                          │ /
 *                       (0)└/───────────── Input intensity (255)
 *                         level  window
 *
 * - Värden < level → 0 (svart)
 * - Värden inom [level, level+window] → linjär mappning med lutning k = 255/window
 * - Värden > level+window → 255 (vit)
 *
 * Detta förstärker kontrasten för det valda intervallet, vilket hjälper läkare
 * att bättre se specifika strukturer (t.ex. ben, mjukvävnad, etc.) i medicinska bilder.
 */
public class WindowLevel implements IPixelProcessor {

    private int level;   // "Foten" på kurvan - nedre gräns för det aktiva intervallet
    private int window;  // Bredd på det aktiva intervallet där linjär skalning sker

    /**
     * Skapar en WindowLevel-processor med standardvärden.
     * Level=75, Window=35 ger bra resultat för hjärnscanningar.
     */
    public WindowLevel() {
        this.level = 75;
        this.window = 35;
    }

    /**
     * Skapar en WindowLevel-processor med angivna värden.
     *
     * @param level "foten" på kurvan, nedre gräns för det aktiva intervallet (0-255)
     * @param window bredden på det aktiva intervallet (1-255)
     */
    public WindowLevel(int level, int window) {
        this.level = level;
        this.window = window;
    }

    /**
     * Sätter level-värdet (foten på kurvan).
     *
     * @param level värde mellan 0 och 255
     */
    public void setLevel(int level) {
        if (level < 0) level = 0;
        if (level > 255) level = 255;
        this.level = level;
    }

    /**
     * Sätter window-värdet (bredden på det aktiva intervallet).
     *
     * @param window värde mellan 1 och 255
     */
    public void setWindow(int window) {
        if (window < 1) window = 1;
        if (window > 255) window = 255;
        this.window = window;
    }

    /**
     * Hämtar nuvarande level-värde.
     *
     * @return level-värdet
     */
    public int getLevel() {
        return level;
    }

    /**
     * Hämtar nuvarande window-värde.
     *
     * @return window-värdet
     */
    public int getWindow() {
        return window;
    }

    /**
     * Applicerar window/level-justering på en pixelmatris.
     * Fungerar bäst på gråskalebilder (t.ex. medicinska skanningar).
     *
     * @param originalPixels den ursprungliga pixelmatrisen
     * @return en ny pixelmatris med justerad kontrast
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
     * Justerar intensiteten för en enskild pixel enligt window/level-kurvan.
     *
     * @param pixel originalbildens pixel
     * @return den justerade pixeln som ett ARGB-värde
     */
    private int adjustPixel(int pixel) {
        // Extrahera färgkomponenter
        int red = PixelConverter.getRed(pixel);
        int green = PixelConverter.getGreen(pixel);
        int blue = PixelConverter.getBlue(pixel);
        int alpha = PixelConverter.getAlpha(pixel);

        // Beräkna gråskalevärde (medelvärde av RGB)
        int intensity = (red + green + blue) / 3;

        // Tillämpa window/level-mappning enligt kurvan
        int newIntensity = applyWindowLevel(intensity);

        // Skapa ny pixel med justerad intensitet för alla färgkanaler
        return PixelConverter.toArgbPixel(alpha, newIntensity, newIntensity, newIntensity);
    }

    /**
     * Tillämpar window/level-mappningen enligt den linjära kurvan.
     *
     * Mappning:
     * - input < level              → output = 0
     * - level ≤ input ≤ level+window → output = k × (input - level), där k = 255/window
     * - input > level+window       → output = 255
     *
     * @param intensity det ursprungliga intensitetsvärdet (0-255)
     * @return det mappade intensitetsvärdet (0-255)
     */
    private int applyWindowLevel(int intensity) {
        // Före foten på kurvan -> sätt till 0 (svart)
        if (intensity < level) {
            return 0;
        }

        // Efter fönstrets slut -> sätt till 255 (vit)
        int upperBound = level + window;
        if (intensity > upperBound) {
            return 255;
        }

        // Inom fönstret -> linjär mappning med lutning k = 255/window
        // output = k × (input - level) = (255/window) × (intensity - level)
        double k = 255.0 / window;  // Lutningen på kurvan
        double output = k * (intensity - level);

        return (int) Math.round(output);
    }
}