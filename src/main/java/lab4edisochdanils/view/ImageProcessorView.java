package lab4edisochdanils.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImagePixelsConverter;
import lab4edisochdanils.utils.ImageProcessingException;

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
        // Initiera modellen med bilden
        try {
            model.loadImage(ImagePixelsConverter.imageToPixels(img));
        } catch (ImageProcessingException e) {
            ImageProcessingException.showError("Fel", "Kunde inte ladda startbild");
        }
        // Skapa Controller
        this.controller = new ImageProcessorController(model, this, fileIO);
        // ImageView med auto-skalning
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

        // Sliders (dolda från början)
        sliderBox = createSliders();
        sliderBox.setVisible(false);
        sliderBox.setManaged(false);

        // Vänster panel (histogram + sliders)
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

    private MenuBar createMenuBar() {
        // File menu
        MenuItem loadItem = new MenuItem("Load Image...");
        loadItem.setOnAction(e -> controller.onLoadImage());
        MenuItem saveItem = new MenuItem("Save Image...");
        saveItem.setOnAction(e -> controller.onSaveImage());
        Menu fileMenu = new Menu("File", null, loadItem, saveItem);

        // Process menu
        MenuItem grayScaleItem = new MenuItem("Gray scale");
        grayScaleItem.setOnAction(e -> controller.onGrayScaleSelected());
        MenuItem windowLevelItem = new MenuItem("Window/Level");
        windowLevelItem.setOnAction(e -> toggleWindowLevelSliders());
        MenuItem blurItem = new MenuItem("Blur");
        blurItem.setOnAction(e -> controller.onBlurSelected());
        MenuItem sharpenItem = new MenuItem("Sharpen");
        sharpenItem.setOnAction(e -> controller.onSharpenSelected());
        MenuItem invertItem = new MenuItem("Invert colors");
        invertItem.setOnAction(e -> controller.onInvertSelected());
        MenuItem resetItem = new MenuItem("Restore original");
        resetItem.setOnAction(e -> controller.onResetToOriginal());
        Menu processMenu = new Menu("Process", null, grayScaleItem, windowLevelItem, blurItem, sharpenItem, invertItem, resetItem);

        return new MenuBar(fileMenu, processMenu, new Menu("Help"));
    }

    private HBox createSliders() {
        // Skala 0..255 på båda sliders, startvärden 0/0 (bypass initialt)
        windowSlider = new Slider(0, 255, 0);
        levelSlider = new Slider(0, 255, 0);

        // Visa skala 0..255 på reglagen
        for (Slider s : new Slider[]{windowSlider, levelSlider}) {
            s.setShowTickLabels(true);
            s.setShowTickMarks(true);
            s.setMajorTickUnit(50);
            s.setMinorTickCount(4); // ger 10-steg mellan 50-markeringar
            s.setBlockIncrement(1);
            s.setPrefWidth(250);
        }

        // Uppdatera bilden när någon slider ändras (delad handler)
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

        // Placera grupperna horisontellt
        HBox sliderContainer = new HBox(20);
        sliderContainer.setPadding(new Insets(10, 10, 20, 20));
        sliderContainer.getChildren().addAll(windowBox, levelBox);

        return sliderContainer;
    }

    public void toggleWindowLevelSliders() {
        boolean visible = !sliderBox.isVisible();
        sliderBox.setVisible(visible);
        sliderBox.setManaged(visible);
    }

    public void updateFromModel() {
        try {
            imageView.setImage(ImagePixelsConverter.pixelsToImage(model.getCurrentPixels()));
            histogramView.updateView(model.calculateHistogram());
        } catch (ImageProcessingException e) {
            ImageProcessingException.showError("Fel", "Kunde inte visa bild");
        }
    }
}
