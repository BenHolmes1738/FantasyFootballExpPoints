package com.exppoints.fantasy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Futures {

    // take string list of players, scrape betting odds for each player
    public List<FuturePlayer> getOdds(List<String> players) {
        // first-last format for player names
        List<FuturePlayer> ret = new java.util.ArrayList<>();
        for (int j = 0; j < players.size(); j++) {
            ret.add(new FuturePlayer(players.get(j)));
            // scrape player odds
            Scraper scraper = new Scraper();
            List<String> out = scraper.scrape(players.get(j));
            // iterate through to find odds for each stat
            for (int i = 0; i < out.size(); i++) {
                switch (out.get(i)) {
                    case "Total Rushing Touchdowns" -> ret.get(j).setRushTds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Receiving Touchdowns" -> ret.get(j).setRecTds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Rushing Yards" -> ret.get(j).setRushYds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Receiving Yards" -> ret.get(j).setRecYds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Receptions" -> ret.get(j).setRec(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Passing Yards" -> ret.get(j).setPassYds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Passing Touchdowns" -> ret.get(j).setPassTds(Float.parseFloat(out.get(i+5).substring(2)));
                    case "Total Interceptions" -> ret.get(j).setInts(Float.parseFloat(out.get(i+5).substring(2)));
                    default -> {
                    }
                }
            }
        }
        return ret;
    }


    // write player projections to file
    public void write(ArrayList<FuturePlayer> ps) {
        QuickSort qSort = new QuickSort();
        qSort.quicksort(ps);
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
} 