/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.GameDAO;
import com.mycompany.ludo.model.Player;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class GameController {

    private GameDAO gameDAO;
    Player player = new Player();

    public GameController(Connection c) {
        this.gameDAO = new GameDAO(c);
    }

    public void numberOfPlayers(int players) {
        player.setNumberOfPlayers(players);
    }

    public void startGame() throws SQLException {
        gameDAO.createGame();
    }
    
    public void endGame(int gameId, int winnerId) throws SQLException {
        gameDAO.endGame(gameId, winnerId);
    }
}
