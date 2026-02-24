package com.exppoints.fantasy.daterbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.exppoints.fantasy.player.FuturePlayer;

public class Database {
    private static final String URL = "jdbc:sqlite:fantasy.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    public static void initDatabaseFuture() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS future_players (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      name TEXT NOT NULL,
                      rush_tds REAL,
                      rush_yds REAL,
                      rec_tds REAL,
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

    public static void insertFuturePlayer(FuturePlayer player) {
        String sql = """
                     INSERT INTO future_players (name, rush_tds, rush_yds, rec_tds, rec_yds, rec, pass_tds, pass_yds, ints)
                     VALUES ('%s', %f, %f, %f, %f, %f, %f, %f, %f);""".formatted(
                        player.getName(), player.getRushTds(), player.getRushYds(), player.getRecTds(), player.getRecYds(),
                        player.getRec(), player.getPassTds(), player.getPassYds(), player.getInts());
        System.out.println(sql);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error inserting future player: " + e.getMessage());
        }
    }

    public static int findFuturePlayerId(String name) {
        String sql = "SELECT id FROM future_players WHERE name = '%s';".formatted(name);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error finding future player ID: " + e.getMessage());
        }
        return -1; // not found
    }

    public static int getFuturePlayer(FuturePlayer ret) {
        int returnCode = -1;
        String sql  = "SELECT * FROM future_players WHERE name = '%s';".formatted(ret.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                ret.setRushTds(rs.getFloat("rush_tds"));
                ret.setRushYds(rs.getFloat("rush_yds"));
                ret.setRecTds(rs.getFloat("rec_tds"));
                ret.setRecYds(rs.getFloat("rec_yds"));
                ret.setRec(rs.getFloat("rec"));
                ret.setPassTds(rs.getFloat("pass_tds"));
                ret.setPassYds(rs.getFloat("pass_yds"));
                ret.setInts(rs.getFloat("ints"));
                returnCode = 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting future player: " + e.getMessage());
        } finally {
            return returnCode;
        }
    }

    public static int DoesPlayerExist(String name) {
        int returnCode = -1;
        String sql  = "SELECT * FROM future_players WHERE name = '%s';".formatted(name);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                returnCode = 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting future player: " + e.getMessage());
        } finally {
            return returnCode;
        }
    }

    public static String getDate(FuturePlayer player) {
        String sql = "SELECT created_at FROM future_players WHERE name = '%s';".formatted(player.getName());
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

    public static void deleteFuturePlayer(FuturePlayer player) {
        String sql = "DELETE FROM future_players WHERE name = '%s';".formatted(player.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error deleting future player: " + e.getMessage());
        }
    }
}