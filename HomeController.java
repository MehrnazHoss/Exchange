package com.example.final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeController {
    @FXML
    public Button homeButton;
    public Label labelOfTime, labelOfDate;
    @FXML
    private void onHomeButtonClicked(ActionEvent e) throws IOException {
        System.out.println("hi");
    }
}
