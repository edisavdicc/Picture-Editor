module se.kth.anderslm.invertcolors {
    requires javafx.controls;
    requires javafx.fxml;

    opens lab4edisochdanils to javafx.fxml;
    exports lab4edisochdanils;
    exports lab4edisochdanils.view;
    opens lab4edisochdanils.view to javafx.fxml;
}