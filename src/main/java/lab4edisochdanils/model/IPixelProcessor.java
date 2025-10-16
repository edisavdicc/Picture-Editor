package lab4edisochdanils.model;

/**
 * Interface for image processing operations.
 * All image processing methods implement this interface
 */
public interface IPixelProcessor {

    /**
     * Processes a pixel matrix and returns a new, modified pixel matrix.
     * @param originalPixels the original pixel matrix to be processed
     * @return a new pixel matrix with the processed image
     */
    int[][] process(int[][] originalPixels);
}
