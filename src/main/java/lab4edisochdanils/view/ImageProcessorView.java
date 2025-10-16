package lab4edisochdanils.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lab4edisochdanils.model.ImageOperation;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;
import lab4edisochdanils.utils.ImageProcessingException;

/**
 * Main view for image processing application.
 */
public class ImageProcessorView extends VBox {
    private final ImageProcessorModel model;
    private ImageView imageView;
    private HistogramView histogramView;
    private ImageProcessorController controller;

    private HBox sliderBox;
    private Slider windowSlider;
    private Slider levelSlider;

    public ImageProcessorView(Image img, ImageProcessorModel model, FileIO fileIO) {
        this.model = model;
        // Initialize model with image
        try {
            model.loadImage(ImagePixelsConverter.imageToPixels(img));
        } catch (ImageProcessingException e) {
            ImageProcessingException.showError("Error", "Could not load image");
        }
        // Create controller
        this.controller = new ImageProcessorController(model, this, fileIO);
        // ImageView with auto-scaling
        imageView = new ImageView();
        imageView.setPreserveRatio(false);
        
        BorderPane imageContainer = new BorderPane(imageView);
        imageContainer.setPadding(new Insets(10));
        imageContainer.setMinWidth(0);
        imageContainer.setMinHeight(0);
        imageView.fitWidthProperty().bind(imageContainer.widthProperty().subtract(20));
        imageView.fitHeightProperty().bind(imageContainer.heightProperty().subtract(20));

        // Histogram
        histogramView = new HistogramView();
        histogramView.setPrefWidth(400);
        histogramView.setPrefHeight(400);

        // Sliders (hidden initially)
        sliderBox = createSliders();
        sliderBox.setVisible(false);
        sliderBox.setManaged(false);

        // Left panel (histogram + sliders)
        VBox leftPanel = new VBox(10, histogramView, sliderBox);
        leftPanel.setMinWidth(400);
        leftPanel.setPrefWidth(400);
        leftPanel.setMaxWidth(400);

        // Main layout
        HBox mainContent = new HBox(10, leftPanel, imageContainer);
        HBox.setHgrow(imageContainer, Priority.ALWAYS);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        updateFromModel();
        getChildren().addAll(createMenuBar(), mainContent);
    }

    /**
     * Creates menu bar.
     * @return MenuBar
     */
    private MenuBar createMenuBar() {
        // File menu
        MenuItem loadItem = new MenuItem("Load Image...");
        loadItem.setOnAction(e -> controller.onLoadImage());
        MenuItem saveItem = new MenuItem("Save Image...");
        saveItem.setOnAction(e -> controller.onSaveImage());
        Menu fileMenu = new Menu("File", null, loadItem, saveItem);

        // Process menu
        MenuItem grayScaleItem = createOperationMenuItem("Gray scale", ImageOperation.GRAYSCALE);
        MenuItem windowLevelItem = new MenuItem("Window/Level");
        windowLevelItem.setOnAction(e -> toggleWindowLevelSliders());
        MenuItem blurItem = createOperationMenuItem("Blur", ImageOperation.BLUR);
        MenuItem sharpenItem = createOperationMenuItem("Sharpen", ImageOperation.SHARPEN);
        MenuItem invertItem = createOperationMenuItem("Invert colors", ImageOperation.INVERT);
        MenuItem resetItem = createOperationMenuItem("Restore original", ImageOperation.RESET_TO_ORIGINAL);
        Menu processMenu = new Menu("Process", null, grayScaleItem, windowLevelItem, blurItem, sharpenItem, invertItem, resetItem);

        return new MenuBar(fileMenu, processMenu, new Menu("Help"));
    }

    /**
     * Creates operation menu item.
     * @param text display text
     * @param operation operation to perform
     * @return MenuItem
     */
    private MenuItem createOperationMenuItem(String text, ImageOperation operation) {
        MenuItem item = new MenuItem(text);
        item.setOnAction(e -> controller.onOperationSelected(operation));
        return item;
    }

    /**
     * Creates window/level sliders.
     * @return HBox containing sliders
     */
    private HBox createSliders() {
        // Range 0-255, initial values 0/0
        windowSlider = new Slider(0, 255, 0);
        levelSlider = new Slider(0, 255, 0);

        // Configure sliders
        for (Slider s : new Slider[]{windowSlider, levelSlider}) {
            s.setShowTickLabels(true);
            s.setShowTickMarks(true);
            s.setMajorTickUnit(50);
            s.setMinorTickCount(4);
            s.setBlockIncrement(1);
            s.setPrefWidth(250);
        }

        // Update on change
        windowSlider.valueProperty().addListener((obs, o, n) ->
            controller.onWindowLevelChanged((int) levelSlider.getValue(), (int) windowSlider.getValue())
        );
        levelSlider.valueProperty().addListener((obs, o, n) ->
            controller.onWindowLevelChanged((int) levelSlider.getValue(), (int) windowSlider.getValue())
        );

        Label windowTitle = new Label("Window");
        VBox windowBox = new VBox(5, windowTitle, windowSlider);

        Label levelTitle = new Label("Level");
        VBox levelBox = new VBox(5, levelTitle, levelSlider);

        // Layout
        HBox sliderContainer = new HBox(20);
        sliderContainer.setPadding(new Insets(10, 10, 20, 20));
        sliderContainer.getChildren().addAll(windowBox, levelBox);

        return sliderContainer;
    }

    /**
     * Toggles window/level sliders visibility.
     */
    public void toggleWindowLevelSliders() {
        boolean visible = !sliderBox.isVisible();
        sliderBox.setVisible(visible);
        sliderBox.setManaged(visible);
    }

    /**
     * Updates view from model.
     */
    public void updateFromModel() {
        try {
            imageView.setImage(ImagePixelsConverter.pixelsToImage(model.getCurrentPixels()));
            histogramView.updateView(model.calculateHistogram());
        } catch (ImageProcessingException e) {
            ImageProcessingException.showError("Error", "Could not display image");
        }
    }
}
