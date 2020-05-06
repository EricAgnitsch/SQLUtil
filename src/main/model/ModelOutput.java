package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelOutput {
    private StringProperty outputText = new SimpleStringProperty(this, "outputText", "");
    private StringProperty request = new SimpleStringProperty(this, "request", "");

    public String getOutputText() {
        return outputText.get();
    }

    public StringProperty outputTextProperty() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText.set(outputText);
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
