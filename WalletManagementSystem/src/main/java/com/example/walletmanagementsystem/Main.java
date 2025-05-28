package com.example.walletmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("first.fxml"));
        primaryStage.setTitle("CashMate Wallet");
        primaryStage.setScene(new Scene(root, 1363, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
