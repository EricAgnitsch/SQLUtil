package main.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.model.AbstractSQLUtilModel;
import main.model.InputObserver;

public class InputView extends Layout implements InputObserver {
    private VBox inputLayout = new VBox();
    private Label inputLabel = new Label("Input (CTRL + ENTER to format SQL):");
    private TextArea inputArea = new TextArea();

    public InputView(AbstractSQLUtilModel model) {
        model.registerInputObserver(this);

        inputLayout.getChildren().addAll(inputLabel, inputArea);
        inputLayout.setSpacing(4);
        VBox.setVgrow(inputLayout, Priority.ALWAYS);
        VBox.setVgrow(inputArea, Priority.ALWAYS);
    }

    public TextArea getInputArea() {
        return inputArea;
    }

    @Override
    public void updateInputText(String inputText) {
        inputArea.setText(inputText);
    }

    @Override
    Node getLayout() {
        return inputLayout;
    }
}
