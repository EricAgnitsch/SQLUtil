package main;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public abstract class Layout {
    protected abstract Parent getLayout();

    protected void align(Control c, Control c2) {
        c.prefHeightProperty().bind(c2.heightProperty());
    }

    protected void grow(Node n) {
        HBox.setHgrow(n, Priority.ALWAYS);
    }
}