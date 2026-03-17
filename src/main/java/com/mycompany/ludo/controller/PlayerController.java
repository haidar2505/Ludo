/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.model.Player;
import com.mycompany.ludo.model.PlayerColor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
 
public class PlayerController {
 
    private PlayerDAO daoPlayer;
    private GameController cGame;
    private PawnController cPawn;
 
    public PlayerController(Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.cGame = new GameController(c);
        this.cPawn = new PawnController(c);
    }
 
    public PawnController getPawnController() {
        return cPawn;
    }
 
    public void createPlayer() throws SQLException {
        int numberOfPlayers = cGame.numberOfPlayers();
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.GREEN};
 
        int[][][] startingPositions = {
            {{1,1}, {1,4}, {4,1}, {4,4}},
            {{1,10}, {1,13}, {4,10}, {4,13}},
            {{10,1}, {10,4}, {13,1}, {13,4}},
            {{10,10}, {10,13}, {13,10}, {13,13}}
        };
 
        for (int i = 0; i < numberOfPlayers; i++) {
            int playerId = daoPlayer.createPlayer("Player" + (i + 1), colors[i]);
            if (playerId != 0) {
                for (int j = 0; j < 4; j++) {
                    cPawn.createPawn(playerId, startingPositions[i][j][0], startingPositions[i][j][1]);
                }
            }
        }
        cGame.startGame();
    }
 
    public List<Player> getPlayers() throws SQLException {
        return daoPlayer.getAllPlayer();
    }
 
    public int[][][] getStartingPositions() {
        return new int[][][] {
            {{1,1}, {1,4}, {4,1}, {4,4}},
            {{1,10}, {1,13}, {4,10}, {4,13}},
            {{10,1}, {10,4}, {13,1}, {13,4}},
            {{10,10}, {10,13}, {13,10}, {13,13}}
        };
    }
}