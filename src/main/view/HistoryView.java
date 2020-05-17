package main.view;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.HistoryData;
import main.model.AbstractSQLUtilModel;
import main.model.HistoryObserver;

public class HistoryView extends Layout implements HistoryObserver {
    private ListView<HistoryData> historyList = new ListView<>();

    public HistoryView(AbstractSQLUtilModel model) {
        model.registerHistoryObserver(this);

        VBox.setVgrow(historyList, Priority.ALWAYS);
    }

    public ListView<HistoryData> getHistoryList() {
        return historyList;
    }

    @Override
    public void addHistory(HistoryData historyData) {
        historyList.getItems().add(historyData);
    }

    @Override
    Node getLayout() {
        return historyList;
    }
}
