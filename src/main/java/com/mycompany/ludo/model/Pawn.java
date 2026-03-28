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
    private int homePosition;
    private boolean isHome;
    private boolean isFinished;

    public Pawn() {
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

    public int getHomePosition() {
        return homePosition;
    }

    public void setHomePosition(int homePosition) {
        this.homePosition = homePosition;
    }

    public boolean getIsHome() {
        return isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

}
