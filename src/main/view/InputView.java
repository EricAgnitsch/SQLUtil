package main.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.controller.SQLUtilControllerInterface;
import main.model.InputObserver;
import main.model.SQLUtilModelInterface;

public class InputView extends Layout implements InputObserver {
    private VBox inputLayout = new VBox();
    private Label inputLabel = new Label("Input (CTRL + ENTER to format SQL):");
    private TextArea inputArea = new TextArea();

    public InputView(SQLUtilControllerInterface controller, SQLUtilModelInterface model) {
        model.registerInputObserver(this);

        inputLayout.getChildren().addAll(inputLabel, inputArea);
        inputLayout.setSpacing(4);
        VBox.setVgrow(inputLayout, Priority.ALWAYS);
        VBox.setVgrow(inputArea, Priority.ALWAYS);

        inputArea.setOnKeyPressed(e -> {
            String input = inputArea.getText();
            if (!input.isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER)
                controller.format(input);
        });
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
