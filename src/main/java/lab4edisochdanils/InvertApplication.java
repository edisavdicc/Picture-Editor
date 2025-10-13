package lab4edisochdanils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.view.ImageProcessorView;

import java.io.IOException;

public class InvertApplication extends Application {

    private FileChooser fileChooser;
    private Image image = null;

    public InvertApplication() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.png", ".jpg", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Image originalImage = new Image(this.getClass().getResource("images/skull_ct.png").toString());

        ImageProcessorModel model = new ImageProcessorModel();
        ImageProcessorView view = new ImageProcessorView(originalImage, model);

        Scene scene = new Scene(view, 800, 600);
        stage.setTitle("Image Processor");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}