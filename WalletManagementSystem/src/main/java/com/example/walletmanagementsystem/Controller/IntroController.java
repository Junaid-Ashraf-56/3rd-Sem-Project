package com.example.walletmanagementsystem.Controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {


    @FXML private FontIcon creditCardIcon;
    @FXML private Label cashmatelabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ParallelTransition animation = new ParallelTransition();

        // Animate walletName (slide from left)

        // Animate creditCardIcon (fade and scale)
        if (creditCardIcon != null) {
            creditCardIcon.setOpacity(0);
            creditCardIcon.setScaleX(0.5);
            creditCardIcon.setScaleY(0.5);
            FadeTransition iconFade = new FadeTransition(Duration.seconds(1), creditCardIcon);
            iconFade.setToValue(1.0);
            ScaleTransition iconScale = new ScaleTransition(Duration.seconds(1), creditCardIcon);
            iconScale.setToX(1.0);
            iconScale.setToY(1.0);
            animation.getChildren().addAll(iconFade, iconScale);
        } else {
            System.err.println("creditCardIcon is null. Check fx:id in Intro.fxml.");
        }

        // Animate cashMateLink (slide from right)
        if (cashmatelabel != null) {
            cashmatelabel.setOpacity(0);
            cashmatelabel.setTranslateX(200); // Start off-screen right
            FadeTransition linkFade = new FadeTransition(Duration.seconds(1), cashmatelabel);
            linkFade.setToValue(1.0);
            TranslateTransition linkSlide = new TranslateTransition(Duration.seconds(1), cashmatelabel);
            linkSlide.setToX(0); // Slide to center
            animation.getChildren().addAll(linkFade, linkSlide);
        } else {
            System.err.println("cashmatelabel is null. Check fx:id in Intro.fxml.");
        }

        // Navigate to Login.fxml after animation + delay
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) (cashmatelabel != null ? cashmatelabel.getScene().getWindow() :
                                creditCardIcon.getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to load Login.fxml: " + e.getMessage());
            }
        }));

        // Play animation and then timeline if there are animations
        if (!animation.getChildren().isEmpty()) {
            animation.setOnFinished(event -> timeline.play());
            animation.play();
        } else {
            // Skip animation if all nodes are null
            timeline.play();
        }
    }
}