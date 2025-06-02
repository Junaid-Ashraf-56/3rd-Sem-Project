package com.example.walletmanagementsystem.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane loginform;

    @FXML
    private TextField LoginEmail;

    @FXML
    private PasswordField LoginPassword;

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private Button LoginData;

    @FXML
    private Hyperlink Signup;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();
        String role = myComboBox.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }


        if (email.equals("admin@example.com") && password.equals("1234") && role.equals("Admin")) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Admin!");

        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }


    @FXML
    void switchToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/login.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) Signup.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
