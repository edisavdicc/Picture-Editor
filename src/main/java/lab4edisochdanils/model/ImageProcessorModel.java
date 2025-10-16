package lab4edisochdanils.model;

/**
 * Main class (facade) for the image processing model.
 * Defines all methods that the controller needs access to.
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
     * Loads a new image into the model.
     * Creates three copies of the image: original, processed and current.
     * 
     * @param pixels the pixel matrix representing the image
     */
    public void loadImage(int[][] pixels) {
        this.originalPixels = copyPixels(pixels);
        this.processedPixels = copyPixels(pixels);
        this.currentPixels = copyPixels(pixels);
    }

    /**
     * Gets the current pixel matrix to be displayed.
     * 
     * @return a copy of the current pixel matrix
     */
    public int[][] getCurrentPixels() {
        return copyPixels(currentPixels);
    }

    /**
     * Applies Window/Level contrast adjustment to the image.* 
     * @param level active range (0-255)
     * @param window size of the range 
     */
    public void applyWindowLevel(int level, int window) {
        if (processedPixels != null) {
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
     * Calculates histogram for the current image.
     * @return histogram data as an int[256][3] matrix where first index is intensity (0-255)
     *         and second index is color (0=red, 1=green, 2=blue)
     */
    public int[][] calculateHistogram() {
        if (currentPixels != null) {
            return histoGram.calculateHistogram(currentPixels);
        }
        return new int[256][3]; 
    }

    /**
     * Applies an image processing operation based on the specified operation type.
     * centralizing all operation handling
     * @param operation chosen image processing operation
     */
    public void applyOperation(ImageOperation operation) {
        if (processedPixels == null) return;
        
        switch (operation) {
            case GRAYSCALE:
                this.processedPixels = grayScale.process(processedPixels);
                this.currentPixels = copyPixels(processedPixels);
                break;
                
            case BLUR:
                this.processedPixels = blur.process(processedPixels);
                this.currentPixels = copyPixels(processedPixels);
                break;
                
            case SHARPEN:
                this.processedPixels = sharpening.process(processedPixels);
                this.currentPixels = copyPixels(processedPixels);
                break;
                
            case INVERT:
                this.processedPixels = invertColors.process(processedPixels);
                this.currentPixels = copyPixels(processedPixels);
                break;
                
            case RESET_TO_ORIGINAL:
                if (originalPixels != null) {
                    this.processedPixels = copyPixels(originalPixels);
                    this.currentPixels = copyPixels(originalPixels);
                }
                break;
        }
    }

    /**
     * Helper method to create a deep copy of a pixel matrix.
     * Used to ensure that original data is not modified.
     * @param pixels the pixel matrix to be copied
     * @return a new pixel matrix with the same values, or null if input is null
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