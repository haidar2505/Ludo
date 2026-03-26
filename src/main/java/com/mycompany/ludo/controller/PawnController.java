/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.dao.PawnDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class PawnController {
    
    private final PawnDAO pawnDAO;
    
    public PawnController(Connection conn) {
        this.pawnDAO = new PawnDAO(conn);
    }
    
    public void createPawns(int playerId) throws SQLException {
        pawnDAO.createPawn(playerId);
    }
    
    public void checkPawnCapture(int position, int playerId) throws SQLException {
        pawnDAO.checkEnemyPawnCapture(position, playerId);
    }
                
    public void movePawn(int pawnId, int position) throws SQLException {
        pawnDAO.movePawn(pawnId, position);
    }
    
    public void capturePawn(int pawnId) throws SQLException {
        pawnDAO.capturedPawn(pawnId);
    }
    
    public void enterHomePath(int pawnId, int homePosition) throws SQLException {
        pawnDAO.enterHomePath(pawnId, homePosition);
    }
    
    public void finishPawn(int pawnId) throws SQLException {
        pawnDAO.finishPawn(pawnId);
    }
    
    public int checkAllPawnsFinished(int playerId) throws SQLException {
        return pawnDAO.checkFinishedPawns(playerId);
    }
}
