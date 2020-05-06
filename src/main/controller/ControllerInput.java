package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.model.ModelInput;
import main.model.ModelOutput;
import main.view.ViewInput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.util.Scanner;

public class ControllerInput {
    private ViewInput viewInput;
    private ModelInput modelInput;
    private ModelOutput modelOutput;

    public ControllerInput(ViewInput viewInput, ModelInput modelInput, ModelOutput modelOutput) {
        this.viewInput = viewInput;
        this.modelInput = modelInput;
        this.modelOutput = modelOutput;

        modelInput.inputTextProperty().bind(viewInput.getInputArea().textProperty());
        modelInput.requestProperty().addListener(requestPropertyListener());

        viewInput.getInputArea().setOnKeyPressed(inputAreaKeyListener());
    }

    private ChangeListener<? super String> requestPropertyListener() {
        return (v, oldVal, newVal) -> viewInput.getInputArea().requestFocus();
    }

    protected EventHandler<? super KeyEvent> inputAreaKeyListener() {
        return e -> {
            if (!modelInput.getInputText().isEmpty()
                    && e.isControlDown()
                    && e.getCode() == KeyCode.ENTER) {
                String output = formatSQL(modelInput.inputTextProperty().get());
                modelOutput.setOutputText(output);
                modelOutput.setRequest("beep");
            }
        };
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
}
