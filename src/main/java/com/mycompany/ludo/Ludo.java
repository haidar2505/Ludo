/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ludo;

import com.mycompany.ludo.IU.PlayerSelectUI;
import com.mycompany.ludo.connection.Connectivity;
import com.mycompany.ludo.dao.PlayerDAO;
import com.mycompany.ludo.service.DiceService;
import com.mycompany.ludo.service.GameService;
import com.mycompany.ludo.service.PawnService;
import com.mycompany.ludo.service.PlayerService;
import java.sql.Connection;

/**
 *
 * @author Haidar
 */
public class Ludo {

    public static void main(String[] args) {
        // Instances
        // Database
        Connection conn = Connectivity.getConnection();
        
        // DAO
        PlayerDAO playerDAO = new PlayerDAO(conn);

        // Services
        GameService gameService = new GameService(conn);
        DiceService diceService = new DiceService();
        PawnService pawnService = new PawnService(conn);

        PlayerService playerService = new PlayerService(playerDAO, gameService, diceService, pawnService);

        // START
        PlayerSelectUI playerSelectUI = new PlayerSelectUI(gameService, playerService);
        playerSelectUI.setVisible(true);
    }
}
