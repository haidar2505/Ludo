/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.dao;

import com.mycompany.ludo.model.Pawn;
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
public class PawnDAO {

    private Connection conn;

    public PawnDAO(Connection conn) {
        this.conn = conn;
    }

    public void createPawn(int playerId) throws SQLException {
        String sql = "INSERT INTO public.pawn (playerid) VALUES (?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < 4; i++) {
                stmt.setInt(1, playerId);
                stmt.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public Pawn getPawn(int pawnId) throws SQLException {
        String sql = "SELECT * FROM public.pawn WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pawnId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pawn pawn = new Pawn();
                pawn.setPawnId(rs.getInt("pawnid"));
                pawn.setPlayerId(rs.getInt("playerid"));
                pawn.setPosition(rs.getInt("position"));
                pawn.setHomePosition(rs.getInt("homeposition"));
                pawn.setIsHome(rs.getBoolean("ishome"));
                pawn.setIsFinished(rs.getBoolean("isfinished"));
                return pawn;
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }
    
    public List<Pawn> getAllPawn(int playerId) throws SQLException {
        String sql = "SELECT * FROM public.pawn WHERE playerid = ?;";
        List<Pawn> playerPawns = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pawn pawn = new Pawn();
                pawn.setPawnId(rs.getInt("pawnid"));
                pawn.setPlayerId(rs.getInt("playerid"));
                pawn.setPosition(rs.getInt("position"));
                pawn.setHomePosition(rs.getInt("homeposition"));
                pawn.setIsHome(rs.getBoolean("ishome"));
                pawn.setIsFinished(rs.getBoolean("isfinished"));
                playerPawns.add(pawn);
            }
            return playerPawns;
        } catch (SQLException e) {
            throw e;
        }
    }
    
}
