package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import lab4edisochdanils.model.Histogram;
import lab4edisochdanils.model.InvertColors;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorController {
    private ImageProcessorView mainView;
    private InvertColors invertModel;
    private Histogram histogramCalculator;
    private InvertOperationView invertOperationView;

    public ImageProcessorController(ImageProcessorView view, InvertColors model) {
        this.mainView = view;
        this.invertModel = model;
        this.histogramCalculator = new Histogram();

        setupMenuHandlers();
        showInvertOperation();
        
        // Beräkna och visa histogram för den ursprungliga bilden
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

    private void onLoadImage(ActionEvent event) {
        // TODO: Implementera FileChooser för att ladda bilder
        System.out.println("Load image - not implemented yet");
    }

    private void onSaveImage(ActionEvent event) {
        // TODO: Implementera FileChooser för att spara bilder
        System.out.println("Save image - not implemented yet");
    }

    public void onInvertSelected(ActionEvent event) {
        // Hämta pixelrepresentation av den aktuella bilden
        Image img = mainView.getCurrentImage();
        int[][] pixels = ImagePixelsConverter.imageToPixels(img);

        // Modifiera pixlarna
        int[][] inverted = invertModel.process(pixels);

        // Sätt den nya bilden
        Image invertedImg = ImagePixelsConverter.pixelsToImage(inverted);
        mainView.setCurrentImage(invertedImg);

        // Uppdatera histogram
        updateHistogram();
    }

    private void onResetToOriginal(ActionEvent event) {
        // TODO: Implementera reset till originalbild
        System.out.println("Reset to original - not implemented yet");
    }

    private void updateHistogram() {
        Image img = mainView.getCurrentImage();
        int[][] pixels = ImagePixelsConverter.imageToPixels(img);
        int[][] histogram = histogramCalculator.calculateHistogram(pixels);
        mainView.updateHistogram(histogram);
    }
}
