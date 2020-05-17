package main.model;

import javafx.application.HostServices;
import main.HistoryData;

import java.util.ArrayList;

public abstract class AbstractSQLUtilModel {
    private ArrayList<InputObserver> inputObservers = new ArrayList<>();
    private ArrayList<OutputObserver> outputObservers = new ArrayList<>();
    private ArrayList<PasteObserver> pasteObservers = new ArrayList<>();
    private ArrayList<HistoryObserver> historyObservers = new ArrayList<>();

    public abstract void setInputText(String inputText);
    public abstract void setOutputText(String outputText);
    public abstract void setPasteLink(String pasteURL);
    public abstract void addHistoryData(String name, String content);

    public abstract void format(String input);
    public abstract void loadPaste(String pasteURL);
    public abstract void createPaste(String output);
    public abstract void openLink(String linkURL);
    public abstract void split(String input, String splitStr);
    public abstract void replaceAll(String input, String oldStr, String newStr);
    public abstract void setHostServices(HostServices hostServices);

    public void registerInputObserver(InputObserver o) {
        inputObservers.add(o);
    }

    public void notifyInputObservers(String inputText) {
        for (InputObserver inputObserver: inputObservers)
            inputObserver.updateInputText(inputText);
    }

    public void registerOutputObserver(OutputObserver o) {
        outputObservers.add(o);
    }

    public void notifyOutputObservers(String outputText) {
        for (OutputObserver outputObserver: outputObservers)
            outputObserver.updateOutputText(outputText);
    }

    public void registerPasteObserver(PasteObserver o) {
        pasteObservers.add(o);
    }

    public void notifyPasteObservers(String pasteURL) {
        for (PasteObserver pasteObserver: pasteObservers)
            pasteObserver.updatePasteLink(pasteURL);
    }

    public void registerHistoryObserver(HistoryObserver o) {
        historyObservers.add(o);
    }

    public void notifyHistoryObservers(HistoryData historyData) {
        for (HistoryObserver historyObserver: historyObservers)
            historyObserver.addHistory(historyData);
    }
}
