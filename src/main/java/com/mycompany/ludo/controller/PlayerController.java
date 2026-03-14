/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.model.Color;
import java.sql.Connection;
import java.sql.SQLException;
import org.postgresql.geometric.PGpoint;

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
        
        PGpoint[][] startingPositions = {
            {new PGpoint(1,1), new PGpoint(1,4), new PGpoint(4,1), new PGpoint(4,4)},
            {new PGpoint(1,10), new PGpoint(1,13), new PGpoint(4,10), new PGpoint(4,13)},
            {new PGpoint(10,1), new PGpoint(10,4), new PGpoint(13,1), new PGpoint(13,4)},
            {new PGpoint(10,10), new PGpoint(10,13), new PGpoint(13,10), new PGpoint(13,13)}
        };
        
        for (int i = 0; i < numberOfPlayers; i++) {
            int playerId = daoPlayer.createPlayer("Player" + (i + 1), colors[i]);
            if(playerId != 0){
                for(int j=0; j<4; j++){
                    cPawn.createPawn(playerId, startingPositions[i][j]);
                }
            }
        }
        cGame.startGame();
    }
}
