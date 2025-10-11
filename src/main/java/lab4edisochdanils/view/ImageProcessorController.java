package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import lab4edisochdanils.model.ImageModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorController {
    private ImageProcessorView mainView;
    private ImageModel model;

    public ImageProcessorController(ImageModel model, ImageProcessorView view) {
        this.model = model;
        this.mainView = view;
    }

    public void onGrayScaleSelected(ActionEvent event) {
        model.grayScale();
        updateViewFromModel();
    }

    public void onLoadImage(ActionEvent event) {
        // TODO: Implementera FileChooser för att ladda bilder
        System.out.println("Load image - not implemented yet");
    }

    public void onSaveImage(ActionEvent event) {
        // TODO: Implementera FileChooser för att spara bilder
        System.out.println("Save image - not implemented yet");
    }

    public void onInvertSelected(ActionEvent event) {
        model.invertColors();
        updateViewFromModel();
    }

    public void onResetToOriginal(ActionEvent event) {
        model.revertToOriginal();
        updateViewFromModel();
    }

    /**
     * Updates the view from the current model state.
     * Converts pixel data to Image and updates histogram.
     */
    void updateViewFromModel() {
        int[][] currentPixels = model.getCurrentPixels();
        Image image = ImagePixelsConverter.pixelsToImage(currentPixels);
        mainView.setCurrentImage(image);
        
        int[][] histogram = model.calculateHistogram();
        mainView.updateHistogram(histogram);
    }
}