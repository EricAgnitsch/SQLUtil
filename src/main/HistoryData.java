package main;

public class HistoryData {
    private String name;
    private String content;

    public HistoryData(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
