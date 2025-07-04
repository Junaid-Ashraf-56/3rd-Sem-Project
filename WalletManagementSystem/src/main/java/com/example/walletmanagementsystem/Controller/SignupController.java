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
import java.sql.SQLException;
import java.util.Objects;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;
import static com.example.walletmanagementsystem.utils.ValidationUtil.isStrongPassword;
import static com.example.walletmanagementsystem.utils.ValidationUtil.isValidEmail;


public class SignupController {
    @FXML private AnchorPane loginform;
    @FXML private AnchorPane signupform;
    @FXML private Hyperlink loginLink;
    @FXML private Hyperlink Signup;
    @FXML private TextField SignupEmail;
    @FXML private PasswordField SignupPassword;
    @FXML private TextField UserName;

    @FXML
    public void switchToLogin(ActionEvent actionEvent) {
        try {
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/walletmanagementsystem/Controller/login.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignup() throws SQLException {
        String userName = UserName.getText();
        String email = SignupEmail.getText();
        String password = SignupPassword.getText();
        final Role role = Role.USER;

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }

        if (!isValidEmail(email) || !isStrongPassword(password)) {
            AlertUtil.showWarning("Wrong email or weak Password", "Please change it");
            return;
        }

        User existingUser = UserDAO.getUserId(email);
        if (existingUser != null) {
            AlertUtil.showError("Signup Failed", "User already exists.");
            return;
        }

        User user = UserDAO.addUser(userName, email, password, role);
        if (user != null) {
            AlertUtil.showInfo("Sign up Success", "Welcome " + user.getName());
        } else {
            AlertUtil.showError("Signup Failed", "Could not register user. Try again.");
        }
    }


    @FXML
    protected void onClickSignUpButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Markets.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Market!");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
