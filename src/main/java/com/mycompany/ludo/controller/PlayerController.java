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

/**
 *
 * @author Haidar
 */
public class PlayerController {
    
    Player player = new Player();
    
    private PlayerDAO daoPlayer;
    private GameController cGame;

    public PlayerController (Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.cGame = new GameController(c);
    }
    
    public void createPlayer(Color color) throws SQLException{
        int numberOfPlayers = player.getNumberOfPlayers();
        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        // Not all player will play (2 or 3 player can play)
        for(int i=0; i < numberOfPlayers; i++){
            player.setName("Player"+i+1);
            // Change functionality later player will chose the color
            player.setColor(colors[i]);
            daoPlayer.createPlayer(player.getName(), player.getColor(), true);
        }
//        cGame.startGame();
    }
}
