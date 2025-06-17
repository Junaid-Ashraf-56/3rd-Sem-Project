package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.Main;
import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Wallet;
import com.example.walletmanagementsystem.utils.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;

public class PortfolioController implements Initializable {

    @FXML private Button portfolioButton;
    @FXML private Button transactionButton;
    @FXML private Button walletbutton;
    @FXML private Button marketbutton;
    @FXML private Label userNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label accountNumberLabel;
    @FXML private Label balanceLabel;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button updatePasswordButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            walletbutton.setStyle("-fx-background-color: #f90; -fx-text-fill: white;");

            marketbutton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");


            userNameLabel.setText(Session.getCurrentUser().getName());
            emailLabel.setText(Session.getCurrentUser().getEmail());
            accountNumberLabel.setText(Session.getCurrentUser().getAccountNumber());
        });
        String accountNumber = Session.getCurrentUser().getAccountNumber();
        showBalance(accountNumber);
    }
    @FXML
    public void onClickLogoutButton(java.awt.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBalance(String accountNumber) {
        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        if (wallet != null) {
            balanceLabel.setText("Balance: $ " + String.format("%.2f", wallet.getBalance()));
        } else {
            balanceLabel.setText("$ 0.0");
        }
    }

    @FXML
    private void handleUpdatePasswordButton(ActionEvent event) {
        String currentPass = oldPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error","All fields are required.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Error","New passwords do not match.");
            return;
        }

        String accountNumber = Session.getCurrentUser().getAccountNumber();
        boolean isCorrect = UserDAO.verifyPassword(accountNumber, currentPass);

        if (!isCorrect) {
            showAlert(Alert.AlertType.ERROR, "Error","Current password is incorrect.");
            return;
        }

        boolean updated = UserDAO.updatePassword(accountNumber, newPass);
        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Error","Password updated successfully.");
            oldPasswordField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error","Failed to update password.");
        }
    }

    @FXML
    protected void onClickWalletButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Wallet.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Wallet");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        // Close current window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickMarketButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/walletmanagementsystem/Controller/Markets.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Markets");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        // Close current window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickTransactionButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Transactions.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Transactions");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        // Close current window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickPortfolioButton(ActionEvent event) {
        // Optional: Action if portfolio button is clicked again
        System.out.println("Already in Portfolio section.");
    }


}
