package com.exppoints.fantasy;

import com.exppoints.fantasy.gui.MenuBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MenuBuilder.buildMainMenu(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}