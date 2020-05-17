package main.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.model.AbstractSQLUtilModel;
import main.model.OutputObserver;

public class OutputView extends Layout implements OutputObserver {
    private VBox outputLayout = new VBox();
    private Label outputLabel = new Label("Output (CTRL + ENTER to create pastebin URL):");
    private TextArea outputArea = new TextArea();

    public OutputView(AbstractSQLUtilModel model) {
        model.registerOutputObserver(this);

        outputLayout.getChildren().addAll(outputLabel, outputArea);
        outputLayout.setSpacing(4);
        VBox.setVgrow(outputLayout, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        outputArea.setEditable(false);
    }

    public TextArea getOutputArea() {
        return outputArea;
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
