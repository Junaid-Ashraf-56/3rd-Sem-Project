package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.Main;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Wallet;
import com.example.walletmanagementsystem.service.WalletService;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.walletmanagementsystem.utils.AlertUtil.showAlert;


public class TransactionsController implements Initializable {

    @FXML private Button transactionButton;
    @FXML private Button walletbutton;
    @FXML private Button marketbutton;
    @FXML private Button transactionbutton;
    @FXML private Label userNameLabel;
    @FXML private Label accountNumberLabel;
    @FXML private Label balanceLabel;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;
    @FXML private Button sendButton;
    @FXML private TextField amountField;
    @FXML private TextField receiverAccountField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            walletbutton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

            marketbutton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
            userNameLabel.setText(Session.getCurrentUser().getName());
            accountNumberLabel.setText(Session.getCurrentUser().getAccountNumber());
        });
        String accountNumber = Session.getCurrentUser().getAccountNumber();
        showBalance(accountNumber);
    }


    @FXML
    private void handleDeposit(ActionEvent event) {
        String amountText = amountField.getText();
        try {
            double amount = Double.parseDouble(amountText);
            String accountNumber = Session.getCurrentUser().getAccountNumber();
            boolean success = WalletService.addFunds(accountNumber, amount);
            System.out.println("Deposit status: " + success);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Funds deposited successfully.");
                showBalance(accountNumber);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to deposit funds.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Enter a valid number.");
        }
    }


    @FXML
    private void handleWithdraw(ActionEvent event) {
        String amountText = amountField.getText();
        try {
            double amount = Double.parseDouble(amountText);
            String accountNumber = Session.getCurrentUser().getAccountNumber();
            boolean success = WalletService.withdrawFunds(accountNumber, amount);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Funds withdrawn successfully.");
                showBalance(accountNumber);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient balance or failed.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Enter a valid number.");
        }
    }


    @FXML
    private void handleTransfer(ActionEvent event) {
        String amountText = amountField.getText();
        String receiverAccount = receiverAccountField.getText().trim();
        try {
            double amount = Double.parseDouble(amountText);
            String senderAccount = Session.getCurrentUser().getAccountNumber().trim();
            boolean success = WalletService.transferFunds(senderAccount, receiverAccount, amount);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Funds transferred successfully.");
                showBalance(senderAccount);
            }
//            else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Transfer failed.\nSender: " + senderAccount + "\nReceiver: " + receiverAccount + "\nAmount: " + amount);
//
//            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Enter a valid number.");
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
    public void onUsernameClick(ActionEvent event) {
        System.out.println("User profile or settings screen can go here");
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

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickMarketButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/walletmanagementsystem/Controller/Markets.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Markets");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickPortfolioButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/walletmanagementsystem/Controller/Portfolio.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Portfolio");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onClickTransactionButton(ActionEvent event) {
        // Optional: Do nothing or show a message
        System.out.println("Already in Transactions section.");
    }
}
