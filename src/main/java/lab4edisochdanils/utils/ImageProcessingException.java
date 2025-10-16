package lab4edisochdanils.utils;

import java.io.IOException;

/**
 * Custom exception for image processing errors.
 */
public class ImageProcessingException extends IOException {
    
    public ImageProcessingException(String message) {
        super(message);
    }
}


