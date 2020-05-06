package main.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Layout;

public class ViewInput extends Layout {
    private Label inputLabel = new Label("Input (CTRL + ENTER to format SQL):");
    private TextArea inputArea = new TextArea();

    @Override
    public Parent getLayout() {
        VBox layout = new VBox();
        layout.getChildren().addAll(inputLabel, inputArea);
        layout.setSpacing(4);
        VBox.setVgrow(layout, Priority.ALWAYS);
        VBox.setVgrow(inputArea, Priority.ALWAYS);
        return layout;
    }

    public Label getInputLabel() {
        return inputLabel;
    }

    public TextArea getInputArea() {
        return inputArea;
    }
}
