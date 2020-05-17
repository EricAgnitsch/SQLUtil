package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.SQLUtilController;
import main.model.*;
import main.view.*;

public class SQLApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("SQL Utility");

        AbstractSQLUtilModel model = new SQLUtilModel();
        model.setHostServices(getHostServices());
        SQLUtilView view = new SQLUtilView(model);
        new SQLUtilController(view, model);

        StackPane layout = new StackPane();
        layout.getChildren().add(view);

        window.setScene(new Scene(layout, 1024, 768));
        window.show();
    }
}
