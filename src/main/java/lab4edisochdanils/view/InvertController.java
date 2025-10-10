package lab4edisochdanils.view;

import javafx.scene.image.Image;
import lab4edisochdanils.model.InvertColors;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class InvertController {

    private InvertView view;
    private InvertColors model; // NB! In your application the model consists of several classes use a Facade as main model

    public InvertController(InvertView view, InvertColors model) {
        this.view = view;
        this.model = model;

        this.view.addEventHandlers(this);
    }

    public void onInvertSelected() {
        // get a ixel representation of the current image
        Image img = view.getCurrentImage();
        int[][] pixels = ImagePixelsConverter.imageToPixels(img);
        // modify the pixels
        int[][] inverted = model.process(pixels);
        // set the new image
        Image invertedImg = ImagePixelsConverter.pixelsToImage(inverted);
        view.setCurrentImage(invertedImg);
    }
}
