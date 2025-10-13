package lab4edisochdanils.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab4edisochdanils.utils.ImageProcessingException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
                ImageProcessingException.showError("Fel", "Ogiltig bildfil");
                image = null;
            }
        } catch (Exception e) {
            ImageProcessingException.showError("Fel", "Kunde inte ladda bild");
            image = null;
        }
    }

    protected void saveImageToFile() {
        if (image == null) {
            ImageProcessingException.showError("Fel", "Ingen bild att spara");
            return;
        }
        
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", new File("copy.png"));
        } catch (Exception e) {
            ImageProcessingException.showError("Fel", "Kunde inte spara bild");
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        this.image = img;
    }
}

