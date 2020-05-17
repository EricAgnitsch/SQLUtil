package main.controller;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import main.HistoryData;
import main.model.AbstractSQLUtilModel;
import main.view.SQLUtilView;

public class SQLUtilController implements SQLUtilControllerInterface {
    private AbstractSQLUtilModel model;

    private TextArea inputArea;
    private Button loadPasteButton;
    private TextField loadPasteTextField;
    private TextArea outputArea;
    private CheckBox pasteCheck;
    private Hyperlink pasteLink;
    private Button splitButton;
    private TextField splitTextField;
    private Button replaceButton;
    private TextField oldCharTextField;
    private TextField newCharTextField;
    private ListView<HistoryData> historyList;

    public SQLUtilController(SQLUtilView view, AbstractSQLUtilModel model) {
        this.model = model;

        view.createView();

        inputArea = view.getInputArea();
        loadPasteButton = view.getLoadPasteButton();
        loadPasteTextField = view.getLoadPasteTextField();
        outputArea = view.getOutputArea();
        pasteCheck = view.getPasteCheck();
        pasteLink = view.getPasteLink();
        splitButton = view.getSplitButton();
        splitTextField = view.getSplitTextField();
        replaceButton = view.getReplaceButton();
        oldCharTextField = view.getOldCharTextField();
        newCharTextField = view.getNewCharTextField();
        historyList = view.getHistoryList();

        createControllers();
    }

    private void createControllers() {
        // load paste view
        loadPasteButton.setOnAction(e -> model.loadPaste(loadPasteTextField.getText()));

        // input view
        inputArea.setOnKeyPressed(e -> {
            String input = inputArea.getText();
            if (!input.isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER)
                model.format(input);
        });

        // output view
        outputArea.setOnKeyPressed(e -> {
            String output = outputArea.getText();
            if (!output.isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER)
                model.createPaste(output);
            if (pasteCheck.isSelected())
                model.openLink(pasteLink.getText());
        });

        // paste view
        pasteLink.setOnAction(e -> model.openLink(pasteLink.getText()));

        // split view
        splitButton.setOnAction(e -> model.split(
                inputArea.getText(),
                splitTextField.getText()));

        // replace view
        replaceButton.setOnAction(e -> model.replaceAll(
                inputArea.getText(),
                oldCharTextField.getText(),
                newCharTextField.getText()));

        // history view
        historyList.setCellFactory(param ->
                new ListCell<>() {
                    @Override
                    protected void updateItem(HistoryData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setText(null);
                        if (item != null)
                            setText(item.getName());
                    }
                }
        );
        historyList.getSelectionModel().selectedItemProperty()
                .addListener((obv, oldVal, newVal) -> outputArea.setText(newVal.getContent()));
    }
}
