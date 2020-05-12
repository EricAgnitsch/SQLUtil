package main.model;

import main.HistoryData;

public interface HistoryObserver {
    void addHistory(HistoryData historyData);
}
