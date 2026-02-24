package com.exppoints.fantasy.gui;

import com.exppoints.fantasy.daterbase.Database;
import com.exppoints.fantasy.player.FuturePlayer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Popups{


    public static int reScrapePrompt(FuturePlayer player) {
        Stage stage = new Stage();
        
        int[] returnCode = {0};

        StackPane root = new StackPane();
        Label label = new Label("Would you like to rescrape data for '%s'? last scrape for '%s' was on '%s'".formatted(player.getName(), player.getName(), Database.getDate(player)));
        Button yes = new Button("Yes");
        Button no = new Button("No");
        root.getChildren().addAll(label, yes, no);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(yes, Pos.CENTER_LEFT);
        StackPane.setAlignment(no, Pos.CENTER_RIGHT);

        

        Scene scene = new Scene(root, 400, 200);
        
        yes.setOnAction(event -> {
            stage.close();
            returnCode[0] = 1;
        });
        no.setOnAction(event -> {
            stage.close();
            returnCode[0] = -1;
        }); 

        stage.setScene(scene);
        stage.setTitle("Rescrape Popup");
        stage.showAndWait();

        return returnCode[0];
    }
}