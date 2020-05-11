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

        SQLUtilModelInterface model = new SQLUtilModel();
        SQLUtilView view = new SQLUtilView(model);
        SQLUtilController controller = new SQLUtilController(view, model);
        controller.setHostServices(getHostServices());

        StackPane layout = new StackPane();
        layout.getChildren().add(view);

        window.setScene(new Scene(layout, 1024, 768));
        window.show();
    }
}
