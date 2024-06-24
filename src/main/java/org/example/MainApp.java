package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        GridPane root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Hukka Lounge - Table Reservation");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}