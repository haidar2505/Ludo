/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.model;

import org.postgresql.geometric.PGpoint;

/**
 *
 * @author Haidar
 */
public class Pawn {

    private int pawnId;
    private int playerId;
    private PGpoint position;
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

    public PGpoint getPosition() {
        return position;
    }

    public void setPosition(PGpoint position) {
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
