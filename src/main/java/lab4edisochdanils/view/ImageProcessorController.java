package lab4edisochdanils.view;

import javafx.scene.image.Image;
import lab4edisochdanils.model.ImageOperation;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;
import lab4edisochdanils.utils.ImageProcessingException;

/**
 * Controller for image processing application.
 */
public class ImageProcessorController {
    private ImageProcessorView mainView;
    private ImageProcessorModel model;
    private FileIO fileIO;

    public ImageProcessorController(ImageProcessorModel model, ImageProcessorView view, FileIO fileIO) {
        this.model = model;
        this.mainView = view;
        this.fileIO = fileIO;
    }

    /**
     * Handles image processing operations.
     * @param operation operation to perform
     */
    public void onOperationSelected(ImageOperation operation) {
        model.applyOperation(operation);
        mainView.updateFromModel();
    }

    /**
     * Handles window/level changes.
     * @param level lower bound
     * @param window range size
     */
    public void onWindowLevelChanged(int level, int window) {
        model.applyWindowLevel(level, window);
        mainView.updateFromModel();
    }

    /**
     * Handles load image action.
     */
    public void onLoadImage() {
        fileIO.openImageFile();
        Image loadedImage = fileIO.getImage();
        if (loadedImage != null) {
            try {
                model.loadImage(ImagePixelsConverter.imageToPixels(loadedImage));
                mainView.updateFromModel();
            } catch (ImageProcessingException e) {
                AlertHelper.showError("Error", "Could not process image");
            }
        }
    }

    /**
     * Handles save image action.
     */
    public void onSaveImage() {
        try {
            Image currentImage = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
            fileIO.setImage(currentImage);
            fileIO.saveImageToFile();
        } catch (ImageProcessingException e) {
            AlertHelper.showError("Error", "Could not save image");
        }
    }

}