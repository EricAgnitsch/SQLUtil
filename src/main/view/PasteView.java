package main.view;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import main.model.AbstractSQLUtilModel;
import main.model.PasteObserver;

public class PasteView extends Layout implements PasteObserver {
    private HBox pasteLayout = new HBox();
    private Label pasteLabel = new Label("Pastebin URL:");
    private Hyperlink pasteLink = new Hyperlink();
    private CheckBox pasteCheck = new CheckBox("Automatically open paste link in browser");

    public PasteView(AbstractSQLUtilModel model) {
        model.registerPasteObserver(this);

        Region reg = new Region();
        HBox.setHgrow(reg, Priority.ALWAYS);
        align(pasteLabel, pasteLink);
        align(pasteCheck, pasteLink);
        pasteLayout.getChildren().addAll(pasteLabel, pasteLink, reg, pasteCheck);
        pasteLayout.setSpacing(4);
    }

    public Hyperlink getPasteLink() {
        return pasteLink;
    }

    public CheckBox getPasteCheck() {
        return pasteCheck;
    }

    @Override
    public void updatePasteLink(String pasteURL) {
        pasteLink.setText(pasteURL);
    }

    @Override
    Node getLayout() {
        return pasteLayout;
    }
}
