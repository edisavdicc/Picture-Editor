package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lab4edisochdanils.model.ImageModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorView extends VBox {
    private ImageView imageView;
    private ScrollPane scrollPane;
    private HistogramView histogramView;
    private MenuBar menuBar;
    
    // MenuItem referenser för att undvika bräcklig indexering
    private MenuItem loadItem;
    private MenuItem saveItem;
    private MenuItem grayScaleItem;
    private MenuItem invertItem;
    private MenuItem resetItem;

    public ImageProcessorView(Image img, ImageModel model) {
        this.imageView = new ImageView();
        this.imageView.setImage(img);
        this.imageView.setPreserveRatio(true);

        // ScrollPane för bilden
        this.scrollPane = new ScrollPane(imageView);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setPannable(true);

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
        ImageProcessorController controller = new ImageProcessorController(model, this);

        // Skapa menubar
        createMenuBar();

        // Koppla event handlers
        addEventHandlers(controller);

        // Uppdatera histogram initialt
        int[][] histogram = model.calculateHistogram();
        histogramView.updateView(histogram);

        this.getChildren().addAll(menuBar, mainContent);
    }

    private void createMenuBar() {
        Menu fileMenu = new Menu("File");
        loadItem = new MenuItem("Load Image...");
        saveItem = new MenuItem("Save Image...");
        fileMenu.getItems().addAll(loadItem, saveItem);

        Menu processMenu = new Menu("Process");
        grayScaleItem = new MenuItem("Gray scale");
        invertItem = new MenuItem("Invert colors");
        resetItem = new MenuItem("Restore original");
        processMenu.getItems().addAll(grayScaleItem, invertItem, resetItem);

        Menu helpMenu = new Menu("Help");

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, processMenu, helpMenu);
    }

    private void addEventHandlers(ImageProcessorController controller) {
        // File menu handlers
        EventHandler<ActionEvent> loadHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onLoadImage(event);
            }
        };
        loadItem.addEventHandler(ActionEvent.ACTION, loadHandler);

        EventHandler<ActionEvent> saveHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onSaveImage(event);
            }
        };
        saveItem.addEventHandler(ActionEvent.ACTION, saveHandler);

        // Process menu handlers
        EventHandler<ActionEvent> grayScaleHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onGrayScaleSelected(event);
            }
        };
        grayScaleItem.addEventHandler(ActionEvent.ACTION, grayScaleHandler);

        EventHandler<ActionEvent> invertHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onInvertSelected(event);
            }
        };
        invertItem.addEventHandler(ActionEvent.ACTION, invertHandler);

        EventHandler<ActionEvent> resetHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onResetToOriginal(event);
            }
        };
        resetItem.addEventHandler(ActionEvent.ACTION, resetHandler);
    }

    public void setCurrentImage(Image img) {
        imageView.setImage(img);
    }

    public void updateHistogram(int[][] histogramData) {
        histogramView.updateView(histogramData);
    }
}