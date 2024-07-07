package com.example.final_project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class USD extends Thread implements Initializable {

    @FXML
    Button button60S;
    @FXML
    Button button1H;
    @FXML
    Button button24H;
    @FXML
    Button button7D;
    @FXML
    Button button1M;
    @FXML
    Button button1Y;
    @FXML
    Label Date;
    @FXML
    Label Time;
    @FXML
    Label CurrentPrice;
    @FXML
    Label StockTurnover;
    @FXML
    Label percentageOfDailyChanges;
    @FXML
    public AreaChart<Number, Number> AreaChartOf60S;
    @FXML
    public AreaChart<Number, Number> AreaChartOf1H;
    @FXML
    public AreaChart<Number, Number> AreaChartOf24H;
    @FXML
    public AreaChart<Number, Number> AreaChartOf7D;
    @FXML
    public AreaChart<Number, Number> AreaChartOf1M;
    @FXML
    public AreaChart<Number, Number> AreaChartOf1Y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now1 = LocalDate.now();
        LocalTime now2 = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        Time.setText(dtf.format(now2));
        Date.setText(date.format(now1));

        button60S.setOnMouseEntered(e -> button60S.setStyle("-fx-background-color: #2faab5;-fx-background-radius: 50;"));
        button60S.setOnMouseExited(e -> button60S.setStyle("-fx-background-color: #2671b5;-fx-background-radius: 50;"));
        button60S.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf60S.setVisible(true);
                AreaChartOf60S.setAnimated(false);
                AreaChartOf60S.getXAxis().setLabel("Time (Seconds)");
                AreaChartOf60S.getYAxis().setLabel("USD Price");
                AreaChartOf60S.getData().add(new XYChart.Series<>());

                fetchAndUpdateData60S();
            }
        });

    }
    private void fetchAndUpdateData60S() {
        Timer timer = new Timer();
        int intervalInSeconds = 1;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        LocalDate now1 = LocalDate.now();
                        LocalTime now2 = LocalTime.now();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                        Time.setText(dtf.format(now2));
                        Date.setText(date.format(now1));

                        fetchAndAddData60S();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData60S() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, USD FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf60S.getData().get(0); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            int i = 0;
            while (resultSet.next()) {
                // Convert the USD value to a double
                double price = resultSet.getDouble("USD");
                CurrentPrice.setText(resultSet.getString("USD"));
                // Add data to the chart
                dataSeries.getData().add(new XYChart.Data<>(i++, price));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
