/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Haidar
 */
public class Connectivity {

    public static Connection getConnection() {

        Connection connection = null;
        Database db = new Database();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(db.getConnectionUrl(), db.getUserName(), db.getPassWord());
            System.out.println("Database connected = " + connection);
        } catch (Exception e) {
            System.out.println("Erreur connexion DB = " + e.getMessage());
        }
        return connection;
    }
}
