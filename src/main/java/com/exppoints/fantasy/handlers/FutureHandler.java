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
import com.exppoints.fantasy.daterbase.DatabaseFuture;
import com.exppoints.fantasy.player.FuturePlayer;

import javafx.scene.control.TextArea;


public class FutureHandler implements PlayerHandler<FuturePlayer> {
    private final DatabaseFuture db;

    public FutureHandler() {
        this.db = new DatabaseFuture();
        db.initDatabase();
    }

    @Override
    public FuturePlayer createPlayer(String name) {
        return new FuturePlayer(name);
    }

    @Override
    public int getPlayer(FuturePlayer player) {
        return db.getPlayer(player);
    }

    @Override
    public String getDate(FuturePlayer player) {
        return db.getDate(player);
    }

    @Override
    public void deletePlayer(FuturePlayer player) {
        db.deletePlayer(player);
    }

    // take string list of players, scrape betting odds for each player
    @Override
    public List<FuturePlayer> getOdds(List<String> players) {
        
        List<FuturePlayer> ret = new java.util.ArrayList<>();
        Scraper scraper = new Scraper();

        int rem = 0;
        // iter thru players
        for (int j = 0; j < players.size(); j++) {
            int jr = j - rem;
            
            // add player to player list, check if player is in database
            ret.add(new FuturePlayer(players.get(j)));
            int id = db.getPlayer(ret.get(jr));
            if (id == -1) {

                // scrape player odds
                List<String> out = scraper.scrape(players.get(j), "https://www.bettingpros.com/nfl/odds/player-futures/");
                if (out == null) {
                    ret.remove(jr);
                    players.remove(j);
                    rem++;
                    continue;
                }
                // iter thru scraped data to find odds for desired stats
                for (int i = 0; i < out.size(); i++) {
                    try {
                        switch (out.get(i)) {
                            case "Total Rushing Touchdowns" -> ret.get(jr).setRushTds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Receiving Touchdowns" -> ret.get(jr).setRecTds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Rushing Yards" -> ret.get(jr).setRushYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Receiving Yards" -> ret.get(jr).setRecYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Receptions" -> ret.get(jr).setRec(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Passing Yards" -> ret.get(jr).setPassYds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Passing Touchdowns" -> ret.get(jr).setPassTds(Float.parseFloat(out.get(i+9).substring(2)));
                            case "Total Interceptions" -> ret.get(jr).setInts(Float.parseFloat(out.get(i+9).substring(2)));
                            default -> {
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing odds for " + ret.get(j).getName() + ": " + e.getMessage());
                    }
                }
                if (ret.get(jr).getScore() == 0) {
                    ret.remove(jr);
                    players.remove(j);
                    rem++;
                }else {
                    db.insertPlayer(ret.get(jr));
                }
            }
        }
        return ret;
    }

    // write player projections to file
    @Override
    public void write(ArrayList<FuturePlayer> ps) {

        // sort players by expected score
        QuickSort qSort = new QuickSort();
        qSort.quicksort(ps);
        
        // write to text file
        Path output = Paths.get("ExpFuturePoints.txt");
        try {
            Files.writeString(output, "");
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
        for (FuturePlayer p : ps) {
            try {
            Files.writeString(output, p.getName() + 
                                "\nexpected rush tds: \t\t\t\t" + p.getRushTds() + 
                                "\nexpected rush yds: \t\t\t\t" + p.getRushYds() + 
                                "\nexpected rec tds: \t\t\t\t" + p.getRecTds() + 
                                "\nexpected rec yds: \t\t\t\t" + p.getRecYds() + 
                                "\nexpected recs: \t\t\t\t\t" + p.getRec() + 
                                "\nexpected pass tds: \t\t\t\t" + p.getPassTds() + 
                                "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                                "\nexpected interceptions: \t\t" + p.getInts() +
                                "\nexpected score: \t\t\t" + p.getScore() + "\n\n",
                                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Error writing QB to file: " + e.getMessage());
            }
        }
        System.out.println("Successfully wrote to the file.");
    }

    // write player projections to GUI output
    @Override
    public void writeGUI(ArrayList<FuturePlayer> ps, TextArea outputArea) {
        
        // clear output area, write projections to GUI
        outputArea.clear();
        for (FuturePlayer p : ps) {
            outputArea.appendText(p.getName() + 
                                "\nexpected rush tds: \t\t\t\t" + p.getRushTds() + 
                                "\nexpected rush yds: \t\t\t\t" + p.getRushYds() + 
                                "\nexpected rec tds: \t\t\t\t" + p.getRecTds() + 
                                "\nexpected rec yds: \t\t\t\t" + p.getRecYds() + 
                                "\nexpected recs: \t\t\t\t\t" + p.getRec() + 
                                "\nexpected pass tds: \t\t\t\t" + p.getPassTds() + 
                                "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                                "\nexpected interceptions: \t\t" + p.getInts() +
                                "\nexpected score: \t\t\t" + p.getScore() + "\n\n");
        }
        System.out.println("Successfully wrote to the GUI output area.");
    }
}