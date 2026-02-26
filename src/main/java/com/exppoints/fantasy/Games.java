package com.exppoints.fantasy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.exppoints.fantasy.daterbase.DatabaseGame;
import com.exppoints.fantasy.player.GamePlayer;

import javafx.scene.control.TextArea;

public class Games {

    // take string list of players, scrape betting odds for each player
    public static List<GamePlayer> getOdds(List<String> players) {
        DatabaseGame.initDatabaseGame();
        // first-last format for player names
        List<GamePlayer> ret = new java.util.ArrayList<>();
        Scraper scraper = new Scraper();
        for (int j = 0; j < players.size(); j++) {
            ret.add(new GamePlayer(players.get(j)));
            int id = DatabaseGame.getGamePlayer(ret.get(j));
            if (id == -1) {
                // scrape player odds
                List<String> out = scraper.scrape(players.get(j), "https://www.bettingpros.com/nfl/odds/player-props/");
                // iterate through to find odds for each stat
                for (int i = 0; i < out.size(); i++) {
                    System.out.println(out.get(i));
                    try {
                        switch (out.get(i)) {
                            case "Anytime Touchdown Scorer" -> ret.get(j).setTds(Float.parseFloat(out.get(i+3)));
                            case "Rushing Yards Over/Under" -> ret.get(j).setRushYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Receiving Yards Over/Under" -> ret.get(j).setRecYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Receptions Over/Under" -> ret.get(j).setRec(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Passing Yards Over/Under" -> ret.get(j).setPassYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Passing Touchdowns Over/Under" -> {
                                float line = Float.parseFloat(out.get(i+9).substring(2));
                                String oddsStr = out.get(i+10);
                                int start = 1;
                                int end = oddsStr.length()-1;
                                oddsStr = oddsStr.substring(start, end);
                                float odds = Float.parseFloat(oddsStr);
                                if (line < 1) {
                                    ret.get(j).setPassTds(odds);
                                } else {
                                    ret.get(j).setExpectedPassTouchdowns(odds, line);
                                }
                            }
                            case "Interceptions Over/Under" -> {
                                float line = Float.parseFloat(out.get(i+9).substring(2));
                                String oddsStr = out.get(i+10);
                                int start = 1;
                                int end = oddsStr.length()-1;
                                oddsStr = oddsStr.substring(start, end);
                                float odds = Float.parseFloat(oddsStr);
                                if (line < 1) {
                                    ret.get(j).setInts(odds);
                                } else {
                                    ret.get(j).setExpectedInterceptions(odds, line);
                                }
                            }
                            default -> {
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing odds for " + ret.get(j).getName() + ": " + e.getMessage());
                    }
                }
                DatabaseGame.insertGamePlayer(ret.get(j));
            }
        }
        return ret;
    }


    // write player projections to file
    public static void write(ArrayList<GamePlayer> ps) {
        QuickSort qSort = new QuickSort();
        qSort.quicksort(ps);
        Path output = Paths.get("ExpPoints.txt");
        try {
            Files.writeString(output, "");
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
        for (GamePlayer p : ps) {
            try {
            Files.writeString(output, p.getName() + 
                                "\nexpected tds: \t\t\t\t\t" + p.getExpTds() + 
                                "\nexpected rush yds: \t\t\t\t" + p.getRushYds() + 
                                "\nexpected rec yds: \t\t\t\t" + p.getRecYds() + 
                                "\nexpected recs: \t\t\t\t\t" + p.getRec() + 
                                "\nexpected pass tds: \t\t\t\t" + p.getExpPTDs() + 
                                "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                                "\nexpected interceptions: \t\t\t" + p.getExpInts() +
                                "\nexpected score: \t\t\t" + p.getScore() + "\n\n",
                                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
        System.out.println("Successfully wrote to the file.");
    }

    // write player projections to GUI output
    public static void writeGUI(ArrayList<GamePlayer> ps, TextArea outputArea) {
        
        outputArea.clear();
        for (GamePlayer p : ps) {
            outputArea.appendText(p.getName() + 
                                "\nexpected tds: \t\t\t\t\t" + p.getExpTds() + 
                                "\nexpected rush yds: \t\t\t\t" + p.getRushYds() + 
                                "\nexpected rec yds: \t\t\t\t" + p.getRecYds() + 
                                "\nexpected recs: \t\t\t\t\t" + p.getRec() + 
                                "\nexpected pass tds: \t\t\t\t" + p.getExpPTDs() + 
                                "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                                "\nexpected interceptions: \t\t\t" + p.getExpInts() +
                                "\nexpected score: \t\t\t" + p.getScore() + "\n\n");
        }
        System.out.println("Successfully wrote to the GUI output area.");
    }
} 