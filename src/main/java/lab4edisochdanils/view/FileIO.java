package lab4edisochdanils.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Handles file I/O for images.
 */
public class FileIO {

    private FileChooser fileChooser;
    private Image image = null;
    private final Stage primaryStage;

    public FileIO(Stage primaryStage) {
        this.primaryStage = primaryStage;

        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "Image files", "*.png", "*.jpg", "*.bmp"
        );
        fileChooser.getExtensionFilters().add(filter);
    }

    protected void openImageFile() {
        File imageFile = fileChooser.showOpenDialog(primaryStage);
        if (imageFile == null) return;
        
        try {
            image = new Image(imageFile.toURI().toString());
            if (image.isError()) {
                AlertHelper.showError("Error", "Invalid image file");
                image = null;
            }
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load image");
            image = null;
        }
    }

    protected void saveImageToFile() {
        if (image == null) {
            AlertHelper.showError("Error", "No image to save");
            return;
        }
        
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", new File("copy.png"));
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not save image");
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        this.image = img;
    }
}

