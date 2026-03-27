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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PawnController {
    
    private final PawnDAO pawnDAO;
    private PlayerController playerController;
    
    private int pawnEntryPosition(PlayerColor color) {
        switch (color) {
            case RED : return 0;
            case BLUE : return 13;
            case YELLOW : return 26;
            case GREEN : return 39;
            default: throw new RuntimeException("Error "+color.name());

        }
    }
    
    private int pawnEntryHomePosition(PlayerColor color) {
        switch(color) {
            case RED : return 50;
            case BLUE : return 11;
            case YELLOW : return 24;
            case GREEN : return 37;
            default: throw new RuntimeException("Error "+color.name());
        }
    }
    
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
    
    public boolean passedHomePath(int currentPosition, int enterHomePosition, int numberRolled) {
        return currentPosition < enterHomePosition && (currentPosition + numberRolled) >= enterHomePosition;
    }
    
    public List<Integer> validPawnToMove(int playerId, PlayerColor color, int numberRolled) throws SQLException {
        List<Pawn> pawns = pawnDAO.getAllPlayerPawns(playerId);
        List<Integer> validPawnsId = new ArrayList<>();
        for(Pawn pawn : pawns){
            if (isValidMove(pawn, color, numberRolled)) {
                validPawnsId.add(pawn.getPawnId());
            }
        }
        return validPawnsId;
    }
    
    public boolean isValidMove(Pawn pawn, PlayerColor color, int numberRolled) throws SQLException {
        int pawnId = pawn.getPawnId();
        int playerId = pawn.getPlayerId();
        Integer position = pawn.getPosition();
        Integer homePosition = pawn.getHomePosition();
        int entryHome = pawnEntryHomePosition(color);
        Pawn pawnSamePosition = pawnDAO.checkPlayerPawnPosition(position, playerId, pawnId);
        
        if(pawn.isIsHome()){
            return numberRolled == 6;
        }
        
        if(pawn.isIsFinished()){
            return false;
        }
        
        if(pawnSamePosition != null){
            return false;
        }
        
        if(passedHomePath(position, entryHome, numberRolled)){
            return (homePosition + numberRolled) <= 5;
        }
        
        return true;
    }

    public void moveAndCapture(int pawnId, int position, int playerId, PlayerColor color, int numberRolled) throws SQLException {
        
        int entryHome = pawnEntryHomePosition(color);
        
        if(passedHomePath(position, entryHome, numberRolled)){
            int homePosition = (position + numberRolled) - entryHome;
            if(homePosition == 5){
                pawnDAO.movePawnToFinish(pawnId);
                pawnDAO.finishPawn(pawnId);
            } else{
                pawnDAO.enterHomePath(pawnId, homePosition);
            }
        } else {
            Pawn pawnCapture = pawnDAO.checkEnemyPawnCapture(position, playerId);
            int pawnPosition = (position + numberRolled) % 52;
            if(pawnCapture != null){
                pawnDAO.capturedPawn(pawnCapture.getPawnId());
            }
            pawnDAO.movePawn(pawnId, pawnPosition);
        }
    }
    
    public void movePawnAuto(int playerId, int gameId, PlayerColor color, int numberRolled) throws SQLException {
        if(pawnDAO.checkAllHomePawns(playerId)){
            List<Pawn> pawns = pawnDAO.getAllPlayerPawns(playerId);
            pawnDAO.movePawn(pawns.get(0).getPawnId(), pawnEntryPosition(color)); 
        } else {
            Pawn pawn = pawnDAO.onePawnOnBoard(playerId);
            int pawnId = pawn.getPawnId();
            
            moveAndCapture(pawnId, pawn.getPosition(), playerId, color, numberRolled);
        }
    }
    
    public void selectPawnMove(int playerId, int gameId, int pawnId, PlayerColor color, int numberRolled) throws SQLException {
        Pawn pawn = pawnDAO.getPawn(pawnId);
        Integer pawnPosition = pawn.getPosition();
        
        if(numberRolled == 6){
            if(pawnPosition == null){
                pawnDAO.movePawn(pawnId, pawnEntryPosition(color)); 
            } else {
                moveAndCapture(pawnId, pawn.getPosition(), playerId, color, numberRolled);
            }
        } else {
            moveAndCapture(pawnId, pawn.getPosition(), playerId, color, numberRolled);
        }
    }
    
    public void finishPawn(int pawnId) throws SQLException {
        pawnDAO.finishPawn(pawnId);
    }
    
    public boolean checkAllPawnsFinished(int playerId) throws SQLException {
        return pawnDAO.checkFinishedPawns(playerId);
    }
}
