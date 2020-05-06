package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.model.ModelInput;
import main.model.ModelOutput;
import main.model.ModelPaste;
import main.view.ViewOutput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

public class ControllerOutput {
    private ViewOutput viewOutput;
    private ModelInput modelInput;
    private ModelOutput modelOutput;
    private ModelPaste modelPaste;

    public ControllerOutput(ViewOutput viewOutput, ModelInput modelInput, ModelOutput modelOutput, ModelPaste modelPaste) {
        this.viewOutput = viewOutput;
        this.modelInput = modelInput;
        this.modelOutput = modelOutput;
        this.modelPaste = modelPaste;

        modelOutput.outputTextProperty().addListener(outputTextPropertyListener());
        modelOutput.requestProperty().addListener(requestPropertyListener());

        viewOutput.getOutputArea().setOnKeyPressed(outputAreaKeyListener());
    }

    private ChangeListener<? super String> requestPropertyListener() {
        return (v, oldVal, newVal) -> viewOutput.getOutputArea().requestFocus();
    }

    protected ChangeListener<? super String> outputTextPropertyListener() {
        return (v, oldVal, newVal) -> viewOutput.getOutputArea().setText(newVal);
    }

    protected EventHandler<? super KeyEvent> outputAreaKeyListener() {
        return e -> {
            if (!modelOutput.getOutputText().isEmpty()
                    && e.isControlDown()
                    && e.getCode() == KeyCode.ENTER) {
                String pasteURL = createPasteURL(modelOutput.getOutputText());
                modelPaste.setPasteURL(pasteURL);
//                if (modelPaste.isSelected())
//                    openLink();
                modelPaste.setRequest("beep");
            }
        };
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
}
