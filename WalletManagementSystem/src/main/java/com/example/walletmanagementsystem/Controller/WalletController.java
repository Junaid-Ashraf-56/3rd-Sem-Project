package com.example.walletmanagementsystem.Controller;


import com.example.walletmanagementsystem.dao.PortfolioDAO;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.Portfolio;
import com.example.walletmanagementsystem.model.Wallet;
import com.example.walletmanagementsystem.service.ChartService;
import com.example.walletmanagementsystem.utils.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WalletController implements Initializable {

    @FXML private LineChart<String, Number> btcChart;
    @FXML private CategoryAxis btcXAxis;
    @FXML private NumberAxis btcYAxis;

    @FXML private LineChart<String, Number> ethChart;
    @FXML private CategoryAxis ethXAxis;
    @FXML private NumberAxis ethYAxis;

    @FXML private LineChart<String, Number> XRPChart;
    @FXML private CategoryAxis XRPXAxis;
    @FXML private NumberAxis XRPYAxis;

    @FXML private Button walletbutton;

    @FXML private Label walletBalance;
    @FXML private VBox walletCoins;

    @FXML private Label BTClabel;
    @FXML private Label ETHlabel;
    @FXML private Label XRPlabel;

    @FXML private Hyperlink UserName;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> walletbutton.requestFocus());
        Platform.runLater(() -> {
            walletbutton.requestFocus();
            UserName.setText(Session.getUsername()); // Will show username from logged-in user
        });
        String accountNumber = Session.getCurrentUser().getAccountNumber();

                showBalance(accountNumber);
                showCoins(accountNumber);

        startGraph();
    }

    private void startGraph() {
        List<String> coins = List.of("bitcoin", "ethereum", "ripple");
        executor.scheduleAtFixedRate(() -> {
            Map<String, XYChart.Series<String, Number>> dataMap = ChartService.getLiveSeries(coins);
            double btcPrice = ChartService.getLivePrice("bitcoin");
            double ethPrice = ChartService.getLivePrice("ethereum");
            double xrpPrice = ChartService.getLivePrice("ripple");

            Platform.runLater(() -> {
                btcChart.getData().setAll(dataMap.get("bitcoin"));
                ethChart.getData().setAll(dataMap.get("ethereum"));
                XRPChart.getData().setAll(dataMap.get("ripple"));

                BTClabel.setText("$ " + String.format("%.2f", btcPrice));
                ETHlabel.setText("$ " + String.format("%.2f", ethPrice));
                XRPlabel.setText("$ " + String.format("%.4f", xrpPrice));
            });

        }, 0, 30, TimeUnit.SECONDS);
    }

    /*Don't delete it is for testing purpose */

    public void showBalance(String accountNumber) {
        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        if (wallet != null) {
            walletBalance.setText("Balance: $ " + String.format("%.2f", wallet.getBalance()));
        } else {
            walletBalance.setText("$ 0.0");
        }
    }

    public void showCoins(String accountNumber) {
        Portfolio portfolio = PortfolioDAO.getPortfolioByAccountNumber(accountNumber);
        if (portfolio != null) {
            HashMap<String, Asset> assets = portfolio.getPortfolio();
            walletCoins.getChildren().clear();

            for (Map.Entry<String, Asset> entry : assets.entrySet()) {
                String symbol = entry.getKey();
                Asset asset = entry.getValue();

                Label coinLabel = new Label(symbol + " - Qty: " + asset.getQuantity());
                coinLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                walletCoins.getChildren().add(coinLabel);
            }
        }
    }

    @FXML
    public void onUsernameClick(ActionEvent event) {
        System.out.println("User profile or settings screen can go here");
    }


    @FXML
    protected void onClickMarketButton(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/walletmanagementsystem/Controller/Markets.fxml", "Markets");
    }

    @FXML
    protected void onClickPortfolioButton(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/walletmanagementsystem/Controller/Portfolio.fxml", "Portfolio");
    }

    @FXML
    protected void onClickTransactionButton(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/walletmanagementsystem/Controller/Transactions.fxml", "Transactions");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
