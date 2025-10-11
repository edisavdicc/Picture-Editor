package lab4edisochdanils.model;
import static lab4edisochdanils.utils.PixelConverter.*;


public class Histogram {
    
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
