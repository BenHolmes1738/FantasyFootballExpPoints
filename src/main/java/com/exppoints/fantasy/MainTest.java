package com.exppoints.fantasy;

import java.util.Arrays;
import java.util.List;

import com.exppoints.fantasy.handlers.FutureHandler;
import com.exppoints.fantasy.handlers.PlayerHandler;
import com.exppoints.fantasy.player.FuturePlayer;

public class MainTest {
    public static void main(String[] args) {
        PlayerHandler<FuturePlayer> handler = new FutureHandler();

        //args
        List<String> input = Arrays.asList(args);

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