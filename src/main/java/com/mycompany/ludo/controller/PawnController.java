/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.dao.PawnDAO;
import com.mycompany.ludo.model.Pawn;
import com.mycompany.ludo.model.enums.PlayerColor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PawnController {
    
    private final PawnDAO pawnDAO;
    private PlayerController playerController;
    
    public PawnController(Connection conn) {
        this.pawnDAO = new PawnDAO(conn);
    }
    
    public void createPawns(int playerId) throws SQLException {
        pawnDAO.createPawn(playerId);
    }
    
    public boolean checkAllHomePawns(int playerId) throws SQLException {
        return pawnDAO.checkAllHomePawns(playerId);
    }
    
    public int countPawnOnBoard(int playerId) throws SQLException {
        return pawnDAO.countPawnOnBoard(playerId);
    }
    
    public int pawnEntryPosition(PlayerColor color) {
        switch (color) {
            case RED : return 0;
            case BLUE : return 13;
            case YELLOW : return 26;
            case GREEN : return 39;
            default: throw new RuntimeException("Error "+color.name());

        }
    }
    
    public Pawn getPawn(int pawnId) throws SQLException {
        return pawnDAO.getPawn(pawnId);
    }
    
    public void checkPawnCapture(int position, int playerId) throws SQLException {
        pawnDAO.checkEnemyPawnCapture(position, playerId);
    }
        
    public void movePawnAuto(int playerId, int gameId, PlayerColor color, int numberRolled) throws SQLException {
        
        if(pawnDAO.checkAllHomePawns(playerId)){
            List<Pawn> pawns = pawnDAO.getAllPlayerPawns(playerId);
            pawnDAO.movePawn(pawns.get(0).getPawnId(), pawnEntryPosition(color)); 
        } else {
            Pawn pawn = pawnDAO.onePawnOnBoard(playerId);
            int pawnId = pawn.getPawnId();
            int pawnPosition = (pawn.getPosition() + numberRolled) % 52;
            pawnDAO.movePawn(pawnId, pawnPosition);
        }
    }
    
    public void selectPawnMove(int playerId, int gameId, int pawnId, PlayerColor color, int numberRolled) throws SQLException {
        Pawn pawn = pawnDAO.getPawn(pawnId);
        Integer pawnPosition = pawn.getPosition();
        
        if(numberRolled == 6){
            if(pawnPosition == null){
                pawnDAO.movePawn(pawnId, pawnEntryPosition(color)); 
            } else {
                pawnPosition = (pawnPosition + numberRolled) % 52;
                pawnDAO.movePawn(pawnId, pawnPosition);
            }
        } else {
            pawnPosition = (pawnPosition + numberRolled) % 52;
            pawnDAO.movePawn(pawnId, pawnPosition);
        }
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
    
    public boolean checkAllPawnsFinished(int playerId) throws SQLException {
        return pawnDAO.checkFinishedPawns(playerId);
    }
}
