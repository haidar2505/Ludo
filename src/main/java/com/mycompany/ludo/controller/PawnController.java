/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.DAO.PawnDAO;
import com.mycompany.ludo.model.Pawn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
 
public class PawnController {
 
    private PawnDAO daoPawn;
    private LudoBoard board;
 
    public PawnController(Connection c) {
        this.daoPawn = new PawnDAO(c);
        this.board = new LudoBoard();
    }
 
    public LudoBoard getBoard() {
        return board;
    }
 
    public void createPawn(int playerId, int row, int col) throws SQLException {
        daoPawn.createPawn(playerId, row, col);
    }
 
    public List<Pawn> loadPawns() throws SQLException {
        List<Pawn> pawns = daoPawn.getAllPawn();
        for (Pawn pawn : pawns) {
            board.initializePawn(pawn.getRow(), pawn.getCol(), pawn.getColor());
        }
        return pawns;
    }
 
    public void updatePawn(Pawn pawn) throws SQLException {
        daoPawn.updatePawn(pawn);
    }
}
 