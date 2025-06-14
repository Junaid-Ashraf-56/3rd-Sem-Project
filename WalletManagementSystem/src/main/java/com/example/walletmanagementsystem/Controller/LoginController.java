package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;
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

import java.io.IOException;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;

public class LoginController {

    @FXML private AnchorPane loginform;
    @FXML private TextField LoginEmail;
    @FXML private PasswordField LoginPassword;
    @FXML private ComboBox<String> myComboBox;
    @FXML private Button LoginData;
    @FXML private Hyperlink Signup;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();
        Role role = Role.valueOf(myComboBox.getValue());

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }
        User user = UserDAO.Login(email,password);

        if (email.equals("admin@gmail.com") && password.equals("1234") && role==Role.ADMIN) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Admin!");
            Session.setCurrentUser(user);

        }else if (user!=null){
            AlertUtil.showInfo("Login Success","Welcome "+user.getName());
            Session.setCurrentUser(user);
        }
        else {
            AlertUtil.showError("Login Failed", "Invalid email or password.");
        }
    }


    @FXML
    void switchToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/signup.fxml"));
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
