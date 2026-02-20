package com.exppoints.fantasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StackPane ogRoot = new StackPane();
        
        Label label = new Label("Welcome... Choose an option...");
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Button button1 = new Button("Future Season");
        Button button2 = new Button("Upcoming Game");
        root.getChildren().addAll(button1, button2);

        ogRoot.getChildren().addAll(label, root);

        Scene scene = new Scene(ogRoot, 1000, 700);

        button1.setOnAction(event -> {
            List<String> inputList = new ArrayList<>();

            StackPane futureRoot = new StackPane();

            VBox inputArea = new VBox();
            inputArea.setAlignment(Pos.CENTER_LEFT);
            Label futureLabel = new Label("enter players...");
            TextField playerInput = new TextField();
            playerInput.setMaxWidth(200);
            inputArea.getChildren().addAll(futureLabel, playerInput);

            Button backButton = new Button("Back");

            StackPane.setAlignment(backButton, Pos.TOP_RIGHT);

            futureRoot.getChildren().addAll(inputArea, backButton);

            Scene futureScene = new Scene(futureRoot, 1000, 700);

            playerInput.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case ENTER -> {
                        String input = playerInput.getText();
                        inputList.add(input);
                        Futures futures = new Futures();
                        ArrayList<FuturePlayer> out = new ArrayList<>(futures.getOdds(inputList));
                        futures.write(out);
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
                Futures futures = new Futures();
                ArrayList<FuturePlayer> out = new ArrayList<>(futures.getOdds(input));
                futures.write(out);
            }
            case "2" -> 
                // read the file!
                ReadingWriting.mainRead();
            default -> 
                System.err.println("Please choose between: \n 1: Future Season \n 2: Upcoming Game");
        }
    }

}