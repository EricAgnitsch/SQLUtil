package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.controller.ControllerInput;
import main.controller.ControllerOutput;
import main.controller.ControllerPaste;
import main.model.ModelInput;
import main.model.ModelOutput;
import main.model.ModelPaste;
import main.view.ViewInput;
import main.view.ViewOutput;
import main.view.ViewPaste;

public class SQLApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("SQL Utility");

        ModelInput modelInput = new ModelInput();
        ModelOutput modelOutput = new ModelOutput();
        ModelPaste modelPaste = new ModelPaste();

        ViewInput viewInput = new ViewInput();
        ViewOutput viewOutput = new ViewOutput();
        ViewPaste viewPaste = new ViewPaste();

        new ControllerInput(viewInput, modelInput, modelOutput);
        new ControllerOutput(viewOutput, modelInput, modelOutput, modelPaste);
        new ControllerPaste(viewPaste, modelInput, modelPaste);

        VBox left = new VBox();
        left.setPadding(new Insets(8, 8, 8, 8));
        left.setSpacing(4);
        left.getChildren().addAll(
                viewInput.getLayout(),
                viewOutput.getLayout(),
                viewPaste.getLayout());
        Scene scene = new Scene(left);

        window.setScene(scene);
        window.show();
    }
}
