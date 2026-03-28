/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.service;

import com.mycompany.ludo.dao.PlayerDAO;
import com.mycompany.ludo.model.Game;
import com.mycompany.ludo.model.Player;
import com.mycompany.ludo.model.enums.PlayerColor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PlayerService {

    private final PlayerDAO playerDAO;
    private GameService gameController;
    private DiceService diceController;
    private PawnService pawnController;

    public PlayerService(Connection conn, GameService gameController,  DiceService diceController, PawnService pawnController) {
        this.playerDAO = new PlayerDAO(conn);
        this.gameController = gameController;
        this.diceController = diceController;
        this.pawnController = pawnController;
    }
    
    public List<Player> getAllPlayers(int gameId) throws SQLException {
        return playerDAO.getAllPlayers(gameId);
    }

    public Player getPlayer(int playerId) throws SQLException {
        return playerDAO.getPlayer(playerId);
    }
    
    public void createPlayer() throws SQLException {
        
    }

//    public int playersNumber() {
//        return 2;
//    }

    public void createPlayer(int gameId, String[] name, String[] color) throws SQLException {
        Game verifyGame = gameController.findGame(gameId);
        if (verifyGame == null) {
            throw new RuntimeException("Error");
        }
        for (int i = 0; i < name.length; i++) {
            int playerId = playerDAO.createPlayer(gameId, name[i], color[i]);
            pawnController.createPawns(playerId);
        }
        List<Player> players = getAllPlayers(gameId);
        gameController.startGame(gameId, players.get(0).getPlayerId());
    }

    public void nextTurn(int gameId, int playerId) throws SQLException {
        Player nextPlayer = playerDAO.getNextPlayer(gameId, playerId);
        if (nextPlayer == null) {
            throw new RuntimeException("Error");
        }
        int nextPlayerId = nextPlayer.getPlayerId();
        gameController.updateCurrentPlayer(gameId, nextPlayerId);
    }
            
    public void playerTurn(int playerId) throws SQLException {
        Player player = getPlayer(playerId);
        int gameId = player.getGameId();
        PlayerColor color = player.getColor();
        
        int numberRolled = diceController.rollDice();
        
        boolean turnDone = pawnController.handleTurn(playerId, gameId, color, numberRolled);
        
        if(turnDone){
            checkPlayerWon(gameId, playerId);
            if (numberRolled != 6) {
                nextTurn(gameId, playerId);
            }
        }
    }

    public void checkPlayerWon(int gameId, int playerId) throws SQLException {
        if (pawnController.checkAllPawnsFinished(playerId)) {
            playerDAO.playerWon(gameId, playerId);
            gameController.endGame(gameId);
        }
    }
    
    public void selectPawn(int playerId, int pawnId) throws SQLException {
        Player player = getPlayer(playerId);
        int gameId = player.getGameId();
        PlayerColor color = player.getColor();
        
        int numberRolled = diceController.getNumberRolled();
        
        List<Integer> validPawns = pawnController.getCurrentValidPawns();
        if(!validPawns.contains(pawnId)) {
            throw new RuntimeException("Invalid pawn selected!");
        }
        pawnController.selectedPawnMove(playerId, pawnId, color, numberRolled);
        checkPlayerWon(gameId, playerId);
        if (numberRolled != 6) {
            nextTurn(gameId, playerId);
        }
    }
}