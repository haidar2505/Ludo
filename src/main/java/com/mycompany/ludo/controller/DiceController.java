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

    public int rollDice() {

        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
