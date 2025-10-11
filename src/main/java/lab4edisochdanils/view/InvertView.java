package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InvertView extends VBox {
    private ImageView imageView;
    private ScrollPane scrollPane;
    private Button invertButton;
    private HistogramView histogramView;
    private Image currentImage;

    public InvertView(Image img) {
        this.currentImage = img;
        this.imageView = new ImageView();
        this.imageView.setImage(currentImage);
        this.imageView.setPreserveRatio(true);

        // ScrollPane för bilden
        this.scrollPane = new ScrollPane(imageView);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setPannable(true);

        // Histogram
        this.histogramView = new HistogramView();

        // Knappar
        this.invertButton = new Button("Invert colors");
        var buttonPane = new FlowPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(invertButton);

        // Layout: Bild till vänster, histogram till höger
        HBox mainContent = new HBox();
        mainContent.setSpacing(10);
        mainContent.getChildren().addAll(histogramView, scrollPane);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().addAll(mainContent, buttonPane);
    }

    void addEventHandlers(InvertController controller) {
        EventHandler<ActionEvent> invertHandler = event -> {
            System.out.println("image");
            controller.onInvertSelected();
        };
        invertButton.addEventHandler(ActionEvent.ACTION, invertHandler);
    }

    void setCurrentImage(Image img) {
        currentImage = img;
        imageView.setImage(currentImage);
    }

    void updateHistogram(int[][] histogramData) {
        histogramView.updateView(histogramData);
    }

    void clearHistogram() {
        histogramView.clear();
    }

    Image getCurrentImage() {
        return currentImage;
    }
}