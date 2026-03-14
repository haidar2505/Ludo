/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import com.mycompany.ludo.model.Pawn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.geometric.PGpoint;

/**
 *
 * @author Haidar
 */
public class PawnDAO {

    private Connection c;

    public PawnDAO(Connection c) {
        this.c = c;
    }

    public List<Pawn> getAllPawn() throws SQLException {
        List<Pawn> pawns = new ArrayList();
        String sql = "SELECT * FROM public.Pawn;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pawn pawn = new Pawn();
                pawn.setPawnId(rs.getInt(1));
                pawn.setPlayerId(rs.getInt(2));
                pawn.setPosition((PGpoint) rs.getObject(3));
                pawn.setIsHome(rs.getBoolean(4));
                pawn.setIsFinished(rs.getBoolean(5));
                pawns.add(pawn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pawns;
    }

    public int createPawn(int playerId) throws SQLException {
        String sql = "INSERT INTO public.pawn(playerid, position) VALUES (?, ?);";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, playerId);
            stmt.setObject(2, new PGpoint(0,0));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

//    public Pawn selectPawn(int id) throws SQLException {
//        String sql = "SELECT * FROM public.pawn WHERE pawnid = ?;";
//        try {
//            PreparedStatement stmt = c.prepareStatement(sql);
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Pawn pawn = new Pawn();
//                pawn.setPawnId(rs.getInt(1));
//                pawn.setPlayerId(rs.getInt(2));
//                pawn.setPosition(rs.getInt(3));
//                pawn.setIsHome(rs.getBoolean(4));
//                pawn.setIsFinished(rs.getBoolean(5));
//                return pawn;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public int movePawn(Pawn pawn) throws SQLException {
        String sql = "UPDATE public.pawn SET position=?, ishome = ?, isfinished=? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setObject(1, pawn.getPosition());
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

    public int returnHomePawn(Pawn pawn) throws SQLException {
        String sql = "UPDATE public.pawn SET position=?, ishome = ? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setObject(1, pawn.getPosition());
            stmt.setBoolean(2, true);
            pawn.setIsHome(true);
            stmt.setInt(3, pawn.getPlayerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAllPawn() throws SQLException {
        String sql = "TRUNCATE TABLE public.pawn CASCADE;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
