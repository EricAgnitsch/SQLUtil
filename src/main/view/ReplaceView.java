package main.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.controller.SQLUtilControllerInterface;

public class ReplaceView extends Layout {
    private TitledPane replaceLayout = new TitledPane();
    private Label oldCharLabel = new Label("Old char(s):");
    private TextField oldCharTextField = new TextField();
    private Label newCharLabel = new Label("New char(s):");
    private TextField newCharTextField = new TextField();
    private Button replaceButton = new Button("Replace");

    public ReplaceView(SQLUtilControllerInterface controller) {
        VBox layout = new VBox();
        layout.setSpacing(4);
        HBox oldCharLayout = new HBox();
        align(oldCharLabel,  oldCharTextField);
        grow(oldCharTextField);
        oldCharTextField.setText("/");
        oldCharLayout.setSpacing(4);
        oldCharLayout.getChildren().addAll(oldCharLabel, oldCharTextField);
        HBox newCharLayout = new HBox();
        align(newCharLabel,  newCharTextField);
        grow(newCharTextField);
        newCharTextField.setText(".");
        newCharLayout.setSpacing(4);
        newCharLayout.getChildren().addAll(newCharLabel, newCharTextField);
//        replaceButton.disableProperty().bind(inputArea.textProperty().isEmpty());
        replaceButton.prefWidthProperty().bind(layout.widthProperty().subtract(30));
        layout.getChildren().addAll(oldCharLayout, newCharLayout, replaceButton);
        replaceLayout.setText("Replace characters");
        replaceLayout.setContent(layout);

        replaceButton.setOnAction(e -> controller.replaceAll(
                getInputText(),
                oldCharTextField.getText(),
                newCharTextField.getText()));
    }

    @Override
    Node getLayout() {
        return replaceLayout;
    }
}
