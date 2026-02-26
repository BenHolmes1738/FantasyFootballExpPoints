package com.exppoints.fantasy.gui;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Popups{


    public static int reScrapePrompt(String name, String time) {
        Stage stage = new Stage();
        
        int[] returnCode = {0};

        //String lastScrape = Database.getDate(player);
        Instant instant = Instant.parse(time.replace(" ", "T") + "Z");
        ZonedDateTime localLastScrape = instant.atZone(ZoneId.systemDefault());

        StackPane root = new StackPane();
        Label label = new Label("Would you like to rescrape data for %s?\nlast scrape for %s was %s".formatted(name, name, StringUtils.substring(localLastScrape.toString(), 0, 19)));
        label.setTextAlignment(TextAlignment.CENTER);
        Button yes = new Button("Yes");
        yes.setMinWidth(200);
        Button no = new Button("No");
        no.setMinWidth(200);
        root.getChildren().addAll(label, yes, no);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(yes, Pos.CENTER_LEFT);
        StackPane.setAlignment(no, Pos.CENTER_RIGHT);

        

        Scene scene = new Scene(root, 400, 100);
        
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