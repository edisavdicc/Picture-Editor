package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InvertView extends VBox { // if we want to add a MenuBar, the main view should be a VBox

    private ImageView imageView;
    private Button invertButton;
    private Image currentImage;

    public InvertView(Image img) {
        this.currentImage = img;
        this.imageView = new ImageView();
        this.imageView.setImage(currentImage);

        this.invertButton = new Button("Invert colors");
        var buttonPane = new FlowPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(invertButton);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().addAll(imageView, buttonPane);
    }

    void addEventHandlers(InvertController controller) {
        EventHandler<ActionEvent> invertHandler = event -> {System.out.println("image");controller.onInvertSelected();
            };
        invertButton.addEventHandler(ActionEvent.ACTION, invertHandler);
    }

    void setCurrentImage(Image img) {
        currentImage = img;
        imageView.setImage(currentImage);
    }

    Image getCurrentImage() {
        return currentImage;
    }
}
