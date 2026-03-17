/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ludo;

import com.mycompany.ludo.DAO.GameDAO;
import com.mycompany.ludo.DAO.PawnDAO;
import com.mycompany.ludo.DAO.PlayerDAO;
import com.mycompany.ludo.connection.Connectivity;
import com.mycompany.ludo.controller.GameScreen;
import com.mycompany.ludo.controller.LudoBoard;
import com.mycompany.ludo.controller.PawnController;
import com.mycompany.ludo.controller.PlayerController;
import com.mycompany.ludo.model.Game;
import com.mycompany.ludo.model.Pawn;
import com.mycompany.ludo.model.Player;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Haidar
 */
public class Ludo {

    private PlayerDAO daoPlayer;
    private GameDAO daoGame;
    private PlayerController playerController;
    private PawnController panwController;
    private PawnDAO daoPawn;
    private LudoBoard LBoard;

    public Ludo(Connection c) {
        this.daoPlayer = new PlayerDAO(c);
        this.daoPawn = new PawnDAO(c);
        this.daoGame = new GameDAO(c);
        this.playerController = new PlayerController(c);
        this.panwController = new PawnController(c);
        this.LBoard = new LudoBoard();
    }

    public void run() throws SQLException {
        // Clean previous game data
        daoPlayer.deletePlayers();  // cascades to pawns

        // Create players and pawns
        playerController.createPlayer();

        // Load everything and launch game screen
        List<Player> players = playerController.getPlayers();
        List<Pawn> pawns = panwController.loadPawns();

        SwingUtilities.invokeLater(() -> {
            new GameScreen(playerController, panwController, players, pawns);
        });
    }
    

    public static void main(String[] args) throws SQLException {
        Connection conn = Connectivity.getConnection();
        Ludo ludo = new Ludo(conn);
        ludo.run();
    }
}
