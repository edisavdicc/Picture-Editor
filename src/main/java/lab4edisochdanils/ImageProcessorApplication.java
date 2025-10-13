package lab4edisochdanils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lab4edisochdanils.model.ImageProcessorModel;
import lab4edisochdanils.utils.ImageProcessingException;
import lab4edisochdanils.view.FileIO;
import lab4edisochdanils.view.ImageProcessorView;

import java.io.IOException;

public class ImageProcessorApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Image originalImage = new Image(this.getClass().getResource("images/skull_ct.png").toString());
            
            if (originalImage.isError()) {
                ImageProcessingException.showError("Fel", "Kunde inte starta programmet");
                return;
            }

            ImageProcessorModel model = new ImageProcessorModel();
            FileIO fileIO = new FileIO(stage);
            ImageProcessorView view = new ImageProcessorView(originalImage, model, fileIO);

            Scene scene = new Scene(view, 800, 600);
            stage.setTitle("Image Processor");
            stage.setMinWidth(1000);
            stage.setMinHeight(600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            ImageProcessingException.showError("Fel", "Kunde inte starta programmet");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}