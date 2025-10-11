package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import lab4edisochdanils.model.ImageModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorController {
    private ImageProcessorView mainView;
    private ImageModel model;
    private InvertOperationView invertOperationView;

    public ImageProcessorController(ImageProcessorView view, ImageModel model) {
        this.mainView = view;
        this.model = model;

        setupMenuHandlers();
        showInvertOperation();
        
        // Load initial image and calculate histogram
        Image initialImage = mainView.getCurrentImage();
        int[][] initialPixels = ImagePixelsConverter.imageToPixels(initialImage);
        model.loadImage(initialPixels);
        updateHistogram();
    }

    private void setupMenuHandlers() {
        // File menu handlers
        MenuItem loadItem = mainView.getFileMenu().getItems().get(0);
        MenuItem saveItem = mainView.getFileMenu().getItems().get(1);
        
        // Edit menu handlers
        MenuItem invertItem = mainView.getEditMenu().getItems().get(0);
        MenuItem resetItem = mainView.getEditMenu().getItems().get(1);

        loadItem.setOnAction(this::onLoadImage);
        saveItem.setOnAction(this::onSaveImage);
        invertItem.setOnAction(this::onInvertSelected);
        resetItem.setOnAction(this::onResetToOriginal);
    }

    public void showInvertOperation() {
        mainView.clearOperationViews();
        invertOperationView = new InvertOperationView();
        invertOperationView.setInvertHandler(this::onInvertSelected);
        mainView.addOperationView(invertOperationView);
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
        // Apply invert operation in model
        model.invertColors();
        
        // Get processed pixels from model and update view
        int[][] processedPixels = model.getCurrentPixels();
        Image processedImage = ImagePixelsConverter.pixelsToImage(processedPixels);
        mainView.setCurrentImage(processedImage);
        
        // Calculate and update histogram from model data
        updateHistogram();
    }

    public void onResetToOriginal(ActionEvent event) {
        // TODO: Implementera reset till originalbild
        System.out.println("Reset to original - not implemented yet");
    }

    public void updateHistogram() {
        // Calculate histogram directly from model's current pixels
        int[][] histogram = model.calculateHistogram();
        mainView.updateHistogram(histogram);
    }
}
