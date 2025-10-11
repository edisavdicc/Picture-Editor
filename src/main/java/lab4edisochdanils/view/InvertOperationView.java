package lab4edisochdanils.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InvertOperationView extends VBox {
    private Button invertButton;

    public InvertOperationView() {
        this.invertButton = new Button("Invert Colors");
        
        var buttonPane = new FlowPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(invertButton);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().addAll(buttonPane);
    }

    public void setInvertHandler(EventHandler<ActionEvent> handler) {
        invertButton.setOnAction(handler);
    }
}
