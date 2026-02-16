package com.exppoints.fantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * main function
     * controls general flow of program
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose:\n1: Future Season \n2: Upcoming Game\n");
        String option = scanner.nextLine(); 
        
        if (option.equals("1")) {
            List<String> input = GetUserInput.getInput();
            Futures futures = new Futures();
            ArrayList<FuturePlayer> out = new ArrayList<>(futures.getOdds(input));
            //List<FuturePlayer> out = futures.getOdds(input);
            futures.write(out);
        } else if (option.equals("2")) {

            String getFile = "Data/Lines.txt";
            String setFile = "ExpPoints.txt";

            ArrayList<GamePlayer> players = new ArrayList<>();

            InputStream is = Main.class
                    .getClassLoader()
                    .getResourceAsStream(getFile);

            if (is == null) {
                throw new IllegalStateException("Resource not found");
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String name;
                String position;
                
                do {
                    name = br.readLine();
                    position = br.readLine();
                    GamePlayer player = new GamePlayer(name, position);
                    System.out.println("player: " + name);
                    if ("QB".equals(position)) {
                        ReadingWriting.readQB(player, br);
                    } else {
                        ReadingWriting.readNonQB(player, br);
                    }

                    players.add(player);

                } while ((br.readLine()) != null);
                
                //try {
                    QuickSort qSort = new QuickSort<>();
                    qSort.quicksort(players);
                    Path output = Paths.get(setFile);
                    Files.writeString(output, "");
                    for (GamePlayer p : players) {
                        if (p.getPosition().equals("QB")) {
                            ReadingWriting.writeQB(p, output);
                        } else {
                            ReadingWriting.writeNonQB(p, output);
                        }
                    }
                    System.out.println("Successfully wrote to the file.");
                /*} catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }*/
                
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.err.println("Please choose between: \n 1: Future Season \n 2: Upcoming Game");
            return;
        }
    }

}