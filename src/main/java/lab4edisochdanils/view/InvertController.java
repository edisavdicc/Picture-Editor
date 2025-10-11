package lab4edisochdanils.view;

import javafx.scene.image.Image;
import lab4edisochdanils.model.Histogram;
import lab4edisochdanils.model.InvertColors;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class InvertController {
    private InvertView view;
    private InvertColors model;
    private Histogram histogramCalculator;

    public InvertController(InvertView view, InvertColors model) {
        this.view = view;
        this.model = model;
        this.histogramCalculator = new Histogram();

        this.view.addEventHandlers(this);

        // Beräkna och visa histogram för den ursprungliga bilden
        updateHistogram();
    }

    public void onInvertSelected() {
        // Hämta pixelrepresentation av den aktuella bilden
        Image img = view.getCurrentImage();
        int[][] pixels = ImagePixelsConverter.imageToPixels(img);

        // Modifiera pixlarna
        int[][] inverted = model.process(pixels);

        // Sätt den nya bilden
        Image invertedImg = ImagePixelsConverter.pixelsToImage(inverted);
        view.setCurrentImage(invertedImg);

        // Uppdatera histogram
        updateHistogram();
    }

    private void updateHistogram() {
        Image img = view.getCurrentImage();
        int[][] pixels = ImagePixelsConverter.imageToPixels(img);
        int[][] histogram = histogramCalculator.calculateHistogram(pixels);
        view.updateHistogram(histogram);
    }
}