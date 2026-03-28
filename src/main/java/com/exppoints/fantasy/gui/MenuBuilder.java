package com.exppoints.fantasy.gui;

import java.util.ArrayList;
import java.util.List;

import com.exppoints.fantasy.daterbase.DatabaseFuture;
import com.exppoints.fantasy.daterbase.DatabaseGame;
import com.exppoints.fantasy.handlers.FutureHandler;
import com.exppoints.fantasy.handlers.GameHandler;
import com.exppoints.fantasy.handlers.PlayerHandler;
import com.exppoints.fantasy.player.Player;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuBuilder<P extends Player> {
    public static void buildMainMenu(Stage stage) {
        Image druski = new Image(MenuBuilder.class.getResourceAsStream("/Data/druskiHenry.png"));
        Image losMcCaffery = new Image(MenuBuilder.class.getResourceAsStream("/Data/losMcCaffery.png"));
        ImageView druskiView = new ImageView(druski);
        ImageView losView = new ImageView(losMcCaffery);

        druskiView.setFitHeight(700);
        druskiView.setFitWidth(500);
        losView.setFitHeight(700);
        losView.setFitWidth(500);
        losView.setScaleX(-1);

        HBox images = new HBox();
        images.setAlignment(Pos.CENTER);
        images.getChildren().addAll(losView, druskiView);

        // main menu
        StackPane root = new StackPane();
        Label label = new Label("Fantasy Football Projection Calc");
        label.setStyle(
            "-fx-font-weight: bold;" + 
            "-fx-font-size: 30px;" + 
            "-fx-font-family: 'Bebas Neue';" + 
            "-fx-text-fill: gold;" +
            "-fx-background-color: black;" + 
            "-fx-background-radius: 10px;"
        );
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        VBox options = new VBox();
        options.setAlignment(Pos.CENTER);
        Button button1 = new Button("Future Season");
        button1.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: gold;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;"
        );
        Button button2 = new Button("Upcoming Game");
        button2.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: gold;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;"
        );
        options.getChildren().addAll(button1, button2);
        root.getChildren().addAll(images, label, options);

        Scene scene = new Scene(root, 1000, 700);

        // button 1 menu
        button1.setOnAction(event -> {
            buildFutureMenu(stage, scene);
        });
        button1.setOnMouseEntered(e -> button1.setStyle(
            "-fx-background-color: gold;" +
            "-fx-text-fill: black;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;"+
            "-fx-border-radius: 12;"
        ));
        button1.setOnMouseExited(e -> button1.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: gold;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;"+
            "-fx-border-radius: 12;"
        ));

        button2.setOnAction(event -> {
            buildGameMenu(stage, scene);
        });
        button2.setOnMouseEntered(e -> button2.setStyle(
            "-fx-background-color: gold;" +
            "-fx-text-fill: black;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;"+
            "-fx-border-radius: 12;"
        ));
        button2.setOnMouseExited(e -> button2.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: gold;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 25;" + 
            "-fx-border-color: gold;" + 
            "-fx-border-width: 2;"+
            "-fx-border-radius: 12;"
        ));

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            druskiView.setFitWidth(newVal.intValue() / 2);
            losView.setFitWidth(newVal.intValue() / 2);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            druskiView.setFitHeight(newVal.intValue());
            losView.setFitHeight(newVal.intValue());
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

        ComboBox<String> pInput = new ComboBox<>();
        pInput.setMaxWidth(200);
        DatabaseFuture db = new DatabaseFuture();
        db.initDatabase();
        for (String s : db.getList()) {
            pInput.getItems().add(s);
        }
        pInput.setEditable(true);
        pInput.setPromptText("Enter player name...");

        inputArea.getChildren().addAll(futureLabel, pInput);

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
        pInput.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    enterPress(loadingPane, pInput, inputList, outputArea, new FutureHandler());
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

        ComboBox<String> pInput = new ComboBox<>();
        pInput.setMaxWidth(200);
        DatabaseGame db = new DatabaseGame();
        db.initDatabase();
        for (String s : db.getList()) {
            pInput.getItems().add(s);
        }
        pInput.setEditable(true);
        pInput.setPromptText("Enter player name...");

        inputArea.getChildren().addAll(gameLabel, pInput);

        // back to main menu button
        Button backButton = new Button("Back");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        // output area for projections
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setMaxWidth(700);
        StackPane.setAlignment(outputArea, Pos.CENTER_RIGHT);

        gameRoot.getChildren().addAll(inputArea, backButton, outputArea);

        Scene gameScene = new Scene(gameRoot, 1000, 700);

        // when enter pressed, scrape for given player, add to list, output projections, write to file 
        pInput.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    enterPress(loadingPane, pInput, inputList, outputArea, new GameHandler());
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

    public static <P extends Player> void enterPress(StackPane loadingPane, ComboBox<String> pInput, List<String> inputList, TextArea outputArea, PlayerHandler<P> handler) {
        loadingPane.setVisible(true);
        String input = pInput.getEditor().getText();
        inputList.add(input);

        P player = handler.createPlayer(input);
        
        int id = handler.getPlayer(player);
        if (id != -1) {
            // check if need rescrape data
            String time = handler.getDate(player);
            int rescrape = Popups.reScrapePrompt(input, time);
            if (rescrape == 1) {
                handler.deletePlayer(player);
            }
        }

        Task<ArrayList<P>> task = new Task<>() {
            @Override
            protected ArrayList<P> call() {
                return new ArrayList<>(handler.getOdds(inputList));
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            ArrayList<P> out = task.getValue();
            handler.write(out);
            handler.writeGUI(out, outputArea);
            loadingPane.setVisible(false);
            pInput.setValue(null);
            pInput.getEditor().clear();
        });

        new Thread(task).start();
    }
}