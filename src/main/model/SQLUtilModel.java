package main.model;

import main.HistoryData;

import java.util.ArrayList;

public class SQLUtilModel implements SQLUtilModelInterface {
    private ArrayList<InputObserver> inputObservers = new ArrayList<>();
    private ArrayList<OutputObserver> outputObservers = new ArrayList<>();
    private ArrayList<PasteObserver> pasteObservers = new ArrayList<>();
    private ArrayList<HistoryObserver> historyObservers = new ArrayList<>();

    private int count = 1;

    @Override
    public void setInputText(String inputText) {
        notifyInputObservers(inputText);
    }

    @Override
    public void registerInputObserver(InputObserver o) {
        inputObservers.add(o);
    }

    @Override
    public void notifyInputObservers(String inputText) {
        for (InputObserver inputObserver: inputObservers)
            inputObserver.updateInputText(inputText);
    }

    @Override
    public void setOutputText(String outputText) {
        notifyOutputObservers(outputText);
    }

    @Override
    public void registerOutputObserver(OutputObserver o) {
        outputObservers.add(o);
    }

    @Override
    public void notifyOutputObservers(String outputText) {
        for (OutputObserver outputObserver: outputObservers)
            outputObserver.updateOutputText(outputText);
    }

    @Override
    public void setPasteLink(String pasteURL) {
        notifyPasteObservers(pasteURL);
    }

    @Override
    public void registerPasteObserver(PasteObserver o) {
        pasteObservers.add(o);
    }

    @Override
    public void notifyPasteObservers(String pasteURL) {
        for (PasteObserver pasteObserver: pasteObservers)
            pasteObserver.updatePasteLink(pasteURL);
    }

    @Override
    public void addHistoryData(String name, String content) {
        notifyHistoryObservers(new HistoryData(count + " | " + name, content));
        count++;
    }

    @Override
    public void registerHistoryObserver(HistoryObserver o) {
        historyObservers.add(o);
    }

    @Override
    public void notifyHistoryObservers(HistoryData historyData) {
        for (HistoryObserver historyObserver: historyObservers)
            historyObserver.addHistory(historyData);
    }
}
