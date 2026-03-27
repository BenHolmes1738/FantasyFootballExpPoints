package com.exppoints.fantasy.handlers;

import com.exppoints.fantasy.player.Player;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.control.TextArea;


public interface PlayerHandler<P extends Player> {
    P createPlayer(String name);

    int getPlayer(P player);
    String getDate(P player);
    void deletePlayer(P player);

    List<P> getOdds(List<String> players);
    void write(ArrayList<P> players);
    void writeGUI(ArrayList<P> players, TextArea outputArea);
}