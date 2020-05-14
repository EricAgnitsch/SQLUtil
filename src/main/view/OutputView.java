package main.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.controller.SQLUtilControllerInterface;
import main.model.OutputObserver;
import main.model.SQLUtilModelInterface;

public class OutputView extends Layout implements OutputObserver {
    private VBox outputLayout = new VBox();
    private Label outputLabel = new Label("Output (CTRL + ENTER to create pastebin URL):");
    private TextArea outputArea = new TextArea();

    public OutputView(SQLUtilControllerInterface controller, SQLUtilModelInterface model) {
        model.registerOutputObserver(this);

        outputLayout.getChildren().addAll(outputLabel, outputArea);
        outputLayout.setSpacing(4);
        VBox.setVgrow(outputLayout, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        outputArea.setEditable(false);

        outputArea.setOnKeyPressed(e -> {
            String output = outputArea.getText();
            if (!output.isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER)
                controller.createPaste(output);
            if (pasteCheck.isSelected())
                controller.openLink(pasteLink.getText());
        });
    }

    @Override
    public void updateOutputText(String outputText) {
        outputArea.setText(outputText);
    }

    @Override
    Node getLayout() {
        return outputLayout;
    }
}
