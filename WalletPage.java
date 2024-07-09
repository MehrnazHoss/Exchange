package com.example.final_project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WalletPage extends Thread implements Initializable {
    @FXML
    static Label tomanProperty;
    @FXML
    static Label eurProperty;
    @FXML
    static Label yenProperty;
    @FXML
    static Label gbpProperty;
    @FXML
    static Label usdProperty;
    @FXML
    static Label tomanTOusd;
    @FXML
    static Label eurTOusd;
    @FXML
    static Label yenTOusd;
    @FXML
    static Label gbpTOusd;
    @FXML
    static Label usdTOusd;
    @FXML
    static Label totalAssetsInUSD;
    @FXML
    static Label walletID;
    @FXML
    public AreaChart<Number, Number> annualAssetChart;

    public static double total;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Timer timer = new Timer();
        int intervalInSeconds = 60;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    walletID.setText(Repository.userList[Repository.currentUser].id);
                    update();
                });
            }
        }, 0, intervalInSeconds * 1000);
    }
    public static void update(){
        tomanProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.TOMAN));
        tomanTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.TOMAN*Repository.USD[0]/Repository.TOMAN[0]));

        eurProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.EUR));
        eurTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.EUR*Repository.USD[0]/Repository.EUR[0]));

        yenProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.YEN));
        yenTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.YEN*Repository.USD[0]/Repository.YEN[0]));

        gbpProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.GBP));
        gbpTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.GBP*Repository.USD[0]/Repository.GBP[0]));

        usdProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.USD));
        usdTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.USD));

        total = Repository.userList[Repository.currentUser].userWallet.EUR*Repository.USD[0]/Repository.EUR[0] +
                Repository.userList[Repository.currentUser].userWallet.TOMAN*Repository.USD[0]/Repository.TOMAN[0] +
                Repository.userList[Repository.currentUser].userWallet.YEN*Repository.USD[0]/Repository.YEN[0] +
                Repository.userList[Repository.currentUser].userWallet.GBP*Repository.USD[0]/Repository.GBP[0] +
                Repository.userList[Repository.currentUser].userWallet.USD;

        totalAssetsInUSD.setText(String.valueOf(total));
    }
}
