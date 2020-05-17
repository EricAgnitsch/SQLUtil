package main.model;

import javafx.application.HostServices;
import main.HistoryData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

public class SQLUtilModel extends AbstractSQLUtilModel {
    private HostServices hostServices;
    private int count = 1;

    @Override
    public void format(String input) {
//        new Thread(() -> {
        try {
            String command = "curl --data-urlencode \"rqst_input_sql=" + input + "\" http://www.gudusoft.com/format.php";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            String output = jsonObject.get("rspn_formatted_sql").toString();
            setOutputText(output);
            addHistoryData("Format SQL", output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        }).start();
    }

    @Override
    public void loadPaste(String pasteURL) {
        if (pasteURL.contains("https://p.teknik.io/")) {
            try {
                pasteURL = pasteURL.replace("https://p.teknik.io/", "https://p.teknik.io/Raw/");
                String command = "curl " + pasteURL;
                Process process = Runtime.getRuntime().exec(command);
                InputStream inputStream = process.getInputStream();
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                if (result.startsWith("<!DOCTYPE html>"))
                    result = "[teknik paste not found]";
                setInputText(result);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            addHistoryData("Loaded teknik paste", pasteURL);
        }
    }

    @Override
    public void createPaste(String output) {
        try {
            File file = createTempFile(output);
            String command = "curl --data \"title=Paste%20Title&expireUnit=view&expireLength=3\" " +
                    "--data-urlencode \"code@" + file.getPath() + "\" https://api.teknik.io/v1/Paste";
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String pasteURL = s.hasNext() ? s.next() : "[Failed paste]";
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(pasteURL);
            pasteURL = ((JSONObject) jsonObject.get("result")).get("url").toString();
            setPasteLink(pasteURL);
            addHistoryData("Paste URL created", pasteURL);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @Override
    public void openLink(String linkURL) {
        hostServices.showDocument(linkURL);
    }

    @Override
    public void split(String input, String splitStr) {
        String outputText = input.replaceAll(splitStr, splitStr + "\n");
        setOutputText(outputText);
        addHistoryData("Split", outputText);
    }

    @Override
    public void replaceAll(String input, String oldStr, String newStr) {
        String outputText = input.replace(oldStr, newStr);
        setOutputText(outputText);
        addHistoryData("Replaced '" + oldStr + "' with '" + newStr + "'", outputText);
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

    @Override
    public void setInputText(String inputText) {
        notifyInputObservers(inputText);
    }

    @Override
    public void setOutputText(String outputText) {
        notifyOutputObservers(outputText);
    }

    @Override
    public void setPasteLink(String pasteURL) {
        notifyPasteObservers(pasteURL);
    }

    @Override
    public void addHistoryData(String name, String content) {
        notifyHistoryObservers(new HistoryData(count + " | " + name, content));
        count++;
    }
}
