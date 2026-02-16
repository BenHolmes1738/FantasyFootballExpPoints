package com.exppoints.fantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

// various functions for reading and writing to files
public class ReadingWriting {

    public static void mainRead() {
        String getFile = "Data/Lines.txt";
        String setFile = "ExpPoints.txt";

        ArrayList<GamePlayer> players = new ArrayList<>();

        InputStream is = Main.class
                .getClassLoader()
                .getResourceAsStream(getFile);

        if (is == null) {
            throw new IllegalStateException("Resource not found");
        }

        try (var br = new BufferedReader(new InputStreamReader(is))) {
            String name;
            String position;
            
            do {
                name = br.readLine();
                position = br.readLine();
                GamePlayer player = new GamePlayer(name, position);
                System.out.println("player: " + name);
                if ("QB".equals(position)) {
                    readQB(player, br);
                } else {
                    readNonQB(player, br);
                }

                players.add(player);

            } while ((br.readLine()) != null);
            
            QuickSort qSort = new QuickSort<>();
            qSort.quicksort(players);
            Path output = Paths.get(setFile);
            Files.writeString(output, "");
            for (GamePlayer p : players) {
                if (p.getPosition().equals("QB")) {
                    writeQB(p, output);
                } else {
                    writeNonQB(p, output);
                }
            }
            System.out.println("Successfully wrote to the file.");
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
    }

    // read odds for QB from file
    public static void readQB(GamePlayer player, BufferedReader br) {
        String td;
        String yds;
        String pyds;
        String ptd;
        String ints;

        try {

            td = br.readLine();
            float tdPct = Float.parseFloat(td);
            tdPct = player.convertTdOddsToPct(tdPct);
            player.setTdPercent(tdPct);

            yds = br.readLine();
            float y = Float.parseFloat(yds);
            player.setYds(y);

            pyds = br.readLine();
            float py = Float.parseFloat(pyds);
            player.setPassYds(py);

            ptd = br.readLine();
            float ptdPct = Float.parseFloat(ptd);
            ptdPct = player.convertTdOddsToPct(ptdPct);
            player.setExpPTds(ptdPct);

            ints = br.readLine();
            float intPct = Float.parseFloat(ints);
            intPct = player.convertTdOddsToPct(intPct);
            player.setExpInts(intPct);

        } catch (IOException e) {
            System.err.println("Error reading QB from file: " + e.getMessage());
        }
    }

    // read odds for non QB from file
    public static void readNonQB(GamePlayer player, BufferedReader br) {
        String td;
        String yds;
        String rec;

        try {

            td = br.readLine();
            float tdPct = Float.parseFloat(td);
            tdPct = player.convertTdOddsToPct(tdPct);
            player.setTdPercent(tdPct);

            yds = br.readLine();
            float y = Float.parseFloat(yds);
            player.setYds(y);

            rec = br.readLine();
            float r = Float.parseFloat(rec);
            player.setRec(r);
            
        } catch (IOException e) {
            System.err.println("Error reading non QB from file: " + e.getMessage());
        }
    }

    // write QB projections to file
    public static void writeQB(GamePlayer p, Path output) {
        try {
        Files.writeString(output, p.getName() + 
                            "\nexpected rush tds: \t\t\t\t" + p.getExpTds() + 
                            "\nexpected rush yds: \t\t\t\t" + p.getYds() + 
                            "\nexpected pass tds: \t\t\t\t" + p.getExpPTDs() + 
                            "\nexpected pass yds: \t\t\t\t" + p.getPassYds() +
                            "\nexpected interceptions: \t\t" + p.getExpInts() +
                            "\nexpected score: \t\t\t" + p.getQBScore() + "\n\n",
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing QB to file: " + e.getMessage());
        }
    }

    // write non QB projections to file
    public static void writeNonQB(GamePlayer p, Path output) {
        try {
        Files.writeString(output, p.getName() + 
                            "\nexpected tds: \t\t\t\t" + p.getExpTds() + 
                            "\nexpected yds: \t\t\t\t" + p.getYds() + 
                            "\nexpected recs: \t\t\t\t" + p.getRec() + 
                            "\nexpected score: \t\t\t" + p.getScore() + "\n\n",
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing non QB to file: " + e.getMessage());
        }
    }
}