package main.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Layout;

public class ViewOutput extends Layout {
    private Label outputLabel = new Label("Output (CTRL + ENTER to create pastebin URL):");
    private TextArea outputArea = new TextArea();

    @Override
    public Parent getLayout() {
        VBox layout = new VBox();
        layout.getChildren().addAll(outputLabel, outputArea);
        layout.setSpacing(4);
        VBox.setVgrow(layout, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        outputArea.setEditable(false);
        return layout;
    }

    public Label getOutputLabel() {
        return outputLabel;
    }

    public TextArea getOutputArea() {
        return outputArea;
    }
}
