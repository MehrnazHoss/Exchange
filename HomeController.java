package com.example.final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button homeButton;
    @FXML
    private void onHomeButtonClicked(ActionEvent e) throws IOException {
        System.out.println("hi");
    }
}
