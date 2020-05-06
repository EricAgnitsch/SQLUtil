package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.model.ModelInput;
import main.model.ModelPaste;
import main.view.ViewPaste;

public class ControllerPaste {
    private ViewPaste viewPaste;
    private ModelInput modelInput;
    private ModelPaste modelPaste;

    public ControllerPaste(ViewPaste viewPaste, ModelInput modelInput, ModelPaste modelPaste) {
        this.viewPaste = viewPaste;
        this.modelInput = modelInput;
        this.modelPaste = modelPaste;

        modelPaste.requestProperty().addListener(requestPropertyListener());

        viewPaste.getPasteLink().textProperty().bind(modelPaste.pasteURLProperty());
        viewPaste.getPasteLink().setOnAction(pasteLinkListener());
    }

    private ChangeListener<? super String> requestPropertyListener() {
        return (v, oldVal, newVal) -> viewPaste.getPasteLink().requestFocus();
    }

    protected EventHandler<ActionEvent> pasteLinkListener() {
        return e -> {
            viewPaste.getPasteLink().setVisited(true);
            // openLink();
            modelInput.setRequest("beep");
        };
    }
}
