package lab4edisochdanils.model;

/**
 * Enumeration of available image processing operations.
 * Used to simplify method calls and reduce code duplication between
 * controller and model.
 * 
 * @author Edis and Danils
 * @version 1.0
 */
public enum ImageOperation {
    /** Convert the image to grayscale */
    GRAYSCALE,
    
    /** Blur the image (blur effect) */
    BLUR,
    
    /** Sharpen the edges in the image */
    SHARPEN,
    
    /** Invert the colors in the image */
    INVERT,
    
    /** Restore to original image */
    RESET_TO_ORIGINAL
}

