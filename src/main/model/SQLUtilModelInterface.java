package main.model;

public interface SQLUtilModelInterface {
    void setInputText(String inputText);
    void registerInputObserver(InputObserver o);
    void notifyInputObservers(String inputText);
    void setOutputText(String outputText);
    void registerOutputObserver(OutputObserver o);
    void notifyOutputObservers(String outputText);
    void setPasteLink(String pasteURL);
    void registerPasteObserver(PasteObserver o);
    void notifyPasteObservers(String pasteURL);
}
