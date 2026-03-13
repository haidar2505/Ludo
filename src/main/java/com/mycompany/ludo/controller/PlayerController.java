/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.model.Color;
import com.mycompany.ludo.model.Player;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PlayerController {


    private PlayerDAO daoPlayer;
    private GameController cGame;
    private PawnController cPawn;

    public PlayerController(Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.cGame = new GameController(c);
        this.cPawn = new PawnController(c);
    }

    public void createPlayer() throws SQLException {
        int numberOfPlayers = cGame.numberOfPlayers();
        System.out.println(numberOfPlayers);
        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

        for (int i = 0; i < numberOfPlayers; i++) {
            int playerId = daoPlayer.createPlayer("Player" + (i + 1), colors[i]);
            if(playerId != 0){
                for(int j=0; j<4; j++){
                    cPawn.createPawn(playerId);
                }
            }
        }
//        cGame.startGame();
    
    }
}
