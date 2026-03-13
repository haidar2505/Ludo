/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PawnDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Haidar
 */
public class PawnController {
    
    private PawnDAO daoPawn;
    
    public PawnController(Connection c){
        this.daoPawn = new PawnDAO(c);
    }
        
    public void createPawn(int playerId) throws SQLException{
        daoPawn.createPawn(playerId);
    }
    
    public void movePawn(int numberRolled){
        
    }
}
