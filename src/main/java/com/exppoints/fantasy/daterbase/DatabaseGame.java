package com.exppoints.fantasy.daterbase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.exppoints.fantasy.player.GamePlayer;

public class DatabaseGame extends Database<GamePlayer> {

    public DatabaseGame() {
        super("game");
    }

    @Override
    public void initDatabase() {
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

    @Override
    public void insertPlayer(GamePlayer player) {
        String sql = """
                     INSERT INTO game_players (name, tds, rush_yds, rec_yds, rec, pass_tds, pass_yds, ints)
                     VALUES ('%s', %f, %f, %f, %f, %f, %f, %f);""".formatted(
                        player.getName(), player.getExpTds(), player.getRushYds(), player.getRecYds(),
                        player.getRec(), player.getExpPTds(), player.getPassYds(), player.getExpInts());
        System.out.println(sql);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error inserting game player: " + e.getMessage());
        }
    }

    @Override
    public int findPlayerId(String name) {
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
        return -1;
    }

    @Override
    public int getPlayer(GamePlayer ret) {
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
                ret.setExpPTds(rs.getFloat("pass_tds"));
                ret.setPassYds(rs.getFloat("pass_yds"));
                ret.setExpInts(rs.getFloat("ints"));
                returnCode = 0;
            }
        } catch (SQLException e) {
            System.err.println("Error getting game player: " + e.getMessage());
        } finally {
            return returnCode;
        }
    }

    @Override
    public int DoesPlayerExist(String name) {
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

    @Override
    public String getDate(GamePlayer player) {
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

    @Override
    public void deletePlayer(GamePlayer player) {
        String sql = "DELETE FROM game_players WHERE name = '%s';".formatted(player.getName());
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error deleting game player: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> getList() {
        ArrayList<String> ret = new ArrayList<>();
        String sql = "SELECT name FROM game_players;";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ret.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting game player list: " + e.getMessage());
        }
        return ret;
    }
}