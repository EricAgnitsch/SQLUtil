package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

public class App extends Application {
    // load paste
    private TitledPane loadPasteLayout = new TitledPane();
    private Label loadPasteLabel = new Label("Insert link (https://p.teknik.io/Raw/#####):");
    private TextField loadPasteTextField = new TextField();
    private Button loadPasteButton = new Button("Load to input");
    // input
    private VBox inputLayout = new VBox();
    private Label inputLabel = new Label("Input (CTRL + ENTER to format SQL):");
    private TextArea inputArea = new TextArea();
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
    private int count = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SQL Utility");

        loadPasteInit();
        inputInit();
        outputInit();
        pasteInit();
        splitInit();
        replaceInit();
        insertSplitInit();
        historyInit();

        VBox leftContent = new VBox();
        leftContent.setPadding(new Insets(8, 8, 8, 8));
        leftContent.setSpacing(4);
        leftContent.getChildren().addAll(loadPasteLayout, inputLayout, outputLayout, pasteLayout);
//        leftContent.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox rightContent = new VBox();
        rightContent.setPadding(new Insets(8,8,8,8));
        rightContent.setSpacing(4);
        rightContent.getChildren().addAll(splitLayout, replaceLayout, insertSplitButton, historyList);

        HBox layout = new HBox();
        HBox.setHgrow(leftContent, Priority.ALWAYS);
        layout.getChildren().addAll(leftContent, rightContent);

        Scene scene = new Scene(layout, 1024, 768);

        inputArea.requestFocus();

        stage.setScene(scene);
        stage.show();
    }

    private void loadPasteInit() {
        // layout
        HBox layout = new HBox();
        layout.setSpacing(4);
        align(loadPasteLabel, loadPasteTextField);
        HBox.setHgrow(loadPasteTextField, Priority.ALWAYS);
        layout.getChildren().addAll(loadPasteLabel, loadPasteTextField, loadPasteButton);
        loadPasteLayout.setText("Load paste to input");
        loadPasteLayout.setContent(layout);
        // controls
        loadPasteButton.setOnAction(e -> {
            String pasteLink = loadPasteTextField.getText();
            if (pasteLink.contains("https://p.teknik.io/Raw/")) {
                String result = "";
                try {
                    String command = "curl " + pasteLink;
                    Process process = Runtime.getRuntime().exec(command);
                    InputStream inputStream = process.getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                    result = s.hasNext() ? s.next() : "";
                    if (result.startsWith("<!DOCTYPE html>"))
                        inputArea.setText("[teknik paste not found]");
                    else
                        inputArea.setText(result);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                addHistory("Loaded teknik paste", pasteLink);
            }
        });
    }

    private void inputInit() {
        // layout
        inputLayout.getChildren().addAll(inputLabel, inputArea);
        inputLayout.setSpacing(4);
        VBox.setVgrow(inputLayout, Priority.ALWAYS);
        VBox.setVgrow(inputArea, Priority.ALWAYS);
        // controls
        inputArea.setOnKeyPressed(e -> {
            if (!inputArea.getText().isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER) {
                String output = formatSQL(inputArea.getText());
                outputArea.setText(output);
                addHistory("Format SQL", output);
                outputArea.requestFocus();
            }
        });
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
            if (!outputArea.getText().isEmpty() && e.isControlDown() && e.getCode() == KeyCode.ENTER) {
                String pasteURL = createPasteURL(outputArea.getText());
                pasteLink.setText(pasteURL);
                addHistory("Paste URL created", pasteURL);
                if (pasteCheck.isSelected())
                    openLink();
                pasteLink.requestFocus();
            }
            else if (e.getCode() == KeyCode.TAB)
                inputArea.requestFocus();
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
        pasteLink.setOnAction(e -> {
            pasteLink.setVisited(true);
            openLink();
            inputArea.requestFocus();
        });
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
        splitButton.prefWidthProperty().bind(layout.widthProperty());
        layout.getChildren().addAll(splitContent, splitButton);
        splitLayout.setText("Split to new line");
        splitLayout.setContent(layout);
        // controls
        splitButton.setOnAction(e -> {
            String input = inputArea.getText();
            String splitStr = splitTextField.getText();
            String output = input.replaceAll(splitStr, splitStr + "\n");
            outputArea.setText(output);
            addHistory("Split", output);
        });
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
        replaceButton.prefWidthProperty().bind(layout.widthProperty());
        layout.getChildren().addAll(oldCharLayout, newCharLayout, replaceButton);
        replaceLayout.setText("Replace characters");
        replaceLayout.setContent(layout);
        // controls
        replaceButton.setOnAction(e -> {
            String input = inputArea.getText();
            String output = input.replaceAll("[" +oldCharTextField.getText() + "]"
                    , newCharTextField.getText());
            outputArea.setText(output);
            addHistory("Replaced '" + oldCharTextField.getText() +
                    "' with '" + newCharTextField.getText() + "'", output);
        });
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

    private String formatSQL(String sql) {
        try {
            String command = "curl --data-urlencode \"rqst_input_sql=" + sql + "\" http://www.gudusoft.com/format.php";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            return jsonObject.get("rspn_formatted_sql").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String createPasteURL(String paste) {
        String result = "[Failed paste]";
        try {
            File file = createTempFile(paste);
            String command = "curl --data \"title=Paste%20Title&expireUnit=view&expireLength=3\" " +
                    "--data-urlencode \"code@" + file.getPath() + "\" https://api.teknik.io/v1/Paste";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            System.out.println(result);
            result = ((JSONObject) jsonObject.get("result")).get("url").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private File createTempFile(String paste) {
        try {
            File file = File.createTempFile("paste", ".txt");
            file.deleteOnExit();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(paste);
            bufferedWriter.close();
            return file;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void align(Control c, Control c2) {
        c.prefHeightProperty().bind(c2.heightProperty());
    }

    private void grow(Node n) {
        HBox.setHgrow(n, Priority.ALWAYS);
    }

    private void openLink() {
        getHostServices().showDocument(pasteLink.getText());
    }

    private void addHistory(String name, String content) {
        historyList.getItems().add(new HistoryData(count + " | " + name, content));
        count++;
    }

    // TODO make copy to clipboard work
    // TODO make a paste editable menu?
    private void copyToClipboard() {
//        String[] cmdLine = { "powershell.exe", "cat example.txt | clip"};
//        Process process = Runtime.getRuntime().exec(cmdLine);
//        InputStream inputStream = process.getInputStream();
//        Scanner scanner = new Scanner(inputStream);
//        String s = scanner.hasNext() ? scanner.next() : "";
//        System.out.println(s);
    }
}
