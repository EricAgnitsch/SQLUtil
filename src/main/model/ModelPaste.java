package main.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelPaste {
    private StringProperty request = new SimpleStringProperty(this, "request", "");
    private StringProperty pasteURL = new SimpleStringProperty(this, "pasteURL", "");
    private BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public String getRequest() {
        return request.get();
    }

    public StringProperty requestProperty() {
        return request;
    }

    public void setRequest(String request) {
        this.request.set(request);
    }

    public String getPasteURL() {
        return pasteURL.get();
    }

    public StringProperty pasteURLProperty() {
        return pasteURL;
    }

    public void setPasteURL(String pasteURL) {
        this.pasteURL.set(pasteURL);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
