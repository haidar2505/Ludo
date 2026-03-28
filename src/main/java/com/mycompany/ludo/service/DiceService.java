/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.service;

import java.util.Random;

/**
 *
 * @author Haidar
 */
public class DiceService {

    Random random = new Random();
    private int value = 0;

    public int rollDice() {
        this.value = random.nextInt(6) + 1;
        return this.value;
    }

    public int getNumberRolled() {
        return value;
    }
}
