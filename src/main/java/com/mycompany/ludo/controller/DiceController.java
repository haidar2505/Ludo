/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import java.util.Random;

/**
 *
 * @author Haidar
 */
public class DiceController {
    
    private int value;
    Random random = new Random();

    public int rollDice() {
        int diceValue = random.nextInt(6) + 1;
        this.value = diceValue;
        return diceValue;
    }
    
    public int getNumberRolled(){
        return value; 
    }
    
}
