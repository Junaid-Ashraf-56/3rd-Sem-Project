package com.example.walletmanagementsystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;



public class LSController {
    @FXML
    private AnchorPane loginform;

    @FXML
    private AnchorPane signupform;

    @FXML
    private Hyperlink Login;

    @FXML
    private Hyperlink Signup;

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
}
