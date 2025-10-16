package lab4edisochdanils.model;
import static lab4edisochdanils.utils.PixelConverter.*;

/**
 * Histogram calculation for images.
 * Calculates the frequency of intensity values for each color channel (red, green, blue) in an image.
 * 
 * @author Edis and Danils
 * @version 1.0
 */
public class Histogram {
    
    /**
     * Calculates histogram for a pixel matrix.
     * Counts how many pixels have each intensity value (0-255) for each color channel.
     * 
     * @param pixels the pixel matrix to analyze
     * @return an int[256][3] matrix where first index (0-255) is intensity and 
     *         second index is color (0=red, 1=green, 2=blue). The value is the number of pixels.
     */
    public int[][] calculateHistogram(int[][] pixels){
        int[][] histogram = new int[256][3]; // 256 intensitetsvärden, 3 färger (RGB)

        int width = pixels.length;
        int height = pixels[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = pixels[x][y];

                int red = getRed(pixel);
                int green = getGreen(pixel);
                int blue = getBlue(pixel);

                // Öka räknaren för respektive färg och intensitetsvärde
                histogram[red][0]++;     // Röd
                histogram[green][1]++;   // Grön
                histogram[blue][2]++;    // Blå
            }
        }

        return histogram;
    }
}
