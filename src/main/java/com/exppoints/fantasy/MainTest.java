package com.exppoints.fantasy;

import java.util.Arrays;
import java.util.List;

import com.exppoints.fantasy.handlers.FutureHandler;
import com.exppoints.fantasy.handlers.PlayerHandler;
import com.exppoints.fantasy.player.FuturePlayer;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("RUNNING MAIN TEST");
        PlayerHandler<FuturePlayer> handler = new FutureHandler();

        //args
        List<String> input = Arrays.asList(args);

        //handler.createPlayer(input.get(0));
        for (String s : input) {
            handler.createPlayer(s);
        }

        List<FuturePlayer> result = handler.getOdds(input);

        if (result.isEmpty()) {
            System.out.println("NO_ODDS");
        } else {
            for (FuturePlayer p : result) {
                System.out.println(p.getName() + ": " + p.getScore());
            }
        }
    }
}