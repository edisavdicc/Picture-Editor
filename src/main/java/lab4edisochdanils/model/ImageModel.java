package lab4edisochdanils.model;

/**
 * Facade for the image processing model.
 * Defines all methods that the controller needs access to.
 * Contains no JavaFX dependencies - only pure data processing.
 */
public class ImageModel {
    private int[][] originalPixels;
    private int[][] currentPixels;
    private Histogram histogram;
    private InvertColors invertProcessor;
    
    public ImageModel() {
        this.histogram = new Histogram();
        this.invertProcessor = new InvertColors();
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
     * Get the original pixel matrix
     * @return original pixel matrix
     */
    public int[][] getOriginalPixels() {
        return copyPixels(originalPixels);
    }
    
    /**
     * Apply invert colors operation
     */
    public void invertColors() {
        if (currentPixels != null) {
            this.currentPixels = invertProcessor.process(currentPixels);
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
            return histogram.calculateHistogram(currentPixels);
        }
        return new int[256][3]; // Empty histogram
    }
    
    /**
     * Check if an image is loaded
     * @return true if image is loaded
     */
    public boolean isImageLoaded() {
        return currentPixels != null;
    }
    
    /**
     * Get image dimensions
     * @return array with [width, height] or [0, 0] if no image
     */
    public int[] getImageDimensions() {
        if (currentPixels != null) {
            return new int[]{currentPixels.length, currentPixels[0].length};
        }
        return new int[]{0, 0};
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
