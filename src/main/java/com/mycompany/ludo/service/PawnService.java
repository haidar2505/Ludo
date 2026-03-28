/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.service;

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
public class PawnService {

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
                throw new IllegalArgumentException("Error " + color.name());
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
                throw new IllegalArgumentException("Error " + color.name());
        }
    }

    public List<Integer> getCurrentValidPawns() {
        return validPawnId;
    }

    public PawnService(Connection conn) {
        this.pawnDAO = new PawnDAO(conn);
    }

    public void createPawns(int playerId) throws SQLException {
        pawnDAO.createPawn(playerId);
    }

    public List<Pawn> getAllPlayerPawns(int playerId) throws SQLException {
        return pawnDAO.getAllPlayerPawns(playerId);
    }

    public Pawn getPawn(int pawnId) throws SQLException {
        return pawnDAO.getPawn(pawnId);
    }

    public boolean checkAllHomePawns(int playerId) throws SQLException {
        return pawnDAO.checkAllHomePawns(playerId);
    }
    
    public Pawn checkEnemyPawnPosition(int playerId, int position) throws SQLException {
        return pawnDAO.checkEnemyPawnPosition(playerId, position);
    }
    
    public Pawn checkPlayerPawnPosition(int playerId, int pawnId, int position) throws SQLException {
        return pawnDAO.checkPlayerPawnPosition(playerId, pawnId, position);
    }
    
    public void movePawn(int pawnId, int position) throws SQLException {
        pawnDAO.movePawn(pawnId, position);
    }
    
    public void captureEnemyPawn(int pawnId) throws SQLException {
        pawnDAO.captureEnemyPawn(pawnId);
    }
    
    public void enterHomePath(int pawnId, int homePosition) throws SQLException {
        pawnDAO.enterHomePath(pawnId, homePosition);
    }

    public void finishPawn(int pawnId) throws SQLException {
        pawnDAO.finishPawn(pawnId);
    }
    
    public boolean checkFinishedPawns(int playerId) throws SQLException {
        return pawnDAO.checkFinishedPawns(playerId);
    }

    public void checkCapture(int playerId, int enemyPosition) throws SQLException {
        Pawn enemyPawn = checkEnemyPawnPosition(playerId, enemyPosition);
        if (enemyPawn != null) {
            captureEnemyPawn(enemyPawn.getPawnId());
        }
    }

    public boolean isEntringHomePath(Integer currentPosition, int numberRolled, int entryHomePosition) {
        if (currentPosition == null) {
            return false;
        }
        return currentPosition < entryHomePosition && (currentPosition + numberRolled) >= entryHomePosition;
    }

    public void handleHomePath(int pawnId, int currentPosition, int numberRolled, int entryHomePosition) throws SQLException {
        int homePosition = (currentPosition + numberRolled) - entryHomePosition;
        if (homePosition == 5) {
            finishPawn(pawnId);
        }
        enterHomePath(pawnId, homePosition);
    }

    public List<Integer> validPawnToMove(int playerId, PlayerColor color, int numberRolled) throws SQLException {
        List<Pawn> pawns = getAllPlayerPawns(playerId);
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
        Integer currentPosition = pawn.getPosition();
        Integer homePosition = pawn.getHomePosition();
        int entryHomePosition = pawnEntryHomePosition(color);

        if (pawn.getIsFinished()) {
            return false;
        }

        if (pawn.getIsHome()) {
            return numberRolled == 6;
        }

        if (homePosition != null) {
            return (homePosition + numberRolled) <= 5;
        }

        if (isEntringHomePath(currentPosition, numberRolled, entryHomePosition)) {
            return ((currentPosition + numberRolled) - entryHomePosition) <= 5;
        }

        int newPosition = (currentPosition + numberRolled) % 52;
        Pawn pawnSamePosition = checkPlayerPawnPosition(playerId, pawnId, newPosition);
        if (pawnSamePosition != null) {
            return false;
        }

        return true;
    }

    public boolean handleTurn(int gameId, int playerId, PlayerColor color, int numberRolled) throws SQLException {
        validPawnId = validPawnToMove(playerId, color, numberRolled);

        if (validPawnId.isEmpty()) {
            return false;
        } else {
            if (checkAllHomePawns(playerId) || validPawnId.size() == 1) {
                handleMovePawn(playerId, color, validPawnId.get(0), numberRolled);
            } else {
                // Player choses a pawn to move
            }
            return true;
        }
    }

    public void movePawnFromHome(int playerId, PlayerColor color, int pawnId) throws SQLException {
        int entryPosition = pawnEntryBoardPosition(color);
        checkCapture(playerId, entryPosition);
        movePawn(pawnId, entryPosition);
    }

    public void movePawnBoard(int playerId, PlayerColor color, int pawnId, int numberRolled) throws SQLException {
        int entryHomePosition = pawnEntryHomePosition(color);

        Pawn pawn = getPawn(pawnId);
        int currentPosition = pawn.getPosition();
        int newPosition = (currentPosition + numberRolled) % 52;

        if (isEntringHomePath(currentPosition, numberRolled, entryHomePosition)) {
            handleHomePath(pawnId, currentPosition, numberRolled, entryHomePosition);
        } else {
            checkCapture(playerId, newPosition);
            movePawn(pawnId, newPosition);
        }
    }

    public void handleMovePawn(int playerId, PlayerColor color, int pawnId, int numberRolled) throws SQLException {
        Pawn pawn = getPawn(pawnId);
        Integer currentPosition = pawn.getPosition();

        if (currentPosition == null) {
            movePawnFromHome(playerId, color, pawnId);
        } else {
            movePawnBoard(playerId, color, pawnId, numberRolled);
        }
    }
}
