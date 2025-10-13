package lab4edisochdanils.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        if (imageFile == null) {
            return;
        }
        image = new Image(imageFile.toURI().toString());
        // uppdatera UI/modell här vid behov
    }

    protected void saveImageToFile() {
        if (image == null) {
            return;
        }
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", new File("copy.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        this.image = img;
    }
}

