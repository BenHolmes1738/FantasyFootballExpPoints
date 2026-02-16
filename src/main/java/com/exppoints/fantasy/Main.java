package com.exppoints.fantasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * main function
     * controls general flow of program
     */
    public static void main(String[] args) {

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