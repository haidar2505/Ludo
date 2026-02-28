/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.model;

/**
 *
 * @author Haidar
 */
public class Pawn {

    private int pawnId;
    private int playerId;
    private int position;
    private boolean isHome;
    private boolean isFinished;

    public Pawn(int pawnId, int playerId, int position, boolean isHome, boolean isFinished) {
        this.pawnId = pawnId;
        this.playerId = playerId;
        this.position = position;
        this.isHome = isHome;
        this.isFinished = isFinished;
    }

    public int getPawnId() {
        return pawnId;
    }

    public void setPawnId(int pawnId) {
        this.pawnId = pawnId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIsHome() {
        return isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

}
