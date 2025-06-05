package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;



public class SignupController {
    @FXML public AnchorPane loginform;
    @FXML public AnchorPane signupform;
    @FXML public Hyperlink Login;
    @FXML public Hyperlink Signup;
    @FXML public TextArea LoginPassword;
    @FXML public TextArea LoginEmail;
    @FXML public Button LoginData;

    @FXML
    public void switchFrom(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource()==Login){
            loginform.setVisible(true);
            signupform.setVisible(false);
        }
        else{
            signupform.setVisible(true);
            loginform.setVisible(false);
        }
    }

    @FXML
    public void handleSignup(){

    }

}
