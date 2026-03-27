package com.exppoints.fantasy.handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.exppoints.fantasy.QuickSort;
import com.exppoints.fantasy.Scraper;
import com.exppoints.fantasy.daterbase.DatabaseGame;
import com.exppoints.fantasy.player.GamePlayer;

import javafx.scene.control.TextArea;


public class GameHandler implements PlayerHandler<GamePlayer> {
    private DatabaseGame db;

    public GamePlayer createPlayer(String name) {
        this.db = new DatabaseGame();
        db.initDatabase();
        return new GamePlayer(name);
    }

    public int getPlayer(GamePlayer player) {
        return db.getPlayer(player);
    }

    public String getDate(GamePlayer player) {
        return db.getDate(player);
    }

    public void deletePlayer(GamePlayer player) {
        db.deletePlayer(player);
    }

    // take string list of players, scrape betting odds for each player
    @Override
    public List<GamePlayer> getOdds(List<String> players) {
        
        // initialize database, player list, scraper
        DatabaseGame db = new DatabaseGame();
        db.initDatabase();
        List<GamePlayer> ret = new java.util.ArrayList<>();
        Scraper scraper = new Scraper();

        // iter thru players
        for (int j = 0; j < players.size(); j++) {

            // add player to player list, check if player is in database
            ret.add(new GamePlayer(players.get(j)));
            int id = db.getPlayer(ret.get(j));
            if (id == -1) {

                // scrape player odds
                List<String> out = scraper.scrape(players.get(j), "https://www.bettingpros.com/nfl/odds/player-props/");
                
                // iter thru scraped data to find odds for desired stats
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
                                    ret.get(j).setPTds(odds);
                                } else {
                                    ret.get(j).setPTdsLambda(odds, line);
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
                                    ret.get(j).setIntsLambda(odds, line);
                                }
                            }
                            default -> {
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing odds for " + ret.get(j).getName() + ": " + e.getMessage());
                    }
                }
                db.insertPlayer(ret.get(j));
            }
        }
        return ret;
    }

    // write player projections to file
    @Override
    public void write(ArrayList<GamePlayer> ps) {

        // sort players by expected score
        QuickSort qSort = new QuickSort();
        qSort.quicksort(ps);

        // write to text file
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
                                "\nexpected pass tds: \t\t\t\t" + p.getExpPTds() + 
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
    @Override
    public void writeGUI(ArrayList<GamePlayer> ps, TextArea outputArea) {
        
        // clear output area, write projections to GUI
        outputArea.clear();
        for (GamePlayer p : ps) {
            outputArea.appendText(p.getName() + 
                                "\nexpected tds: \t\t\t\t\t" + p.getExpTds() + 
                                "\nexpected rush yds: \t\t\t\t" + p.getRushYds() + 
                                "\nexpected rec yds: \t\t\t\t" + p.getRecYds() + 
                                "\nexpected recs: \t\t\t\t\t" + p.getRec() + 
                                "\nexpected pass tds: \t\t\t\t" + p.getExpPTds() + 
                                "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                                "\nexpected interceptions: \t\t\t" + p.getExpInts() +
                                "\nexpected score: \t\t\t" + p.getScore() + "\n\n");
        }
        System.out.println("Successfully wrote to the GUI output area.");
    }
} 