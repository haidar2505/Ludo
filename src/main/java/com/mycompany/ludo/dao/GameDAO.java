/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.dao;

import com.mycompany.ludo.model.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Haidar
 */
public class GameDAO {

    private Connection conn;

    public GameDAO(Connection conn) {
        this.conn = conn;
    }

    public int createGame() throws SQLException {
        String sql = "INSERT INTO public.game DEFAULT VALUES RETURNING gameid;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("gameid");
            }
        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }

    public Game findGame(int gameid) throws SQLException {
        String sql = "SELECT * FROM public.game WHERE gameid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Game game = new Game();
                game.setGameId(rs.getInt("gameId"));
                game.setStartTime(rs.getTimestamp("starttime").toLocalDateTime());
                game.setEndTime(rs.getTimestamp("endtime").toLocalDateTime());
                game.setCurrentPlayerId(rs.getInt("currentplayerid"));
                return game;
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public void startGame(int gameId, int playerId) throws SQLException {
        String sql = "UPDATE public.game set starttime = ?, currentplayerid = ? WHERE gameid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, playerId);
            stmt.setInt(3, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void nextPlayer(int gameId, int playerId) throws SQLException {
        String sql = "UPDATE public.game SET currentplayerid = ? WHERE gameid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void endGame(int gameId) throws SQLException {
        String sql = "UPDATE public.game SET endtime = ? WHERE gameid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, gameId);
        } catch (SQLException e) {
            throw e;
        }
    }
}