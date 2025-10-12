package lab4edisochdanils.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;

public class ImageProcessorView extends VBox {
    private final ImageProcessorModel model;
    private ImageView imageView;
    private HistogramView histogramView;
    private ImageProcessorController controller;

    private HBox sliderBox;
    private Slider windowSlider;
    private Slider levelSlider;
    private boolean slidersVisible = false;

    public ImageProcessorView(Image img, ImageProcessorModel model) {
        this.model = model;

        // ImageView - bilden anpassas alltid till sin box
        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(false);

        // ScrollPane för bilden
        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setPannable(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setStyle("-fx-background-color: #f0f0f0;");
        
        // Bind bilden till scrollPane så den alltid fyller utrymmet
        imageView.fitWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        imageView.fitHeightProperty().bind(scrollPane.heightProperty().subtract(20));

        // Histogram
        this.histogramView = new HistogramView();
        histogramView.setPrefWidth(400);
        histogramView.setPrefHeight(400);

        // Skapa Window/Level sliders (dolda från början)
        sliderBox = createSliders();
        sliderBox.setVisible(false);
        sliderBox.setManaged(false);

        // Layout: vänster panel (histogram + sliders) och höger (bild)
        HBox mainContent = new HBox(10);
        
        // Vänster panel med histogram och sliders
        VBox leftPanel = new VBox(10);
        leftPanel.getChildren().addAll(histogramView, sliderBox);
        leftPanel.setMinWidth(400);
        leftPanel.setPrefWidth(400);
        leftPanel.setMaxWidth(400);
        
        mainContent.getChildren().addAll(leftPanel, scrollPane);

        // Låt scrollPane växa och fylla tillgängligt utrymme
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

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
        MenuItem windowLevelItem = new MenuItem("Window/Level");
        MenuItem blurItem = new MenuItem("Blur");
        MenuItem sharpenItem = new MenuItem("Sharpen");
        MenuItem invertItem = new MenuItem("Invert colors");
        MenuItem resetItem = new MenuItem("Restore original");
        processMenu.getItems().addAll(grayScaleItem, windowLevelItem, blurItem, sharpenItem, invertItem, resetItem);


        // Help menu
        Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, processMenu, helpMenu);

        // Koppla event handlers här
        loadItem.setOnAction(event -> controller.onLoadImage());
        saveItem.setOnAction(event -> controller.onSaveImage());
        grayScaleItem.setOnAction(event -> controller.onGrayScaleSelected());
        windowLevelItem.setOnAction(event -> toggleWindowLevelSliders());
        blurItem.setOnAction(event -> controller.onBlurSelected());
        sharpenItem.setOnAction(event -> controller.onSharpenSelected());
        invertItem.setOnAction(event -> controller.onInvertSelected());
        resetItem.setOnAction(event -> controller.onResetToOriginal());

        return menuBar;
    }

    /**
     * Skapar Window/Level sliders i horisontell layout.
     */
    private HBox createSliders() {
        HBox box = new HBox(40);
        box.setPadding(new Insets(10, 10, 20, 20));

        // Skapa sliders
        windowSlider = createConfiguredSlider(1, 255, 35);
        levelSlider = createConfiguredSlider(0, 255, 75);

        // Lägg till listeners
        windowSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.onWindowLevelChanged((int) levelSlider.getValue(), newVal.intValue());
        });
        levelSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.onWindowLevelChanged(newVal.intValue(), (int) windowSlider.getValue());
        });

        // Lägg till i layout
        box.getChildren().addAll(
            createLabeledSlider("Window", windowSlider),
            createLabeledSlider("Level", levelSlider)
        );
        return box;
    }

    /**
     * Hjälpmetod för att skapa en konfigurerad slider.
     */
    private Slider createConfiguredSlider(double min, double max, double initial) {
        Slider slider = new Slider(min, max, initial);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setPrefWidth(200);
        return slider;
    }

    /**
     * Hjälpmetod för att skapa en VBox med label och slider.
     */
    private VBox createLabeledSlider(String labelText, Slider slider) {
        VBox box = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");
        box.getChildren().addAll(label, slider);
        return box;
    }

    /**
     * Togglar synlighet för Window/Level sliders.
     */
    public void toggleWindowLevelSliders() {
        slidersVisible = !slidersVisible;
        sliderBox.setVisible(slidersVisible);
        sliderBox.setManaged(slidersVisible);
    }

    /**
     * Uppdaterar vyn från modellens nuvarande tillstånd.
     */
    public void updateFromModel() {
        int[][] currentPixels = model.getCurrentPixels();
        Image image = ImagePixelsConverter.pixelsToImage(currentPixels);
        imageView.setImage(image);

        int[][] histogram = model.calculateHistogram();
        histogramView.updateView(histogram);
    }
}