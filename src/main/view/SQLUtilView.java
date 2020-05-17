package main.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.HistoryData;
import main.model.*;

public class SQLUtilView extends HBox implements SQLUtilViewInterface {
    private AbstractSQLUtilModel model;

    private LoadPasteView loadPasteView;
    private InputView inputView;
    private OutputView outputView;
    private PasteView pasteView;
    private SplitView splitView;
    private ReplaceView replaceView;
    private HistoryView historyView;

    // insert split
    private Button insertSplitButton = new Button("Insert Statement Split");
    // history
    public SQLUtilView(AbstractSQLUtilModel model) {
        this.model = model;
    }

    public void createView() {
        loadPasteView = new LoadPasteView();
        inputView = new InputView(model);
        outputView = new OutputView(model);
        pasteView = new PasteView(model);
        splitView = new SplitView();
        replaceView = new ReplaceView();
        insertSplitInit();
        historyView = new HistoryView(model);

        VBox leftContent = new VBox();
        leftContent.setPadding(new Insets(8, 8, 8, 8));
        leftContent.setSpacing(4);
        leftContent.getChildren().addAll(
                loadPasteView.getLayout(),
                inputView.getLayout(),
                outputView.getLayout(),
                pasteView.getLayout());

        VBox rightContent = new VBox();
        rightContent.setPadding(new Insets(8,8,8,8));
        rightContent.setSpacing(4);
        rightContent.getChildren().addAll(
                splitView.getLayout(),
                replaceView.getLayout(),
                insertSplitButton,
                historyView.getLayout());

        HBox.setHgrow(leftContent, Priority.ALWAYS);
        getChildren().addAll(leftContent, rightContent);
    }

    private void insertSplitInit() {
        // layout
//        insertSplitButton.prefWidthProperty().bind(replaceLayout.widthProperty());
        // controls
        insertSplitButton.setDisable(true);
    }

    @Override
    public TextArea getInputArea() {
        return inputView.getInputArea();
    }

    @Override
    public TextField getLoadPasteTextField() {
        return loadPasteView.getLoadPasteTextField();
    }

    @Override
    public Button getLoadPasteButton() {
        return loadPasteView.getLoadPasteButton();
    }

    @Override
    public TextArea getOutputArea() {
        return outputView.getOutputArea();
    }

    @Override
    public CheckBox getPasteCheck() {
        return pasteView.getPasteCheck();
    }

    @Override
    public Hyperlink getPasteLink() {
        return pasteView.getPasteLink();
    }

    @Override
    public TextField getSplitTextField() {
        return splitView.getSplitTextField();
    }

    @Override
    public Button getSplitButton() {
        return splitView.getSplitButton();
    }

    @Override
    public TextField getOldCharTextField() {
        return replaceView.getOldCharTextField();
    }

    @Override
    public TextField getNewCharTextField() {
        return replaceView.getNewCharTextField();
    }

    @Override
    public Button getReplaceButton() {
        return replaceView.getReplaceButton();
    }

    @Override
    public ListView<HistoryData> getHistoryList() {
        return historyView.getHistoryList();
    }
}
