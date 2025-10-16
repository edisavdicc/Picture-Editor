package lab4edisochdanils.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHelper {
    
    public static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

