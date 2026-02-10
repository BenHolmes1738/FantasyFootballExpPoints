package com.exppoints.fantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReadingWriting {
    public static void readQB(Player player, BufferedReader br) {
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
            player.setPYds(py);

            ptd = br.readLine();
            float ptdPct = Float.parseFloat(ptd);
            ptdPct = player.convertTdOddsToPct(ptdPct);
            player.setPTdPercent(ptdPct);

            ints = br.readLine();
            float intPct = Float.parseFloat(ints);
            intPct = player.convertTdOddsToPct(intPct);
            player.setIntPercent(intPct);

        } catch (IOException e) {
            System.err.println("Error reading QB from file: " + e.getMessage());
        }
    }

    public static void readNonQB(Player player, BufferedReader br) {
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

    public static void writeQB(Player p, Path output) {
        try {
        Files.writeString(output, p.getName() + 
                            "\nexpected rush tds: \t\t\t\t" + p.getExpTds() + 
                            "\nexpected rush yds: \t\t\t\t" + p.getYds() + 
                            "\nexpected pass tds: \t\t\t\t" + p.getExpPTDs() + 
                            "\nexpected pass yds: \t\t\t\t" + p.getPYds() +
                            "\nexpected interceptions: \t\t" + p.getExpInts() +
                            "\nexpected score: \t\t\t" + p.getQBScore() + "\n\n",
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing QB to file: " + e.getMessage());
        }
    }

    public static void writeNonQB(Player p, Path output) {
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