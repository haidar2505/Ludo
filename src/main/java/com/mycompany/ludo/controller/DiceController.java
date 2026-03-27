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
    
    Random random = new Random();

    public int rollDice() {
        return random.nextInt(6) + 1;
    }
    
}
