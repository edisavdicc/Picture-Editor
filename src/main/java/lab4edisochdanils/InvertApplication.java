package lab4edisochdanils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab4edisochdanils.model.ImageModel;
import lab4edisochdanils.view.ImageProcessorController;
import lab4edisochdanils.view.ImageProcessorView;

import java.io.IOException;

public class InvertApplication extends Application {

    private FileChooser fileChooser;
    private Image image = null;
    
    public InvertApplication(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.png", ".jpg", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
    }
    
    @Override
    public void start(Stage stage) throws IOException {

        // NB! This is not the way to load an image - this is just for test purpose.
        // Use a FileChooser in your application
        Image originalImage = new Image(this.getClass().getResource("images/devil.png").toString());

        // Ny MVC-struktur med ImageProcessorView som huvudcontainer
        ImageProcessorView view = new ImageProcessorView(originalImage);
        ImageModel model = new ImageModel();
        ImageProcessorController controller = new ImageProcessorController(view, model);

        Scene scene = new Scene(view,  800, 600);
        stage.setTitle("Image Processing App with Histogram");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}