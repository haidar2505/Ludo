/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.dao;

import com.mycompany.ludo.model.Player;
import com.mycompany.ludo.model.enums.PlayerColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PlayerDAO {

    private Connection conn;

    public PlayerDAO(Connection conn) {
        this.conn = conn;
    }

    public int createPlayer(int gameId, String name, PlayerColor color) throws SQLException {
        String sql = "INSERT INTO public.player (gameid, name, color) VALUES (?, ?, CAST(? AS player_color)) RETURNING playerid;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            stmt.setString(2, name);
            stmt.setString(3, color.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }

    public List<Player> getAllPlayers(int gameId) throws SQLException {
        String sql = "SELECT * FROM public.player WHERE gameid = ? ORDER BY playerid;";
        List<Player> allPlayers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setPlayerId(rs.getInt("playerid"));
                player.setGameId(rs.getInt("gameid"));
                player.setName(rs.getString("name"));
                player.setColor(PlayerColor.valueOf(rs.getString("color")));
                player.setIsWinner(rs.getBoolean("iswinner"));
                allPlayers.add(player);
            }
            return allPlayers;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Player getPlayer(int playerId) throws SQLException {
        String sql = "SELECT * FROM public.player WHERE playerid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Player player = new Player();
                player.setPlayerId(rs.getInt("playerid"));
                player.setGameId(rs.getInt("gameid"));
                player.setName(rs.getString("name"));
                player.setColor(PlayerColor.valueOf(rs.getString("color")));
                player.setIsWinner(rs.getBoolean("iswinner"));
                return player;
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public Player getNextPlayer(int gameId, int playerId) throws SQLException {
        List<Player> allPlayers = getAllPlayers(gameId);

        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getPlayerId() == playerId) {
                return allPlayers.get((i + 1) % allPlayers.size());
            }
        }
        return null;
    }

    public void playerWon(int gameId, int playerId) throws SQLException {
        String sql = "UPDATE public.player SET iswinner = TRUE WHERE gameid = ? AND playerId = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
