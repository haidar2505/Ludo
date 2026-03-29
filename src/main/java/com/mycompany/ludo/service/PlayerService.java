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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PlayerService {

    private final PlayerDAO playerDAO;
    private GameService gameService;
    private DiceService diceService;
    private PawnService pawnService;
    private List<Player> numberOfPlayers = new ArrayList<>();

    public List<Player> getPlayers() {
        return numberOfPlayers;
    }
    
    public PlayerService(Connection conn, GameService gameService, DiceService diceService, PawnService pawnService) {
        this.playerDAO = new PlayerDAO(conn);
        this.gameService = gameService;
        this.diceService = diceService;
        this.pawnService = pawnService;
    }

    public int createPlayer(int gameId, String name, PlayerColor color) throws SQLException {
        return playerDAO.createPlayer(gameId, name, color);
    }

    public List<Player> getAllPlayers(int gameId) throws SQLException {
        return playerDAO.getAllPlayers(gameId);
    }

    public Player getPlayer(int playerId) throws SQLException {
        return playerDAO.getPlayer(playerId);
    }

    public Player getNextPlayer(int gameId, int playerId) throws SQLException {
        return playerDAO.getNextPlayer(gameId, playerId);
    }

    public boolean checkPlayerWon(int gameId, int playerId) throws SQLException {
        if (pawnService.checkFinishedPawns(playerId)) {
            playerDAO.playerWon(gameId, playerId);
            gameService.endGame(gameId);
            return true;
        }
        return false;
    }

    public void initializePlayer(int gameId, int numberOfPlayers) throws SQLException {
        Game verifyGame = gameService.findGame(gameId);
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.GREEN};
        if (verifyGame == null) {
            throw new IllegalArgumentException("Game : " + gameId + " not found");
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println(numberOfPlayers);
            int playerId = createPlayer(gameId, "Player"+(i+1), colors[i]);
            pawnService.createPawns(playerId);
        }
        List<Player> players = getAllPlayers(gameId);
        gameService.startGame(gameId, players.get(0).getPlayerId());
    }

    public void nextTurn(int gameId, int playerId) throws SQLException {
        Player nextPlayer = getNextPlayer(gameId, playerId);
        if (nextPlayer == null) {
            throw new IllegalArgumentException("Next Player not found");
        }
        int nextPlayerId = nextPlayer.getPlayerId();
        gameService.updateCurrentPlayer(gameId, nextPlayerId);
    }

    public void playerTurn(int playerId) throws SQLException {
        Player player = getPlayer(playerId);
        int gameId = player.getGameId();
        PlayerColor color = player.getColor();

        boolean rolledSix;

        do {
            int numberRolled = diceService.rollDice();
            rolledSix = numberRolled == 6;
            boolean turnDone = pawnService.handleTurn(gameId, playerId, color, numberRolled);

            if (turnDone) {
                if (checkPlayerWon(gameId, playerId)) {
                    return;
                }
            }
        } while (rolledSix);

        nextTurn(gameId, playerId);
    }
}
