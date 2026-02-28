/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.model;

import java.time.LocalDateTime;

/**
 *
 * @author Haidar
 */
public class Game {

    private int gameId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int winnerId;

    public Game(int gameId, LocalDateTime startTime, LocalDateTime endTime, int winnerId) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.winnerId = winnerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

}
