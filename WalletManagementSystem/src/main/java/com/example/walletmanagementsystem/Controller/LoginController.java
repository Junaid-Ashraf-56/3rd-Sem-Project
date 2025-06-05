package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;

public class LoginController {

    @FXML
    public AnchorPane loginform;

    @FXML
    public TextField LoginEmail;

    @FXML
    public PasswordField LoginPassword;

    @FXML
    public ComboBox<String> myComboBox;

    @FXML
    public Button LoginData;

    @FXML
    public Hyperlink Signup;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();
        String role = myComboBox.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }
        User user = UserDAO.Login(email,password);

        if (email.equals("admin@example.com") && password.equals("1234") && role.equals("Admin")) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Admin!");

        }else if (user!=null){
            AlertUtil.showInfo("Login Success","Welcome"+user.getName());
        }
        else {
            AlertUtil.showError("Login Failed", "Invalid email or password.");
        }
    }


    @FXML
    void switchToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/login.fxml"));
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
