package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.service.WalletService;
import com.example.walletmanagementsystem.utils.AlertUtil;
import com.example.walletmanagementsystem.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class LoginController {

    @FXML private AnchorPane loginform;
    @FXML private TextField LoginEmail;
    @FXML private PasswordField LoginPassword;
    @FXML private ComboBox<String> myComboBox;
    @FXML private Hyperlink Signup;
    @FXML private Button Login;
    @FXML private AnchorPane Iloginform;
    @FXML private PasswordField ILoginPassword;
    @FXML private FontIcon togglePasswordIcon;
    private TextField passwordTextField;
    private boolean isPasswordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Switch to PasswordField (hide password)
            String password = passwordTextField.getText();
            loginform.getChildren().remove(passwordTextField);
            LoginPassword = new PasswordField();
            LoginPassword.setText(password);
            LoginPassword.setLayoutX(229.0);
            LoginPassword.setLayoutY(340.0);
            LoginPassword.setPrefHeight(46.0);
            LoginPassword.setPrefWidth(323.0);
            LoginPassword.setPromptText("Password");
            LoginPassword.setStyle("-fx-background-radius: 25px; -fx-border-radius: 25px;");
            LoginPassword.getStyleClass().add("signupbutton");
            loginform.getChildren().add(LoginPassword);
            togglePasswordIcon.setIconLiteral("fas-eye");
            isPasswordVisible = false;
        } else {
            // Switch to TextField (show password)
            String password = LoginPassword.getText();
            loginform.getChildren().remove(LoginPassword);
            passwordTextField = new TextField();
            passwordTextField.setText(password);
            passwordTextField.setLayoutX(229.0);
            passwordTextField.setLayoutY(340.0);
            passwordTextField.setPrefHeight(46.0);
            passwordTextField.setPrefWidth(323.0);
            passwordTextField.setPromptText("Password");
            passwordTextField.setStyle("-fx-background-radius: 25px; -fx-border-radius: 25px;");
            passwordTextField.getStyleClass().add("signupbutton");
            loginform.getChildren().add(passwordTextField);
            togglePasswordIcon.setIconLiteral("fas-eye-slash");
            isPasswordVisible = true;
        }
    }

    private WalletService walletService = new WalletService();

    @FXML
    private void initialize() {
        // Populate ComboBox with Role values
        myComboBox.getItems().setAll(Role.ADMIN.name(), Role.USER.name());
        myComboBox.setValue(Role.USER.name()); // Default value
        // Debug initialization
        System.out.println("LoginController initialized. LoginEmail: " + (LoginEmail != null) + ", LoginPassword: " + (LoginPassword != null));
    }


    @FXML
    void handleLogin(ActionEvent event) {
        if (LoginEmail == null || LoginPassword == null || myComboBox == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Initialization Error", "UI components not initialized. Check FXML file.");
            return;
        }

        String email = LoginEmail.getText().trim();
        String password = LoginPassword.getText();
        String roleValue = myComboBox.getValue();

        if (email.isEmpty() || password.isEmpty() || roleValue == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill in all fields.");
            return;
        }

        try {
            Role role = Role.valueOf(roleValue);
            User user = null;

            // Hardcoded admin login for testing (REMOVE IN PRODUCTION)
            if (email.equals("admin@gmail.com") && password.equals("1234") && role == Role.ADMIN) {
                user = new User();
                user.setName("Admin");
                user.setRole(Role.ADMIN);
                user.setAccountNumber("ADMIN123"); // Test account number for admin
            } else {
                user = UserDAO.Login(email, password);
            }

            if (user != null && user.getRole() == role) {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getName() + "!");
                Session.setCurrentUser(user);

                // Integrate with WalletService
                String accountNumber = user.getAccountNumber();
                double balance = walletService.getBalance(accountNumber);
                System.out.println("Account " + accountNumber + " balance: " + balance);

                // Navigate to Markets screen
                navigateToMarkets(event);
            } else {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email, password, or role.");
            }
        } catch (IllegalArgumentException e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Form Error", "Invalid role selected.");
        }
    }


    @FXML
    void switchToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Signup.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load signup page.");
        }
    }

    private void navigateToMarkets(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Markets.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 414.0, 383.0);
            stage.setTitle("Market");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load market page.");
        }
    }
}