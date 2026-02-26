package com.exppoints.fantasy.daterbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.exppoints.fantasy.player.GamePlayer;

public class DatabaseGame {
    private static final String URL = "jdbc:sqlite:game.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    public static void initDatabaseGame() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS game_players (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      name TEXT NOT NULL,
                      tds REAL,
                      rush_yds REAL,
                      rec_yds REAL,
                      rec REAL,
                      pass_tds REAL,
                      pass_yds REAL,
                      ints REAL, 
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                     );""";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void insertGamePlayer(GamePlayer player) {
        String sql = """
                     INSERT INTO game_players (name, tds, rush_yds, rec_yds, rec, pass_tds, pass_yds, ints)
                     VALUES ('%s', %f, %f, %f, %f, %f, %f, %f);""".formatted(
                        player.getName(), player.getExpTds(), player.getRushYds(), player.getRecYds(),
                        player.getRec(), player.getExpPTDs(), player.getPassYds(), player.getExpInts());
        System.out.println(sql);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error inserting game player: " + e.getMessage());
        }
    }

    public static int findGamePlayerId(String name) {
        String sql = "SELECT id FROM game_players WHERE name = '%s';".formatted(name);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error finding game player ID: " + e.getMessage());
        }
        return -1; // not found
    }

    public static int getGamePlayer(GamePlayer ret) {
        int returnCode = -1;
        String sql  = "SELECT * FROM game_players WHERE name = '%s';".formatted(ret.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                ret.setExpTds(rs.getFloat("tds"));
                ret.setRushYds(rs.getFloat("rush_yds"));
                ret.setRecYds(rs.getFloat("rec_yds"));
                ret.setRec(rs.getFloat("rec"));
                ret.setPassTds(rs.getFloat("pass_tds"));
                ret.setPassYds(rs.getFloat("pass_yds"));
                ret.setInts(rs.getFloat("ints"));
                returnCode = 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting game player: " + e.getMessage());
        } finally {
            return returnCode;
        }
    }

    public static int DoesPlayerExist(String name) {
        int returnCode = -1;
        String sql  = "SELECT * FROM game_players WHERE name = '%s';".formatted(name);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                returnCode = 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting game player: " + e.getMessage());
        } finally {
            return returnCode;
        }
    }

    public static String getDate(GamePlayer player) {
        String sql = "SELECT created_at FROM game_players WHERE name = '%s';".formatted(player.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("created_at");
            }
        } catch (SQLException e) {
            System.err.println("Error getting date: " + e.getMessage());
        }
        return "N/A";
    }

    public static void deleteGamePlayer(GamePlayer player) {
        String sql = "DELETE FROM game_players WHERE name = '%s';".formatted(player.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error deleting game player: " + e.getMessage());
        }
    }
}