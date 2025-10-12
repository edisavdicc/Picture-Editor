package lab4edisochdanils.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorView extends VBox {
    private final ImageProcessorModel model;
    private ImageView imageView;
    private HistogramView histogramView;
    private ImageProcessorController controller;

    public ImageProcessorView(Image img, ImageProcessorModel model) {
        this.model = model;
        
        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(true);

        // ScrollPane för bilden
        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        // Histogram
        this.histogramView = new HistogramView();

        // Layout: meny, sedan HBox med histogram + bild
        HBox mainContent = new HBox();
        mainContent.setSpacing(10);
        mainContent.getChildren().addAll(histogramView, scrollPane);

        // Initiera modellen med bilden
        int[][] initialPixels = ImagePixelsConverter.imageToPixels(img);
        model.loadImage(initialPixels);

        // Skapa Controller här i View
        this.controller = new ImageProcessorController(model, this);

        // Skapa menubar med event handlers
        MenuBar menuBar = createMenuBar();

        // Uppdatera från model (visar bilden första gången)
        updateFromModel();

        this.getChildren().addAll(menuBar, mainContent);
    }

    private MenuBar createMenuBar() {
        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load Image...");
        MenuItem saveItem = new MenuItem("Save Image...");
        fileMenu.getItems().addAll(loadItem, saveItem);

        // Process menu
        Menu processMenu = new Menu("Process");
        MenuItem grayScaleItem = new MenuItem("Gray scale");
        MenuItem blurItem = new MenuItem("Blur");
        MenuItem sharpenItem = new MenuItem("Sharpen");
        MenuItem invertItem = new MenuItem("Invert colors");
        MenuItem resetItem = new MenuItem("Restore original");
        processMenu.getItems().addAll(grayScaleItem, blurItem, sharpenItem, invertItem, resetItem);


        // Help menu
        Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, processMenu, helpMenu);
        
        // Koppla event handlers här
        loadItem.setOnAction(event -> controller.onLoadImage());
        saveItem.setOnAction(event -> controller.onSaveImage());
        grayScaleItem.setOnAction(event -> controller.onGrayScaleSelected());
        blurItem.setOnAction(event->controller.onBlurSelected());
        sharpenItem.setOnAction(event -> controller.onSharpenSelected()); 
        invertItem.setOnAction(event -> controller.onInvertSelected());
        resetItem.setOnAction(event -> controller.onResetToOriginal());
        
        return menuBar;
    }

    /**
     * Updates the view from the current model state.
     * Retrieves pixel data from model, converts to Image and updates display.
     * Also updates histogram.
     */
    public void updateFromModel() {
        // Hämta direkt från model
        int[][] currentPixels = model.getCurrentPixels();
        Image image = ImagePixelsConverter.pixelsToImage(currentPixels);
        imageView.setImage(image);
        
        // Uppdatera histogram
        int[][] histogram = model.calculateHistogram();
        histogramView.updateView(histogram);
    }
}