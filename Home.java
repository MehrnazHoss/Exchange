package com.example.final_project;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Home extends Application {

    private HomeController homeController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("homePage.fxml"));
        Parent root = fxmlLoader.load();
        homeController = fxmlLoader.getController();

        Scene scene = new Scene(root, 1550, 800);
        stage.setScene(scene);
        stage.show();
        setDateTimeForLabelOfTime();
    }

    public void setDateTimeForLabelOfTime(){
        LocalDate startDate = LocalDate.of(2024, 6, 5);
        LocalDate endDate = LocalDate.of(2024, 6, 6);
        LocalDateTime startDateTime = LocalDateTime.of(2024, 6, 5, 0, 34, 36); // تاریخ و زمان شروع
        LocalDateTime endDateTime = LocalDateTime.of(2024, 6, 6, 0, 35, 36); // تاریخ و زمان پایان


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            //LocalDate currentDate = LocalDate.now();
            //*********** error
            Duration durationTime = Duration.between(startDate.atTime(0,34,36), endDate.atTime(0,34,36));
            double hoursTime = durationTime.toHours() % 24;
            double minutesTime = durationTime.toMinutes() % 60;
            double secondsTime = durationTime.toSeconds() % 60;

            String formattedDurationTime = String.format("%02d:%02d:%02d", hoursTime, minutesTime, secondsTime);
            homeController.labelOfTime.setText(formattedDurationTime);

            homeController.labelOfDate.setText("      "+"2024/06/05");
            if (hoursTime == 0 && (minutesTime > 0 && minutesTime < 35) && secondsTime == 36){
                homeController.labelOfDate.setText("      "+"2024/06/06");
            }
            homeController.labelOfDate.setFont(new Font("Aparajita",23));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
