package main.model;

import java.util.ArrayList;

public class SQLUtilModel implements SQLUtilModelInterface {
    private ArrayList<InputObserver> inputObservers = new ArrayList<>();
    private ArrayList<OutputObserver> outputObservers = new ArrayList<>();
    private ArrayList<PasteObserver> pasteObservers = new ArrayList<>();
    private String inputText;
    private String outputText;
    private String pasteURL;

    @Override
    public void setInputText(String inputText) {
        this.inputText = inputText;
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
        this.outputText = outputText;
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
        this.pasteURL = pasteURL;
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
}
