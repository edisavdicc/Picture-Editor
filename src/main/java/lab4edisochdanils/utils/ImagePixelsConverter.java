package lab4edisochdanils.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Conversion from JavaFX Image object to an int[][] pixel matrix, and vice versa.
 */
public class ImagePixelsConverter {

    public static int[][] imageToPixels(Image image) throws ImageProcessingException {
        if (image == null) {
            throw new ImageProcessingException("Bilden är null");
        }
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        
        if (width <= 0 || height <= 0) {
            throw new ImageProcessingException("Ogiltiga dimensioner");
        }

        int[][] pixelMatrix = new int[width][height];
        PixelReader pixelReader = image.getPixelReader();
        
        if (pixelReader == null) {
            throw new ImageProcessingException("Kunde inte läsa pixeldata");
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelMatrix[x][y] = pixelReader.getArgb(x, y);
            }
        }

        return pixelMatrix;
    }

    public static Image pixelsToImage(int[][] pixelMatrix) throws ImageProcessingException {
        if (pixelMatrix == null || pixelMatrix.length == 0) {
            throw new ImageProcessingException("Ogiltig pixelmatris");
        }
        
        if (pixelMatrix[0] == null || pixelMatrix[0].length == 0) {
            throw new ImageProcessingException("Ogiltiga dimensioner");
        }
        
        int width = pixelMatrix.length;
        int height = pixelMatrix[0].length;

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int argbValue;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                argbValue = pixelMatrix[x][y];
                pixelWriter.setArgb(x,y,argbValue);
            }
        }

        return writableImage;
    }
}
