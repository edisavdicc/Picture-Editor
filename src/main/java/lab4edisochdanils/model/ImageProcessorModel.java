package lab4edisochdanils.model;

/**
 * Facade for the image processing model.
 * Defines all methods that the controller needs access to.
 * Contains no JavaFX dependencies - only pure data processing.
 */
public class ImageProcessorModel {
    private int[][] originalPixels;
    private int[][] currentPixels;
    private Histogram histoGram;
    private InvertColors invertColors;
    private GrayScale grayScale;
    private Blur blur;
    private Sharpening sharpening;
    
    public ImageProcessorModel() {
        this.histoGram = new Histogram();
        this.invertColors = new InvertColors();
        this.grayScale = new GrayScale();
        this.blur = new Blur();
        this.sharpening = new Sharpening();
    }
    
    /**
     * Load an image into the model
     * @param pixels the pixel matrix representing the image
     */
    public void loadImage(int[][] pixels) {
        this.originalPixels = copyPixels(pixels);
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
        if (currentPixels != null) {
            this.currentPixels = invertColors.process(currentPixels);
        }
    }

    public void sharpen() {
        if (currentPixels != null) {
            this.currentPixels = sharpening.process(currentPixels);
        }
    }

    /**
     * Apply grayscale operation
     */
    public void grayScale() {
        if (currentPixels != null) {
            this.currentPixels = grayScale.process(currentPixels);
        }
    }
    
    public void blur(){
        if(currentPixels != null){
            this.currentPixels = blur.process(currentPixels);
        }
    }
    
    /**
     * Revert to original image
     */
    public void revertToOriginal() {
        if (originalPixels != null) {
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
