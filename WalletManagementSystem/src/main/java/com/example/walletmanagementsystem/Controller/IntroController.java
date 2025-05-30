package com.example.walletmanagementsystem;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

    @FXML
    private Label walletName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        walletName.setOpacity(0);
        walletName.setTranslateY(30);

        FadeTransition fade = new FadeTransition(Duration.seconds(1), walletName);
        fade.setToValue(1.0);

        TranslateTransition slide = new TranslateTransition(Duration.seconds(1), walletName);
        slide.setToY(0);

        ParallelTransition animation = new ParallelTransition(fade, slide);
        animation.play();
    }
}
