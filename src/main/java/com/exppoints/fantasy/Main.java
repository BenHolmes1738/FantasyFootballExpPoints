package com.exppoints.fantasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
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

public class Main extends Application {

    @Override
    public void start(Stage stage) {

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
            StackPane.setAlignment(backButton, Pos.TOP_RIGHT);

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
                        loadingPane.setVisible(true);
                        String input = playerInput.getText();
                        inputList.add(input);

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
                }
            });

            backButton.setOnAction(e -> {
                stage.setScene(scene);
                stage.setTitle("Fantasy Football App");
            });

            stage.setScene(futureScene);
            stage.setTitle("Future Season");
        });

        stage.setScene(scene);
        stage.setTitle("Fantasy Football App");
        stage.show();
    }

    /**
     * main function
     * controls general flow of program
     */
    public static void main(String[] args) {
        launch();

        // pick what functionality to run!!!
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose:\n1: Future Season \n2: Upcoming Game\n");
        String option = scanner.nextLine(); 
        
        switch (option) {
            case "1" -> {
                // run future season scraping and writing
                List<String> input = GetUserInput.getInput();
                ArrayList<FuturePlayer> out = new ArrayList<>(Futures.getOdds(input));
                Futures.write(out);
            }
            case "2" -> 
                // read the file!
                ReadingWriting.mainRead();
            default -> 
                System.err.println("Please choose between: \n 1: Future Season \n 2: Upcoming Game");
        }
    }

}