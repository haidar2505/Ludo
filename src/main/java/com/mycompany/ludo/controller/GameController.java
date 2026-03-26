/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.dao.GameDAO;
import com.mycompany.ludo.model.Game;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class GameController {
    
    private final GameDAO gameDAO;
    
    public GameController(Connection conn) {
        this.gameDAO = new GameDAO(conn);
    }
    
    public int createGame() throws SQLException {
        return gameDAO.createGame();
    }
    
    public Game findGame(int gameId) throws SQLException {
        return gameDAO.findGame(gameId);
    }
    
    public void startGame(int gameId, int playerId) throws SQLException {
        gameDAO.startGame(gameId, playerId);
    }
    
    public void updateCurrentPlayer(int gameId, int playerId) throws SQLException {
        gameDAO.updateCurrentPlayer(gameId, playerId);
    }
    
    public void endGame(int gameId) throws SQLException {
        gameDAO.endGame(gameId);
    }
}
