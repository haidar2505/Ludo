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

    public List<Pawn> getAllPlayerPawns(int playerId) throws SQLException {
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
    
    public Pawn onePawnOnBoard(int playerId) throws SQLException {
        String sql = "SELECT * FROM public.pawn WHERE ishome = FALSE AND isfinished = FALSE AND playerid = ? LIMIT 1;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
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
    
    public int countPawnOnBoard(int playerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM public.pawn WHERE ishome = FALSE AND isfinished = FALSE AND playerid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }
    
    public Pawn checkEnemyPawnCapture(int position, int playerId) throws SQLException {
        String sql = "SELECT pawn.* FROM public.pawn AS pawn JOIN public.player AS player ON pawn.playerid = player.playerid WHERE pawn.position = ? AND player.playerid != ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, position);
            stmt.setInt(2, playerId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
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
    
        public Pawn checkPlayerPawnPosition(int position, int playerId, int pawnId) throws SQLException {
        String sql = "SELECT pawn.* FROM public.pawn AS pawn JOIN public.player AS player ON pawn.playerid = player.playerid WHERE pawn.position = ? AND player.playerid == ? AND pawn.pawnid != ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, position);
            stmt.setInt(2, playerId);
            stmt.setInt(3, pawnId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
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

    public void movePawn(int pawnId, int position) throws SQLException {
        String sql = "UPDATE public.pawn SET position = ?, isHome = FALSE WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, position);
            stmt.setInt(2, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void enterHomePath(int pawnId, int homePosition) throws SQLException {
        String sql = "UPDATE public.pawn SET position = NULL, homePosition = ? WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, homePosition);
            stmt.setInt(2, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void capturedPawn(int pawnId) throws SQLException {
        String sql = "UPDATE public.pawn SET position = NULL, isHome = TRUE WHERE pawnid = ?;";
        try {
            PreparedStatement stmt =conn.prepareStatement(sql);
            stmt.setInt(1, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void movePawnToFinish(int pawnId) throws SQLException {
        String sql = "UPDATE public.pawn SET homePosition = 5, isFinished = FALSE WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void finishPawn(int pawnId) throws SQLException {
        String sql = "UPDATE public.pawn SET homePosition = NULL, isFinished = TRUE WHERE pawnid = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pawnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public boolean checkAllHomePawns(int playerId) throws SQLException {
        String sql = "SELECT COUNT(*) public.pawn WHERE playerid = ? AND isHome = TRUE;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }
    
    public boolean checkFinishedPawns(int playerId) throws SQLException {
        String sql = "SELECT COUNT(*) public.pawn WHERE playerid = ? AND isFinished = TRUE;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }
}