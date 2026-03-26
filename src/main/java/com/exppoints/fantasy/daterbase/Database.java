package com.exppoints.fantasy.daterbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public abstract class Database<T> {
    private final String URL;

    protected Database(String URLname) {
        this.URL = "jdbc:sqlite:%s.db".formatted(URLname);
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // required database functionalities
    public abstract void initDatabase();
    public abstract void insertPlayer(T player);
    public abstract int findPlayerId(String name);
    public abstract int getPlayer(T ret);
    public abstract int DoesPlayerExist(String name);
    public abstract String getDate(T player);
    public abstract void deletePlayer(T player);
    public abstract ArrayList<String> getList();
}