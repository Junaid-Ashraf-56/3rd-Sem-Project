package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;


public class SignupController {
    @FXML private AnchorPane loginform;
    @FXML private AnchorPane signupform;
    @FXML private Hyperlink loginLink;
    @FXML private Hyperlink Signup;
    @FXML private TextField SignupEmail;
    @FXML private PasswordField SignupPassword;
    @FXML private TextField UserName;
    @FXML private Button Sigup;

    @FXML
    public void switchToLogin(ActionEvent actionEvent) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/example/walletmanagementsystem/Controller/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignup(){
        String userName = UserName.getText();
        String email = SignupEmail.getText();
        String password = SignupPassword.getText();
        final Role role = Role.valueOf("USER");

        if (userName.isEmpty()||email.isEmpty()||password.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }
        User userExist = UserDAO.getUserId(email);
        if (userExist!=null){
            AlertUtil.showError("Signup Failed", "User already Exist.");
        }
        else {
            User user = UserDAO.addUser(userName,email,password,role);
            if (user!=null) {
                AlertUtil.showInfo("Sign up Success", "Welcome " + user.getName());
            }
            else {
                AlertUtil.showError("Signup Failed", "Could not register user. Try again.");
            }
        }
    }

}
