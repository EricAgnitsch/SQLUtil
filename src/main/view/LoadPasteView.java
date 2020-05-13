package main.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.controller.SQLUtilControllerInterface;

public class LoadPasteView extends Layout {
    private TitledPane loadPasteLayout = new TitledPane();
    private Label loadPasteLabel = new Label("Insert link (https://p.teknik.io/#####):");
    private TextField loadPasteTextField = new TextField();
    private Button loadPasteButton = new Button("Load to input");

    public LoadPasteView(SQLUtilControllerInterface controller) {
        HBox layout = new HBox();
        layout.setSpacing(4);
        align(loadPasteLabel, loadPasteTextField);
        HBox.setHgrow(loadPasteTextField, Priority.ALWAYS);
        layout.getChildren().addAll(loadPasteLabel, loadPasteTextField, loadPasteButton);
        loadPasteLayout.setText("Load paste to input");
        loadPasteLayout.setContent(layout);

        loadPasteButton.setOnAction(e -> controller.loadPaste(loadPasteTextField.getText()));
    }

    @Override
    Node getLayout() {
        return loadPasteLayout;
    }
}