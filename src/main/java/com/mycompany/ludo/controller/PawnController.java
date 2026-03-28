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
    private List<Integer> validPawnId = new ArrayList<>();
    private int pawnEntryBoardPosition(PlayerColor color) {
        switch (color) {
            case RED:
                return 0;
            case BLUE:
                return 13;
            case YELLOW:
                return 26;
            case GREEN:
                return 39;
            default:
                throw new RuntimeException("Error " + color.name());

        }
    }
    private int pawnEntryHomePosition(PlayerColor color) {
        switch (color) {
            case RED:
                return 50;
            case BLUE:
                return 11;
            case YELLOW:
                return 24;
            case GREEN:
                return 37;
            default:
                throw new RuntimeException("Error " + color.name());
        }
    }

    public PawnController(Connection conn) {
        this.pawnDAO = new PawnDAO(conn);
    }

    public void createPawns(int playerId) throws SQLException {
        pawnDAO.createPawn(playerId);
    }
    
    public int countPawnOnBoard(int playerId) throws SQLException {
        return pawnDAO.countPawnOnBoard(playerId);
    }
    
    public Pawn getPawn(int pawnId) throws SQLException {
        return pawnDAO.getPawn(pawnId);
    }
    
    public List<Pawn> getAllPlayerPawns(int playerId) throws SQLException {
        return pawnDAO.getAllPlayerPawns(playerId);
    }

    public boolean checkAllHomePawns(int playerId) throws SQLException {
        return pawnDAO.checkAllHomePawns(playerId);
    }
    
    public Pawn checkEnemyPawn(int position, int playerId) throws SQLException{
        return pawnDAO.checkEnemyPawnCapture(position, playerId);
    }
    
    public boolean passedHomePath(int currentPosition, int enterHomePosition, int numberRolled) {
        return currentPosition < enterHomePosition && (currentPosition + numberRolled) >= enterHomePosition;
    }
    
    public void capturePawn(int pawnId) throws SQLException {
        pawnDAO.capturedPawn(pawnId);
    }
    
    public void movePawn(int pawnId, int position) throws SQLException {
        pawnDAO.movePawn(pawnId, position);
    }
    
    public boolean checkAllPawnsFinished(int playerId) throws SQLException {
        return pawnDAO.checkFinishedPawns(playerId);
    }
    
    public List<Integer> getCurrentValidPawns() {
        return validPawnId;
    }

    public boolean handleTurn(int playerId, int gameId, PlayerColor color, int numberRolled) throws SQLException {
        validPawnId = validPawnToMove(playerId, color, numberRolled);
        
        if(validPawnId.isEmpty()){
            return false;
        }
        
        if(validPawnId.size() == 1){
            movePawnAuto(validPawnId.get(0), playerId, color, numberRolled);
            return true;
        } else {
          
        }
        
        return false;
    }

    public List<Integer> validPawnToMove(int playerId, PlayerColor color, int numberRolled) throws SQLException {
        List<Pawn> pawns = pawnDAO.getAllPlayerPawns(playerId);
        List<Integer> validPawnsId = new ArrayList<>();
        for (Pawn pawn : pawns) {
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
        
        if (pawn.getIsFinished()) {
            return false;
        }
        
        if (pawn.getIsHome()) {
            return numberRolled == 6;
        }

        if (homePosition != null) {
            return (homePosition + numberRolled) <= 5;
        }

        if (passedHomePath(position, entryHome, numberRolled)) {
            return ((position + numberRolled) - entryHome) <= 5;
        }
        
        int newPosition = (position + numberRolled) % 52;
        Pawn pawnSamePosition = pawnDAO.checkPlayerPawnPosition(newPosition, playerId, pawnId);
        if(pawnSamePosition != null){
            return false;
        }

        return true;
    }

    public void processMovePawn(int pawnId, int position, int playerId, PlayerColor color, int numberRolled) throws SQLException {

        int entryHome = pawnEntryHomePosition(color);

        if (passedHomePath(position, entryHome, numberRolled)) {
            int homePosition = (position + numberRolled) - entryHome;
            if (homePosition == 5) {
                pawnDAO.finishPawn(pawnId);
            } else {
                pawnDAO.enterHomePath(pawnId, homePosition);
            }
        } else {
            int pawnPosition = (position + numberRolled) % 52;
            Pawn enemyPawnCapture = pawnDAO.checkEnemyPawnCapture(pawnPosition, playerId);
            if (enemyPawnCapture != null) {
                pawnDAO.capturedPawn(enemyPawnCapture.getPawnId());
            }
            pawnDAO.movePawn(pawnId, pawnPosition);
        }
    }

    public void movePawnAuto(int pawnId, int playerId, PlayerColor color, int numberRolled) throws SQLException {
        Pawn pawn = getPawn(pawnId);
        Integer position = pawn.getPosition();
        
        if(position == null){
            int entryBoardPosition = pawnEntryBoardPosition(color);
            Pawn enemyPawnCapture = checkEnemyPawn(entryBoardPosition, playerId);
            if(enemyPawnCapture != null) {
                capturePawn(enemyPawnCapture.getPawnId());
            }
            movePawn(pawnId, entryBoardPosition);
        } else {
            processMovePawn(pawnId, position, playerId, color, numberRolled);
        }
    }

    public void selectedPawnMove(int playerId, int pawnId, PlayerColor color, int numberRolled) throws SQLException {
        Pawn pawn = pawnDAO.getPawn(pawnId);
        Integer pawnPosition = pawn.getPosition();

        if (numberRolled == 6 && pawnPosition == null) {
            int entryPosition = pawnEntryBoardPosition(color);
            Pawn enemyPawnCapture = pawnDAO.checkEnemyPawnCapture(entryPosition, playerId);
            if (enemyPawnCapture != null) {
                pawnDAO.capturedPawn(enemyPawnCapture.getPawnId());
            }
            pawnDAO.movePawn(pawnId, entryPosition);

        } else {
            processMovePawn(pawnId, pawnPosition, playerId, color, numberRolled);
        }
    }
}
