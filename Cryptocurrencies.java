package com.example.final_project;
import java.sql.*;

public class Cryptocurrencies {
    public float rateOfCryptocurrency;
    public float percentageOfChanges;
    public float maxRateOfCryptocurrency;
    public float minRateOfCryptocurrency;

    public Cryptocurrencies(String nameOfCryptocurrency, float rateOfCryptocurrency, float percentageOfChanges,
                            float maxRateOfCryptocurrency, float minRateOfCryptocurrency){
        this.percentageOfChanges = percentageOfChanges;
        setNameOfCryptocurrency(nameOfCryptocurrency);
        this.rateOfCryptocurrency = rateOfCryptocurrency;
        this.maxRateOfCryptocurrency = maxRateOfCryptocurrency;
        this.minRateOfCryptocurrency = minRateOfCryptocurrency;
    }

    public void setNameOfCryptocurrency(String nameOfCryptocurrency){
        if (nameOfCryptocurrency.equals("USD") || nameOfCryptocurrency.equals("EUR") ||
        nameOfCryptocurrency.equals("TOMAN") || nameOfCryptocurrency.equals("YEN") || nameOfCryptocurrency.equals("GBP"))
            System.out.println("It's not Valid!");
    }

    public void rates(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306";
            Connection connection = DriverManager.getConnection(url, "root", "1234");
            Statement statement = connection.createStatement();
            statement.execute("use cryptocurrency");
            ResultSet resultSet = statement.executeQuery("select * from book1;");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(2));
//            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
