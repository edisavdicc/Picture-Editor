package lab4edisochdanils.view;

import javafx.scene.image.Image;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorController {
    private ImageProcessorView mainView;
    private ImageProcessorModel model;
    private FileIO fileIO;

    public ImageProcessorController(ImageProcessorModel model, ImageProcessorView view, FileIO fileIO) {
        this.model = model;
        this.mainView = view;
        this.fileIO = fileIO;
    }

    public void onGrayScaleSelected() {
        model.grayScale();
        mainView.updateFromModel();
    }

    public void onBlurSelected(){
        model.blur();
        mainView.updateFromModel();

    }

    public void onSharpenSelected() {
        model.sharpen();
        mainView.updateFromModel();
    }

    /**
     * Handler för när användaren ändrar window/level-värden
     * @param level nedre gräns för det aktiva intervallet
     * @param window storlek på intervallet
     */
    public void onWindowLevelChanged(int level, int window) {
        model.applyWindowLevel(level, window);
        mainView.updateFromModel();
    }

    public void onLoadImage() {
        fileIO.openImageFile();
        Image loadedImage = fileIO.getImage();
        if (loadedImage != null) {
            model.loadImage(ImagePixelsConverter.imageToPixels(loadedImage));
            mainView.updateFromModel();
        }
    }

    public void onSaveImage() {
        Image currentImage = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
        fileIO.setImage(currentImage);
        fileIO.saveImageToFile();
    }

    public void onInvertSelected() {
        model.invertColors();
        mainView.updateFromModel();
    }

    public void onResetToOriginal() {
        model.revertToOriginal();
        mainView.updateFromModel();
    }
}