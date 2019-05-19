package com.eralpsahin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Modified by eralpsahin on 03.03.2019.
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));

        primaryStage.setTitle("Akari Solver");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

