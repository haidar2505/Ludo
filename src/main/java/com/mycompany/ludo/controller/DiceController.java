/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import com.mycompany.ludo.model.Dice;
import java.util.Random;

/**
 *
 * @author Haidar
 */
public class DiceController {

    Dice dice = new Dice();
    Random random = new Random();

    public int rollDice() {
        int min = 1;
        int max = 6;
        int result = random.nextInt(max - min + 1) + min;
        dice.setNumberRolled(result);
        return result;
    }
}
