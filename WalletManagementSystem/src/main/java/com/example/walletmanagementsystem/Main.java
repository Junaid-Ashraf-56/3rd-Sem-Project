package com.example.walletmanagementsystem;

import com.example.walletmanagementsystem.Controller.LSController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        loader.setController(new LSController());
        Parent root = loader.load();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
