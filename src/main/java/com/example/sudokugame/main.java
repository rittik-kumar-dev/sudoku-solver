package com.example.sudokugame;



import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) {

        SudokuUI ui = new SudokuUI(primaryStage);
        ui.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}