package com.exppoints.fantasy.gui;

import java.util.ArrayList;
import java.util.List;

import com.exppoints.fantasy.Futures;
import com.exppoints.fantasy.Games;
import com.exppoints.fantasy.daterbase.Database;
import com.exppoints.fantasy.daterbase.DatabaseGame;
import com.exppoints.fantasy.player.FuturePlayer;
import com.exppoints.fantasy.player.GamePlayer;
import com.exppoints.fantasy.player.Player;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuBuilder<P extends Player> {
    public static void buildMainMenu(Stage stage) {
        // main menu
        StackPane root = new StackPane();
        Label label = new Label("Welcome... Choose an option...");
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        VBox options = new VBox();
        options.setAlignment(Pos.CENTER);
        Button button1 = new Button("Future Season");
        Button button2 = new Button("Upcoming Game");
        options.getChildren().addAll(button1, button2);
        root.getChildren().addAll(label, options);

        Scene scene = new Scene(root, 1000, 700);

        // button 1 menu
        button1.setOnAction(event -> {
            buildFutureMenu(stage, scene);
        });

        button2.setOnAction(event -> {
            buildGameMenu(stage, scene);
        });

        stage.setScene(scene);
        stage.setTitle("Fantasy Football App");
        stage.show();
    }

    public static void buildFutureMenu(Stage stage, Scene scene) {
        List<String> inputList = new ArrayList<>();

        StackPane futureRoot = new StackPane();

        // loading pop up while scraping
        StackPane loadingPane = new StackPane();
        Label loadingLabel = new Label("Scraping...");
        loadingLabel.setFont(new Font("Comic Sans MS", 30));
        loadingPane.getChildren().add(loadingLabel);
        StackPane.setAlignment(loadingLabel, Pos.CENTER);
        loadingPane.setVisible(false);

        // input area for player names
        VBox inputArea = new VBox();
        inputArea.setAlignment(Pos.CENTER_LEFT);
        Label futureLabel = new Label("enter players...");
        TextField playerInput = new TextField();
        playerInput.setMaxWidth(200);
        inputArea.getChildren().addAll(futureLabel, playerInput);

        // back to main menu button
        Button backButton = new Button("Back");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        // output area for projections
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setMaxWidth(700);
        StackPane.setAlignment(outputArea, Pos.CENTER_RIGHT);

        futureRoot.getChildren().addAll(inputArea, backButton, outputArea, loadingPane);

        Scene futureScene = new Scene(futureRoot, 1000, 700);

        // when enter pressed, scrape for given player, add to list, output projections, write to file 
        playerInput.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    enterPress(loadingPane, playerInput, inputList, outputArea);
                }
            }
        });
        backButton.setOnAction(e -> {
            stage.setScene(scene);
            stage.setTitle("Fantasy Football App");
        });

        stage.setScene(futureScene);
        stage.setTitle("Future Season");
    }

    public static void buildGameMenu(Stage stage, Scene scene) {
        List<String> inputList = new ArrayList<>();

        StackPane gameRoot = new StackPane();

        // loading pop up while scraping
        StackPane loadingPane = new StackPane();
        Label loadingLabel = new Label("Scraping...");
        loadingLabel.setFont(new Font("Comic Sans MS", 30));
        loadingPane.getChildren().add(loadingLabel);
        StackPane.setAlignment(loadingLabel, Pos.CENTER);
        loadingPane.setVisible(false);

        // input area for player names
        VBox inputArea = new VBox();
        inputArea.setAlignment(Pos.CENTER_LEFT);
        Label gameLabel = new Label("enter players...");
        TextField playerInput = new TextField();
        playerInput.setMaxWidth(200);
        inputArea.getChildren().addAll(gameLabel, playerInput);

        // back to main menu button
        Button backButton = new Button("Back");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        // output area for projections
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setMaxWidth(700);
        StackPane.setAlignment(outputArea, Pos.CENTER_RIGHT);

        Label label = new Label("Yeah so im not done this yet...");
        label.setFont(new Font("Comic Sans MS", 30));
        StackPane.setAlignment(label, Pos.CENTER);

        gameRoot.getChildren().addAll(inputArea, backButton, outputArea, label);

        Scene gameScene = new Scene(gameRoot, 1000, 700);

        // when enter pressed, scrape for given player, add to list, output projections, write to file 
        playerInput.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    enterPressGame(loadingPane, playerInput, inputList, outputArea);
                }
            }
        });
        
        backButton.setOnAction(e -> {
            stage.setScene(scene);
            stage.setTitle("Fantasy Football App");
        });

        stage.setScene(gameScene);
        stage.setTitle("Upcoming Game");
    }

    public static void enterPress(StackPane loadingPane, TextField playerInput, List<String> inputList, TextArea outputArea) {
        loadingPane.setVisible(true);
        String input = playerInput.getText();
        inputList.add(input);

        FuturePlayer player = new FuturePlayer(input);

        int id = Database.getFuturePlayer(player);
        if (id != -1) {
            // check if need rescrape data
            String time = Database.getDate(player);
            int rescrape = Popups.reScrapePrompt(player.getName(), time);
            if (rescrape == 1) {
                Database.deleteFuturePlayer(player);
            }
        }

        Task<ArrayList<FuturePlayer>> task = new Task<>() {
            @Override
            protected ArrayList<FuturePlayer> call() {
                return new ArrayList<>(Futures.getOdds(inputList));
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            ArrayList<FuturePlayer> out = task.getValue();
            Futures.write(out);
            Futures.writeGUI(out, outputArea);
            loadingPane.setVisible(false);
            playerInput.clear();
        });

        new Thread(task).start();
    }

    public static void enterPressGame(StackPane loadingPane, TextField playerInput, List<String> inputList, TextArea outputArea) {
        loadingPane.setVisible(true);
        String input = playerInput.getText();
        inputList.add(input);
        
        GamePlayer player = new GamePlayer(input);
        
        int id = DatabaseGame.getGamePlayer(player);
        if (id != -1) {
            // check if need rescrape data
            int rescrape = Popups.reScrapePrompt(player.getName(), DatabaseGame.getDate(player));
            if (rescrape == 1) {
                DatabaseGame.deleteGamePlayer(player);
            }
        }
        
        Task<ArrayList<GamePlayer>> task = new Task<>() {
            @Override
            protected ArrayList<GamePlayer> call() {
                return new ArrayList<>(Games.getOdds(inputList));
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            ArrayList<GamePlayer> out = task.getValue();
            Games.write(out);
            Games.writeGUI(out, outputArea);
            loadingPane.setVisible(false);
            playerInput.clear();
        });

        new Thread(task).start();
    }
}