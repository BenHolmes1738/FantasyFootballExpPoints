package com.exppoints.fantasy;

import com.exppoints.fantasy.gui.MenuBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MenuBuilder.buildMainMenu(stage);
    }

    /**
     * main function
     * controls general flow of program
     */
    public static void main(String[] args) {
        launch();
/*
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
*/
    }

}