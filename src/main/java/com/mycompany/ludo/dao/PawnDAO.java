/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import com.mycompany.ludo.model.Pawn;
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
    
//    public int selectPawn(int id){
//        
//    }

    public int movePawn(Pawn pawn) throws SQLException {
        String sql = "UPDATE public.game SET position=?, ishome = ?, isfinished=? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, pawn.getPosition());
            stmt.setBoolean(2, false);
            pawn.setIsHome(false);
            stmt.setBoolean(3, pawn.isIsFinished());
            stmt.setInt(3, pawn.getPawnId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int returnHomePawn(Pawn pawn){
        String sql = "UPDATE public.game SET position=?, ishome = ? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, pawn.getPosition());
            stmt.setBoolean(2, true);
            pawn.setIsHome(true);
            stmt.setInt(3, pawn.getPlayerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
