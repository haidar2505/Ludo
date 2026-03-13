/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.GameDAO;
import com.mycompany.ludo.model.Game;
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
    Game game = new Game();

    public GameController(Connection c) {
        this.gameDAO = new GameDAO(c);
    }

    public int numberOfPlayers() {
        game.setNumberOfPlayers(2);
        return game.getNumberOfPlayers();
    }

    public void startGame() throws SQLException {
        gameDAO.createGame();
    }
    
    public void endGame(Game game) throws SQLException {
        gameDAO.endGame(game);
    }
}
