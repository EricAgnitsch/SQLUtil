package main.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.HistoryData;
import main.controller.SQLUtilControllerInterface;
import main.model.*;

public class SQLUtilView extends HBox implements HistoryObserver {
    private SQLUtilControllerInterface controller;
    private SQLUtilModelInterface model;

    private LoadPasteView loadPasteView;
    private InputView inputView;
    private OutputView outputView;
    private PasteView pasteView;
    private SplitView splitView;
    private ReplaceView replaceView;

    // insert split
    private Button insertSplitButton = new Button("Insert Statement Split");
    // history
    private ListView<HistoryData> historyList = new ListView<>();

    public SQLUtilView(SQLUtilModelInterface model) {
        this.model = model;

        model.registerHistoryObserver(this);
    }

    public void setController(SQLUtilControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void addHistory(HistoryData historyData) {
        historyList.getItems().add(historyData);
    }

    public void createView() {
        loadPasteView = new LoadPasteView(controller);
        inputView = new InputView(controller, model);
        outputView = new OutputView(controller, model);
        pasteView = new PasteView(controller, model);
        splitView = new SplitView(controller);
        replaceView = new ReplaceView();
        insertSplitInit();
        historyInit();

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
                historyList);

        HBox.setHgrow(leftContent, Priority.ALWAYS);
        getChildren().addAll(leftContent, rightContent);
    }

    private void insertSplitInit() {
        // layout
        insertSplitButton.prefWidthProperty().bind(replaceLayout.widthProperty());
        // controls
        insertSplitButton.setDisable(true);
    }

    private void historyInit() {
        // layout
        VBox.setVgrow(historyList, Priority.ALWAYS);
        // controls
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

    public String getInputText() {
        return inputView.getInputText();
    }
}
