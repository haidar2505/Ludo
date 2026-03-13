/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ludo;

import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.connection.Connectivity;
import com.mycompany.ludo.controller.PlayerController;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Haidar
 */
public class Ludo {

    private PlayerDAO daoPlayer;
    private PlayerController cPlayer;

    public Ludo(Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.cPlayer = new PlayerController(c);
    }

    public void run() throws SQLException {
        cPlayer.createPlayer();
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
        ludo.run();
//        daoPlayer.switchPlayer(2);
    }
}
