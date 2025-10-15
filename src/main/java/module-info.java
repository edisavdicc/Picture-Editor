module se.kth.anderslm.invertcolors {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    opens lab4edisochdanils to javafx.fxml;
    exports lab4edisochdanils;
    exports lab4edisochdanils.view;
    exports lab4edisochdanils.utils;
    
    opens lab4edisochdanils.view to javafx.fxml;
}