package main.controller;

public interface SQLUtilControllerInterface {
    void loadPaste(String pasteURL);
    void format(String input);
    void createPaste(String output);
    void openLink(String linkURL);
    void split(String input, String splitStr);
    void replaceAll(String input, String oldStr, String newStr);
}
