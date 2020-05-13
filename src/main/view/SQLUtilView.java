package main.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.HistoryData;
import main.controller.SQLUtilControllerInterface;
import main.model.*;

public class SQLUtilView extends HBox implements OutputObserver, PasteObserver, HistoryObserver {
    private SQLUtilControllerInterface controller;
    private SQLUtilModelInterface model;

    // output
    private VBox outputLayout = new VBox();
    private Label outputLabel = new Label("Output (CTRL + ENTER to create pastebin URL):");
    private TextArea outputArea = new TextArea();
    // paste
    private HBox pasteLayout = new HBox();
    private Label pasteLabel = new Label("Pastebin URL:");
    private Hyperlink pasteLink = new Hyperlink();
    private CheckBox pasteCheck = new CheckBox("Automatically open paste link in browser");
    // split
    private TitledPane splitLayout = new TitledPane();
    private Label splitLabel = new Label("New line after:");
    private TextField splitTextField = new TextField();
    private Button splitButton = new Button("Split");
    // replace
    private TitledPane replaceLayout = new TitledPane();
    private Label oldCharLabel = new Label("Old char(s):");
    private TextField oldCharTextField = new TextField();
    private Label newCharLabel = new Label("New char(s):");
    private TextField newCharTextField = new TextField();
    private Button replaceButton = new Button("Replace");
    // insert split
    private Button insertSplitButton = new Button("Insert Statement Split");
    // history
    private ListView<HistoryData> historyList = new ListView<>();

    public SQLUtilView(SQLUtilModelInterface model) {
        this.model = model;

        model.registerOutputObserver(this);
        model.registerPasteObserver(this);
        model.registerHistoryObserver(this);
    }

    public void setController(SQLUtilControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void updateOutputText(String outputText) {
        outputArea.setText(outputText);
    }

    @Override
    public void updatePasteLink(String pasteURL) {
        pasteLink.setText(pasteURL);
    }

    @Override
    public void addHistory(HistoryData historyData) {
        historyList.getItems().add(historyData);
    }

    public void createView() {
        LoadPasteView loadPasteView = new LoadPasteView(controller);
        InputView inputView = new InputView(controller, model);
        outputInit();
        pasteInit();
        splitInit();
        replaceInit();
        insertSplitInit();
        historyInit();

        VBox leftContent = new VBox();
        leftContent.setPadding(new Insets(8, 8, 8, 8));
        leftContent.setSpacing(4);
        leftContent.getChildren().addAll(
                loadPasteView.getLayout(),
                inputView.getLayout(),
                outputLayout,
                pasteLayout);

        VBox rightContent = new VBox();
        rightContent.setPadding(new Insets(8,8,8,8));
        rightContent.setSpacing(4);
        rightContent.getChildren().addAll(splitLayout, replaceLayout, insertSplitButton, historyList);

        HBox.setHgrow(leftContent, Priority.ALWAYS);
        getChildren().addAll(leftContent, rightContent);
    }

    private void outputInit() {
        // layout
        outputLayout.getChildren().addAll(outputLabel, outputArea);
        outputLayout.setSpacing(4);
        VBox.setVgrow(outputLayout, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        outputArea.setEditable(false);

        // controls
        outputArea.setOnKeyPressed(e -> {
            String output = outputArea.getText();
            if (!output.isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER)
                controller.createPaste(output, pasteCheck.isSelected());
        });
    }

    private void pasteInit() {
        // layout
        Region reg = new Region();
        HBox.setHgrow(reg, Priority.ALWAYS);
        align(pasteLabel, pasteLink);
        align(pasteCheck, pasteLink);
        pasteLayout.getChildren().addAll(pasteLabel, pasteLink, reg, pasteCheck);
        pasteLayout.setSpacing(4);

        // controls
        pasteLink.setOnAction(e -> controller.openLink(pasteLink.getText()));
    }

    private void splitInit() {
        // layout
        VBox layout = new VBox();
        layout.setSpacing(4);
        HBox splitContent = new HBox();
        align(splitLabel, splitTextField);
        grow(splitTextField);
        splitTextField.setText(",");
        splitContent.setSpacing(4);
        splitContent.getChildren().addAll(splitLabel, splitTextField);
        splitButton.disableProperty().bind(inputArea.textProperty().isEmpty());
        splitButton.prefWidthProperty().bind(layout.widthProperty().subtract(20));
        layout.getChildren().addAll(splitContent, splitButton);
        splitLayout.setText("Split to new line");
        splitLayout.setContent(layout);

        // controls
        splitButton.setOnAction(e -> controller.split(
                inputArea.getText(),
                splitTextField.getText()));
    }

    private void replaceInit() {
        // layout
        VBox layout = new VBox();
        layout.setSpacing(4);
        HBox oldCharLayout = new HBox();
        align(oldCharLabel,  oldCharTextField);
        grow(oldCharTextField);
        oldCharTextField.setText("/");
        oldCharLayout.setSpacing(4);
        oldCharLayout.getChildren().addAll(oldCharLabel, oldCharTextField);
        HBox newCharLayout = new HBox();
        align(newCharLabel,  newCharTextField);
        grow(newCharTextField);
        newCharTextField.setText(".");
        newCharLayout.setSpacing(4);
        newCharLayout.getChildren().addAll(newCharLabel, newCharTextField);
        replaceButton.disableProperty().bind(inputArea.textProperty().isEmpty());
        replaceButton.prefWidthProperty().bind(layout.widthProperty().subtract(20));
        layout.getChildren().addAll(oldCharLayout, newCharLayout, replaceButton);
        replaceLayout.setText("Replace characters");
        replaceLayout.setContent(layout);

        // controls
        replaceButton.setOnAction(e -> controller.replaceAll(
                inputArea.getText(),
                oldCharTextField.getText(),
                newCharTextField.getText()));
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
                .addListener((obv, oldVal, newVal) ->outputArea.setText(newVal.getContent()));
    }

    private void align(Control c, Control c2) {
        c.prefHeightProperty().bind(c2.heightProperty());
    }

    private void grow(Node n) {
        HBox.setHgrow(n, Priority.ALWAYS);
    }
}
