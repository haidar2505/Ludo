/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PawnDAO;
import com.mycompany.ludo.model.PlayerColor;
import com.mycompany.ludo.model.Pawn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Haidar
 */
public class PawnController {
    
    private PawnDAO daoPawn;
    private LudoBoard Board;
    
    public PawnController(Connection c){
        this.daoPawn = new PawnDAO(c);
        this.Board = new LudoBoard();
    }
            
    public void createPawn(int playerId, int row, int col) throws SQLException{
        daoPawn.createPawn(playerId, row, col);
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.GREEN};
        List<Pawn> pawn = daoPawn.getAllPawn();
        for(int i =0; i<pawn.size(); i++){
            Board.initializePawn(pawn.get(i).getRow(), pawn.get(i).getCol(), pawn.get(i).getColor());
        }
    }
    
    public void movePawn(int numberRolled){
        
    }
    
    public void verifyPawn(){
        
    }
}
