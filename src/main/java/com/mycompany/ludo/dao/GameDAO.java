/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

//import com.mycompany.ludo.model.Game;
import com.mycompany.ludo.model.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 *
 * @author Haidar
 */
public class GameDAO {

    private Connection c;

    public GameDAO(Connection c) {
        this.c = c;
    }

    public int createGame() throws SQLException {
        String sql = "INSERT INTO public.game(starttime, endtime, winnerid) VALUES (?, ?, ?); ";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setNull(2, Types.TIMESTAMP);
            stmt.setNull(3, Types.INTEGER);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Game gameInfo() throws SQLException {
        String sql = "SELECT * FROM public.game;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Game infoGame = new Game();
                infoGame.setGameId(rs.getInt(1));
                infoGame.setStartTime(rs.getString(2));
                infoGame.setEndTime(rs.getString(3));
                infoGame.setWinnerId(rs.getInt(4));
                return infoGame;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int endGame(Game game) throws SQLException {
        String sql = "UPDATE public.game SET endtime=?, winnerid=? WHERE gameid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, game.getWinnerId());
            stmt.setInt(3, game.getGameId());
            stmt.executeUpdate();
            System.out.println("Game sucessfully created");
        } catch (SQLException e) {
            System.out.println("Creation failed");
            e.printStackTrace();
        }
        return 0;
    }
}
