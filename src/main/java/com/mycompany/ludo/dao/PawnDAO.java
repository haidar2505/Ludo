/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class PawnDAO {

    private Connection c;

    public PawnDAO(Connection c) {
        this.c = c;
    }

    public int createPawn(int playerId) throws SQLException {
        String sql = "INSERT INTO public.pawn(playerid, position) VALUES (?, ?);";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, playerId);
            stmt.setInt(2, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int movePawn(int pawnId, int position, boolean isFinished) throws SQLException {
        String sql = "UPDATE public.game SET position=?, ishome = ?, isfinished=? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, position);
            stmt.setBoolean(2, false);
            stmt.setBoolean(3, isFinished);
            stmt.setInt(3, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int returnHomePawn(int position){
        String sql = "UPDATE public.game SET position=?, ishome = ? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, position);
            stmt.setBoolean(2, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
