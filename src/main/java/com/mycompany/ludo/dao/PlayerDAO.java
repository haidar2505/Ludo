/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import com.mycompany.ludo.model.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class PlayerDAO {

    private Connection connect;

    public PlayerDAO(Connection connect) {
        this.connect = connect;
    }

    public int createPlayer(String playerName, Color color) throws SQLException {

        String sql = "INSERT INTO public.player(name, color) VALUES (?, ?);";

        try {
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1, playerName);
            stmt.setString(2, color.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
