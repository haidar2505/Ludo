/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.DAO;

import com.mycompany.ludo.model.PlayerColor;
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
        String sql = "SELECT p.pawnid, p.playerid, p.row, p.col, p.ishome, p.isfinished, pl.color FROM public.pawn AS p JOIN public.player AS pl ON p.playerid = pl.playerid;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pawn pawn = new Pawn();
                pawn.setPawnId(rs.getInt(1));
                pawn.setPlayerId(rs.getInt(2));
                pawn.setRow(rs.getInt(3));
                pawn.setCol(rs.getInt(4));
                pawn.setIsHome(rs.getBoolean(5));
                pawn.setIsFinished(rs.getBoolean(6));
                pawn.setColor(PlayerColor.valueOf(rs.getString(7)));
                pawns.add(pawn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pawns;
    }

    public int createPawn(int playerId, int row, int col) throws SQLException {
        String sql = "INSERT INTO public.pawn(playerid, row, col) VALUES (?, ?, ?);";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, playerId);
            stmt.setInt(2, row);
            stmt.setInt(3, col);
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
        String sql = "UPDATE public.pawn SET row=?, col=?, ishome = ?, isfinished=? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, pawn.getRow());
            stmt.setInt(2, pawn.getCol());
            stmt.setBoolean(3, pawn.getIsHome());
            stmt.setBoolean(4, pawn.getIsFinished());
            stmt.setInt(5, pawn.getPawnId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int returnHomePawn(Pawn pawn) throws SQLException {
        String sql = "UPDATE public.pawn SET row=?, col=?, ishome = ? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setObject(1, pawn.getRow());
            stmt.setInt(2, pawn.getCol());
            pawn.setIsHome(true);
            stmt.setInt(4, pawn.getPlayerId());
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
