package com.example.final_project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WalletPage extends Thread implements Initializable {
    @FXML
    public Label tomanProperty;
    @FXML
    public Label eurProperty;
    @FXML
    public Label yenProperty;
    @FXML
    public Label gbpProperty;
    @FXML
    public Label usdProperty;
    @FXML
    public Label tomanTOusd;
    @FXML
    public Label eurTOusd;
    @FXML
    public Label yenTOusd;
    @FXML
    public Label gbpTOusd;
    @FXML
    public Label usdTOusd;
    @FXML
    public Label totalAssetsInUSD;
    @FXML
    public Label walletID;
    @FXML
    public AreaChart<Number, Number> annualAssetChart;
    public static double total;
    public DecimalFormat f=new DecimalFormat("#.##");
    @FXML
    public Button backButton=new Button();
    @FXML
    public Button exitButton=new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #b5103c;-fx-background-radius: 10;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color:  #c91444;-fx-background-radius: 10;"));
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color:  #7532f0;-fx-background-radius: 10;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color:    #7f40f5;-fx-background-radius: 10;"));
        walletID.setText(Repository.userList[Repository.currentUser].id);
        Timer timer = new Timer();
        int intervalInSeconds = 10;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    update();
                });
            }
        }, 0, intervalInSeconds * 1000);
    }
    public void update(){
        tomanProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.TOMAN));
        tomanTOusd.setText(String.valueOf(f.format(Repository.userList[Repository.currentUser].userWallet.TOMAN*Repository.USD[0]/Repository.TOMAN[0])));

        eurProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.EUR));
        eurTOusd.setText(String.valueOf(f.format(Repository.userList[Repository.currentUser].userWallet.EUR*Repository.USD[0]/Repository.EUR[0])));

        yenProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.YEN));
        yenTOusd.setText(String.valueOf(f.format(Repository.userList[Repository.currentUser].userWallet.YEN*Repository.USD[0]/Repository.YEN[0])));

        gbpProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.GBP));
        gbpTOusd.setText(String.valueOf(f.format(Repository.userList[Repository.currentUser].userWallet.GBP*Repository.USD[0]/Repository.GBP[0])));

        usdProperty.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.USD));
        usdTOusd.setText(String.valueOf(Repository.userList[Repository.currentUser].userWallet.USD));

        total = Double.parseDouble(f.format(Repository.userList[Repository.currentUser].userWallet.EUR*Repository.USD[0]/Repository.EUR[0]))+
                Double.parseDouble(f.format(Repository.userList[Repository.currentUser].userWallet.TOMAN*Repository.USD[0]/Repository.TOMAN[0]))+
                Double.parseDouble(f.format(Repository.userList[Repository.currentUser].userWallet.YEN*Repository.USD[0]/Repository.YEN[0])) +
                Double.parseDouble(f.format(Repository.userList[Repository.currentUser].userWallet.GBP*Repository.USD[0]/Repository.GBP[0]))+
                Double.parseDouble(f.format(Repository.userList[Repository.currentUser].userWallet.USD));

        totalAssetsInUSD.setText(f.format(total));

        XYChart.Series<Number, Number> dataSeries = annualAssetChart.getData().getFirst(); // Get the series
        dataSeries.getData().clear(); // Clear existing data

        for (double j = 0; j < 1; j+= (double) 1 /29030400) {
            dataSeries.getData().add(new XYChart.Data<>(j, total));
            System.out.println(j);
            System.out.print(total);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void exit(ActionEvent e){
        System.exit(0);
    }
    @FXML
    public void back(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProfilePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1550, 800);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }
}
