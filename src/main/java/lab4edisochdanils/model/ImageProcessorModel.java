package lab4edisochdanils.model;

/**
 * Facade for the image processing model.
 * Defines all methods that the controller needs access to.
 * Contains no JavaFX dependencies - only pure data processing.
 */
public class ImageProcessorModel {
    private int[][] originalPixels;        // Ursprunglig bild (för Restore original)
    private int[][] processedPixels;       // Bild efter blur/gray/etc (innan Window/Level)
    private int[][] currentPixels;         // Slutlig bild som visas (efter Window/Level)

    private Histogram histoGram;
    private InvertColors invertColors;
    private GrayScale grayScale;
    private Blur blur;
    private Sharpening sharpening;
    private WindowLevel windowLevel;

    public ImageProcessorModel() {
        this.histoGram = new Histogram();
        this.invertColors = new InvertColors();
        this.grayScale = new GrayScale();
        this.blur = new Blur();
        this.sharpening = new Sharpening();
        this.windowLevel = new WindowLevel();
    }

    /**
     * Load an image into the model
     * @param pixels the pixel matrix representing the image
     */
    public void loadImage(int[][] pixels) {
        this.originalPixels = copyPixels(pixels);
        this.processedPixels = copyPixels(pixels);
        this.currentPixels = copyPixels(pixels);
    }

    /**
     * Get the current pixel matrix
     * @return current pixel matrix
     */
    public int[][] getCurrentPixels() {
        return copyPixels(currentPixels);
    }

    /**
     * Apply invert colors operation
     */
    public void invertColors() {
        if (processedPixels != null) {
            this.processedPixels = invertColors.process(processedPixels);
            this.currentPixels = copyPixels(processedPixels);
        }
    }

    /**
     * Apply grayscale operation
     */
    public void grayScale() {
        if (processedPixels != null) {
            this.processedPixels = grayScale.process(processedPixels);
            this.currentPixels = copyPixels(processedPixels);
        }
    }

    /**
     * Apply blur operation
     */
    public void blur() {
        if (processedPixels != null) {
            this.processedPixels = blur.process(processedPixels);
            this.currentPixels = copyPixels(processedPixels);
        }
    }

    /**
     * Apply sharpening operation
     */
    public void sharpen() {
        if (processedPixels != null) {
            this.processedPixels = sharpening.process(processedPixels);
            this.currentPixels = copyPixels(processedPixels);
        }
    }

    /**
     * Apply window/level contrast adjustment
     * @param level nedre gräns för det aktiva intervallet
     * @param window storlek på intervallet
     */
    public void applyWindowLevel(int level, int window) {
        if (processedPixels != null) {
            // Bypass: om window är 0 eller mindre, visa bilden utan W/L-justering
            if (window <= 0) {
                this.currentPixels = copyPixels(processedPixels);
                return;
            }

            windowLevel.setLevel(level);
            windowLevel.setWindow(window);
            // Applicera på processedPixels
            this.currentPixels = windowLevel.process(processedPixels);
        }
    }

    /**
     * Revert to original image
     */
    public void revertToOriginal() {
        if (originalPixels != null) {
            this.processedPixels = copyPixels(originalPixels);
            this.currentPixels = copyPixels(originalPixels);
        }
    }

    /**
     * Calculate histogram for current pixels
     * @return histogram data as int[256][3] matrix
     */
    public int[][] calculateHistogram() {
        if (currentPixels != null) {
            return histoGram.calculateHistogram(currentPixels);
        }
        return new int[256][3]; // Empty histogram
    }
SKAPA ENUM, SWITCH CASE, ALERT I VIEW, UML, FÖRENKING KRING FUNTKIONSANROP
    /**
     * Helper method to create a deep copy of pixel matrix
     */
    private int[][] copyPixels(int[][] pixels) {
        if (pixels == null) return null;

        int width = pixels.length;
        int height = pixels[0].length;
        int[][] copy = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                copy[x][y] = pixels[x][y];
            }
        }

        return copy;
    }
}