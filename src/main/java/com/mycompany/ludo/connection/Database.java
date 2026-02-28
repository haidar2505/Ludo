/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.connection;

/**
 *
 * @author Haidar
 */
public class Database {
    private String userName = "postgres";
    private String passWord = "postgres2505";
    private String connectionUrl = "jdbc:postgresql://localhost:5432/Ludo";

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }
}
