package lab4edisochdanils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab4edisochdanils.model.InvertColors;
import lab4edisochdanils.view.InvertController;
import lab4edisochdanils.view.InvertView;

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

        // NB! This is a demonstration of the ImagePixelsConverter interface
        // Your application should implement the model-view-controller design pattern
        InvertView view = new InvertView(originalImage);
        InvertColors model = new InvertColors();
        InvertController controller = new InvertController(view, model);

        Scene scene = new Scene(view, 1000, 630);
        stage.setTitle("Invert Colors App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}