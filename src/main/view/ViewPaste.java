package main.view;

import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import main.Layout;

public class ViewPaste extends Layout {
    private Label pasteLabel = new Label("Paste URL:");
    private Hyperlink pasteLink = new Hyperlink();
    private CheckBox pasteCheck = new CheckBox("Automatically open link in browser");

    @Override
    public Parent getLayout() {
        HBox layout = new HBox();
        Region reg = new Region();
        HBox.setHgrow(reg, Priority.ALWAYS);
        align(pasteLabel, pasteLink);
        align(pasteCheck, pasteLink);
        layout.getChildren().addAll(pasteLabel, pasteLink, reg, pasteCheck);
        layout.setSpacing(4);
        return layout;
    }

    public Label getPasteLabel() {
        return pasteLabel;
    }

    public Hyperlink getPasteLink() {
        return pasteLink;
    }

    public CheckBox getPasteCheck() {
        return pasteCheck;
    }
}
