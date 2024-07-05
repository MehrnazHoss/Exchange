package com.example.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class USD extends Cryptocurrencies{
    public USD(float rateOfCryptocurrency, float percentageOfChanges, float maxRateOfCryptocurrency, float minRateOfCryptocurrency) {
        super("USD", rateOfCryptocurrency, percentageOfChanges, maxRateOfCryptocurrency, minRateOfCryptocurrency);
    }

    @FXML
    Label ratesOfUSDbutton;
    @Override
    public void rates(){

    }

}
