package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.Main;
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
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
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

    @FXML private LineChart<String,Number> ethChart;
    @FXML private CategoryAxis ethXAxis;
    @FXML private NumberAxis ethYAxis;

    @FXML private LineChart<String,Number> XRPChart;
    @FXML private CategoryAxis XRPXAxis;
    @FXML private NumberAxis XRPYAxis;


    @FXML private VBox walletBalance;
    @FXML private VBox walletCoins;



    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = Session.getUserId();
//        showBalance(userId);
//        showCoins(userId);

        startGraph();
    }

    private void startGraph() {
        List<String> coins = List.of("BTC", "ETH", "XRP");
        executor.scheduleAtFixedRate(() -> {
            Map<String, XYChart.Series<String, Number>> dataMap = ChartService.getLiveSeries(coins);

            Platform.runLater(() -> {
                btcChart.getData().setAll(dataMap.get("BTC"));
                ethChart.getData().setAll(dataMap.get("ETH"));
                XRPChart.getData().setAll(dataMap.get("XRP"));
            });

        }, 0, 10, TimeUnit.SECONDS);

    }

    /*Don't delete it is for testing purpose */

//    public void showBalance(int userId){
//        walletBalance.getChildren().clear();
//        Wallet wallet = WalletDAO.getWalletById(userId);
//        if (wallet!=null){
//            Label balanceLabel = new Label("Balance: $" + String.format("%.2f", wallet.getBalance()));
//            balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
//            walletBalance.getChildren().add(balanceLabel);
//        }
//    }
//
//    public void showCoins(int userId) {
//        Portfolio portfolio = PortfolioDAO.getPortfolioByUserId(userId);
//        if (portfolio != null) {
//            HashMap<String, Asset> assets = portfolio.getPortfolio();
//            walletCoins.getChildren().clear();
//
//            for (Map.Entry<String, Asset> entry : assets.entrySet()) {
//                String symbol = entry.getKey();
//                Asset asset = entry.getValue();
//
//                Label coinLabel = new Label(symbol + " - Qty: " + asset.getQuantity());
//                coinLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
//                walletCoins.getChildren().add(coinLabel);
//            }
//        }
//    }

    @FXML
    protected void onClickMarketButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/walletmanagementsystem/Controller/Markets.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene((Parent)fxmlLoader.load(), (double)414.0F, (double)383.0F);
        stage.setTitle("Markets!");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
