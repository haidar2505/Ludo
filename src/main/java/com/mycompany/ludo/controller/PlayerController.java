/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

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
public class PlayerController {

    private final PlayerDAO playerDAO;
    private GameController gameController;
    private PawnController pawnController;
    private DiceController diceController;

    public PlayerController(Connection conn) {
        this.playerDAO = new PlayerDAO(conn);
    }

    public int playersNumber() {
        return 2;
    }

    public void createPlayer(int gameId, String[] name, String[] color) throws SQLException {
        Game verifyGame = gameController.findGame(gameId);
        if (verifyGame == null) {
            throw new RuntimeException("Error");
        }
        for (int i = 0; i < name.length; i++) {
            int playerId = playerDAO.createPlayer(gameId, name[i], color[i]);
            pawnController.createPawns(playerId);
        }
        List<Player> players = playerDAO.getAllPlayers(gameId);
        gameController.startGame(gameId, players.get(0).getPlayerId());
    }

    public List<Player> getAllPlayers(int gameId) throws SQLException {
        return playerDAO.getAllPlayers(gameId);
    }

    public Player getPlayer(int playerId) throws SQLException {
        return playerDAO.getPlayer(playerId);
    }

    public void nextTurn(int gameId, int playerId) throws SQLException {
        Player nextPlayer = playerDAO.getNextPlayer(gameId, playerId);
        if (nextPlayer == null) {
            throw new RuntimeException("Error");
        }
        int nextPlayerId = nextPlayer.getPlayerId();
        gameController.updateCurrentPlayer(gameId, nextPlayerId);
    }

    public void playerPlay(int playerId) throws SQLException {
        Player player = playerDAO.getPlayer(playerId);
        int gameId = player.getGameId();
        PlayerColor color = player.getColor();
        int diceValue = diceController.rollDice();

        if (pawnController.checkAllHomePawns(playerId)) {
            if (diceValue == 6) {
                pawnController.movePawnAuto(playerId, color, diceValue);
                playerPlay(playerId);
            } else {
                nextTurn(gameId, playerId);
            }
        } else if (pawnController.countPawnOnBoard(playerId) == 1) {
            pawnController.movePawnAuto(playerId, color, diceValue);
            nextTurn(gameId, playerId);
        } else {
            pawnController.selectPawnMove(playerId, gameId, color, diceValue);
        }

    }

    public void playerWon(int gameId, int playerId) throws SQLException {
        if (pawnController.checkAllPawnsFinished(playerId)) {
            playerDAO.playerWon(gameId, playerId);
            gameController.endGame(gameId);
        }
    }
}
