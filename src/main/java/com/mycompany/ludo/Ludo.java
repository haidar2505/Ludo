/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ludo;

import com.mycompany.ludo.DAO.PawnDAO;
import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.connection.Connectivity;
import com.mycompany.ludo.controller.LudoBoard;
import com.mycompany.ludo.controller.PlayerController;
import com.mycompany.ludo.model.Player;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Haidar
 */
public class Ludo {

    private PlayerDAO daoPlayer;
    private PlayerController cPlayer;
    private PawnDAO daoPawn;
    private LudoBoard LBoard;

    public Ludo(Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.daoPawn = new PawnDAO(c);
        this.cPlayer = new PlayerController(c);
        this.LBoard = new LudoBoard();
    }

    public void run() throws SQLException {
        cPlayer.createPlayer();
//        daoPawn.deleteAllPawn();
//        daoPlayer.deletePlayers();
    }

    public static void main(String[] args) throws SQLException {
//        JFrame frame = new JFrame("Ludo");
//        frame.setSize(400, 500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setFocusable(true);
//        frame.setVisible(true);
        Connection conn = Connectivity.getConnection();
        Ludo ludo = new Ludo(conn);
//        ludo.run();
        LudoBoard.runBoard();
//        daoPlayer.switchPlayer(2);
    }
}
