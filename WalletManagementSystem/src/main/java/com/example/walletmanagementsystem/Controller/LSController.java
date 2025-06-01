package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;



public class LSController {
    @FXML public AnchorPane loginform;
    @FXML public AnchorPane signupform;
    @FXML public Hyperlink Login;
    @FXML public Hyperlink Signup;
    @FXML public TextArea LoginPassword;
    @FXML public TextArea LoginEmail;
    @FXML public Button LoginData;


    public void switchFrom(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource()==Login){
            loginform.setVisible(true);
            signupform.setVisible(false);
        }
        else{
            loginform.setVisible(false);
            signupform.setVisible(true);
        }
    }

    @FXML
    public void handleLogin(){
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();
        if (email.isEmpty()&&password.isEmpty()){
            AlertUtil.showWarning("Validation Error ","PLease enter your email and passwword");
        return;
        }

        User user = UserDAO.Login(email,password);
        if (user!=null){
           AlertUtil.showInfo("Login Success","Welcome"+user.getName());
           //Navigate to dashboard
        }
        else {
            AlertUtil.showError("Login Failed", "Invalid email or password.");
        }

    }

    @FXML
    public void handleSignup(){

    }

}
