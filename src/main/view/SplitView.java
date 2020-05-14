package main.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.controller.SQLUtilControllerInterface;

public class SplitView extends Layout{
    private TitledPane splitLayout = new TitledPane();
    private Label splitLabel = new Label("New line after:");
    private TextField splitTextField = new TextField();
    private Button splitButton = new Button("Split");

    public SplitView(SQLUtilControllerInterface controller) {
        VBox layout = new VBox();
        layout.setSpacing(4);
        HBox splitContent = new HBox();
        align(splitLabel, splitTextField);
        grow(splitTextField);
        splitTextField.setText(",");
        splitContent.setSpacing(4);
        splitContent.getChildren().addAll(splitLabel, splitTextField);
//        splitButton.disableProperty().bind(inputArea.textProperty().isEmpty());
        splitButton.prefWidthProperty().bind(layout.widthProperty().subtract(30));
        layout.getChildren().addAll(splitContent, splitButton);
        splitLayout.setText("Split to new line");
        splitLayout.setContent(layout);

        splitButton.setOnAction(e -> controller.split(
                getInputText(),
                splitTextField.getText()));
    }

    @Override
    Node getLayout() {
        return splitLayout;
    }
}
