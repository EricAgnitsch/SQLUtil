package main.view;

import javafx.scene.control.*;
import main.HistoryData;

public interface SQLUtilViewInterface {
    TextArea getInputArea();
    TextField getLoadPasteTextField();
    Button getLoadPasteButton();
    TextArea getOutputArea();
    CheckBox getPasteCheck();
    Hyperlink getPasteLink();
    TextField getSplitTextField();
    Button getSplitButton();
    TextField getOldCharTextField();
    TextField getNewCharTextField();
    Button getReplaceButton();
    ListView<HistoryData> getHistoryList();
}
