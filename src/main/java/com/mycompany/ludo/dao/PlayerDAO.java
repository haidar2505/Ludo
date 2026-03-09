/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import com.mycompany.ludo.model.Color;
import com.mycompany.ludo.model.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class PlayerDAO {

    private Connection c;

    public PlayerDAO(Connection c) {
        this.c = c;
    }

    public int createPlayer(String playerName, Color color, Boolean isActive) throws SQLException {
        String sql = "INSERT INTO public.player(name, color) VALUES (?, ?);";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, playerName);
            stmt.setString(2, color.name());
            stmt.setBoolean(3, isActive);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Player switchPlayer() throws SQLException {
        String sql = "SELECT TOP 1 * FROM public.player WHERE playerid >= ?";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Player player = new Player();
                player.setPlayerId(rs.getInt(1));
                player.setName(rs.getString(2));
                player.setColor(Color.valueOf(rs.getString(3)));
                player.setIsActive(rs.getBoolean(4));
                return player;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
