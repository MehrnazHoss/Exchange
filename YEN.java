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


public class YEN extends Thread implements Initializable {

    @FXML
    Button button60S;
    @FXML
    Button swapButton;
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
        Timer timer = new Timer();
        int intervalInSeconds = 1;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalDate now1 = LocalDate.now();
                    LocalTime now2 = LocalTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                    Time.setText(dtf.format(now2));
                    Date.setText(date.format(now1));

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url1 = "jdbc:mysql://127.0.0.1:3306";
                        Connection connection = DriverManager.getConnection(url1, "root", "1234");
                        Statement statement = connection.createStatement();
                        statement.execute("use cryptocurrency");

                        ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

                        LocalTime now = LocalTime.now();
                        String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                        while (resultSet.next()) {
                            if (currentTime.equals(resultSet.getTime("Time").toString())) {
                                CurrentPrice.setText(resultSet.getString("YEN"));
                                sleep(1000);
                            }
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalDate now1 = LocalDate.now();
                    LocalTime now2 = LocalTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                    Time.setText(dtf.format(now2));
                    Date.setText(date.format(now1));

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url1 = "jdbc:mysql://127.0.0.1:3306";
                        Connection connection = DriverManager.getConnection(url1, "root", "1234");
                        Statement statement = connection.createStatement();
                        statement.execute("use cryptocurrency");

                        ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

                        LocalTime now = LocalTime.now();
                        String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                        LocalTime newTime = now.plusHours(1);
                        String formattedNewTime = newTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                        double[] prices = new double[24];
                        int i = 0;
                        while (resultSet.next()) {
                            double price = 0;
                            if (currentTime.equals(resultSet.getTime("Time").toString())) {
                                while (!formattedNewTime.equals(resultSet.getTime("Time").toString())) {
                                    // Convert the USD value to a double
                                    price += resultSet.getDouble("YEN");
                                    sleep(60000);
                                    resultSet.next();
                                }
                                price /= 60;
                                prices[i] = price;
                                if (prices[i-1] > 0){
                                    percentageOfDailyChanges.setText((prices[i] - prices[i - 1]) * 100 / prices[i - 1] + "%");
                                }
                                i++;
                            }
                            else{
                                percentageOfDailyChanges.setText("0%");
                            }
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);



        button60S.setOnMouseEntered(e -> button60S.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button60S.setOnMouseExited(e -> button60S.setStyle("-fx-background-color:  #ff0099;-fx-background-radius: 50;"));
        button1H.setOnMouseEntered(e -> button1H.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button1H.setOnMouseExited(e -> button1H.setStyle("-fx-background-color:   #ff0099;-fx-background-radius: 50;"));
        button24H.setOnMouseEntered(e -> button24H.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button24H.setOnMouseExited(e -> button24H.setStyle("-fx-background-color:   #ff0099;-fx-background-radius: 50;"));
        button7D.setOnMouseEntered(e -> button7D.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button7D.setOnMouseExited(e -> button7D.setStyle("-fx-background-color:   #ff0099;-fx-background-radius: 50;"));
        button1M.setOnMouseEntered(e -> button1M.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button1M.setOnMouseExited(e -> button1M.setStyle("-fx-background-color:   #ff0099;-fx-background-radius: 50;"));
        button1Y.setOnMouseEntered(e -> button1Y.setStyle("-fx-background-color: #b502a9;-fx-background-radius: 50;"));
        button1Y.setOnMouseExited(e -> button1Y.setStyle("-fx-background-color:   #ff0099;-fx-background-radius: 50;"));

        button60S.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf60S.setVisible(true);
                AreaChartOf1H.setVisible(false);
                AreaChartOf24H.setVisible(false);
                AreaChartOf7D.setVisible(false);
                AreaChartOf1M.setVisible(false);
                AreaChartOf1Y.setVisible(false);

                AreaChartOf60S.setAnimated(false);
                AreaChartOf60S.getXAxis().setLabel("Time (Seconds)");
                AreaChartOf60S.getYAxis().setLabel("YEN Price");
                AreaChartOf60S.getData().add(new XYChart.Series<>());

                fetchAndUpdateData60S();
            }
        });
        button1H.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf1H.setVisible(true);
                AreaChartOf60S.setVisible(false);
                AreaChartOf24H.setVisible(false);
                AreaChartOf7D.setVisible(false);
                AreaChartOf1M.setVisible(false);
                AreaChartOf1Y.setVisible(false);

                AreaChartOf1H.setAnimated(false);
                AreaChartOf1H.getXAxis().setLabel("Time (Minutes)");
                AreaChartOf1H.getYAxis().setLabel("YEN Price");
                AreaChartOf1H.getData().add(new XYChart.Series<>());

                fetchAndUpdateData1H();
            }
        });
        button24H.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf1H.setVisible(false);
                AreaChartOf60S.setVisible(false);
                AreaChartOf24H.setVisible(true);
                AreaChartOf7D.setVisible(false);
                AreaChartOf1M.setVisible(false);
                AreaChartOf1Y.setVisible(false);

                AreaChartOf24H.setAnimated(false);
                AreaChartOf24H.getXAxis().setLabel("Time (Hours)");
                AreaChartOf24H.getYAxis().setLabel("YEN Price");
                AreaChartOf24H.getData().add(new XYChart.Series<>());

                fetchAndUpdateData24H();
            }
        });
        button7D.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf1H.setVisible(false);
                AreaChartOf60S.setVisible(false);
                AreaChartOf24H.setVisible(false);
                AreaChartOf7D.setVisible(true);
                AreaChartOf1M.setVisible(false);
                AreaChartOf1Y.setVisible(false);

                AreaChartOf7D.setAnimated(false);
                AreaChartOf7D.getXAxis().setLabel("Time (Days)");
                AreaChartOf7D.getYAxis().setLabel("YEN Price");
                AreaChartOf7D.getData().add(new XYChart.Series<>());

                fetchAndUpdateData7D();
            }
        });
        button1M.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf1H.setVisible(false);
                AreaChartOf60S.setVisible(false);
                AreaChartOf24H.setVisible(false);
                AreaChartOf7D.setVisible(false);
                AreaChartOf1M.setVisible(true);
                AreaChartOf1Y.setVisible(false);

                AreaChartOf1M.setAnimated(false);
                AreaChartOf1M.getXAxis().setLabel("Time (Weeks)");
                AreaChartOf1M.getYAxis().setLabel("YEN Price");
                AreaChartOf1M.getData().add(new XYChart.Series<>());

                fetchAndUpdateData1M();
            }
        });
        button1Y.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AreaChartOf1H.setVisible(false);
                AreaChartOf60S.setVisible(false);
                AreaChartOf24H.setVisible(false);
                AreaChartOf7D.setVisible(false);
                AreaChartOf1M.setVisible(false);
                AreaChartOf1Y.setVisible(true);

                AreaChartOf1Y.setAnimated(false);
                AreaChartOf1Y.getXAxis().setLabel("Time (Months)");
                AreaChartOf1Y.getYAxis().setLabel("YEN Price");
                AreaChartOf1Y.getData().add(new XYChart.Series<>());

                fetchAndUpdateData1Y();
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

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf60S.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (int j = 0; j < 60; j++) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchAndUpdateData1H() {
        Timer timer = new Timer();
        int intervalInSeconds = 1;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        fetchAndAddData1H();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData1H() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf1H.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

//            boolean sw = false;
//            while (resultSet.next()){
//                if (now.format(dtf).equals(resultSet.getTime("Time").toString())) {
////                    System.out.println(resultSet.getDouble("USD "));
////                    System.out.print(i++);
//                    dataSeries.getData().add(new XYChart.Data<>(i++, resultSet.getDouble("USD")));
//                    sw = true;
//                }
//                if (sw == true){
//                    sleep(60000);
//                    if (i == 60){
//                        i = 0;
//                    }
//                    sw = false;
//                }
//            }

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (double j = 0; j < 1; j+= (double) 1 /60) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchAndUpdateData24H() {
        Timer timer = new Timer();
        int intervalInSeconds = 1;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        fetchAndAddData24H();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData24H() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf24H.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (double j = 0; j < 1; j+= (double) 1 /3600) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchAndUpdateData7D() {
        Timer timer = new Timer();
        int intervalInSeconds = 24*3600;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        fetchAndAddData7D();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData7D() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf7D.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (double j = 0; j < 1; j+= (double) 1 /86400) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchAndUpdateData1M() {
        Timer timer = new Timer();
        int intervalInSeconds = 1;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        fetchAndAddData1M();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData1M() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf1M.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (double j = 0; j < 1; j+= (double) 1 /2419200) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchAndUpdateData1Y() {
        Timer timer = new Timer();
        int intervalInSeconds = 1;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        fetchAndAddData1Y();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, intervalInSeconds * 1000);
    }

    private void fetchAndAddData1Y() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url1 = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url1, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");

            ResultSet resultSet = statement.executeQuery("SELECT Time, YEN FROM book1");

            // Update the chart with data
            XYChart.Series<Number, Number> dataSeries = AreaChartOf1Y.getData().getFirst(); // Get the series
            dataSeries.getData().clear(); // Clear existing data

            LocalTime now = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:36");

            while (resultSet.next()) {
                // Add data to the chart
                double price = resultSet.getDouble("YEN");
                if (now.format(dtf).equals(resultSet.getTime("TIME").toString())) {
                    for (double j = 0; j < 1; j+= (double) 1 /29030400) {
                        dataSeries.getData().add(new XYChart.Data<>(j, resultSet.getDouble("YEN")));
//                        System.out.println(price);
//                        System.out.print(j);
                        sleep(1000);
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
