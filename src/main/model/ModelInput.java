package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelInput {
    private StringProperty inputText = new SimpleStringProperty(this, "inputText", "");
    private StringProperty request = new SimpleStringProperty(this, "request", "");

    public String getInputText() {
        return inputText.get();
    }

    public StringProperty inputTextProperty() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText.set(inputText);
    }

    public String getRequest() {
        return request.get();
    }

    public StringProperty requestProperty() {
        return request;
    }

    public void setRequest(String request) {
        this.request.set(request);
    }
}
