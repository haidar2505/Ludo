/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.ludo.controller;

import com.mycompany.ludo.model.PlayerColor;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;
import javax.swing.*;
 
public class LudoBoard extends JPanel {
 
    private final JPanel[][] cells = new JPanel[15][15];
    private BiConsumer<Integer, Integer> pawnClickListener;
 
    public LudoBoard() {
        setLayout(new GridLayout(15, 15));
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                JPanel cell = new JPanel(new GridLayout(1, 1));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                setCellColor(row, col, cell);
                final int r = row, c = col;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (pawnClickListener != null) pawnClickListener.accept(r, c);
                    }
                });
                cells[row][col] = cell;
                add(cell);
            }
        }
    }
 
    public void setPawnClickListener(BiConsumer<Integer, Integer> listener) {
        this.pawnClickListener = listener;
    }
 
    private void setCellColor(int r, int c, JPanel cell) {
        if (r < 6 && c < 6)   { cell.setBackground(new Color(255, 80,  80));  return; }
        if (r < 6 && c > 8)   { cell.setBackground(new Color(80,  200, 80));  return; }
        if (r > 8 && c < 6)   { cell.setBackground(new Color(255, 255, 100)); return; }
        if (r > 8 && c > 8)   { cell.setBackground(new Color(100, 150, 255)); return; }
        // Home stretches
        if (r == 7 && c >= 1 && c <= 5) { cell.setBackground(new Color(255, 160, 160)); return; }
        if (c == 7 && r >= 1 && r <= 5) { cell.setBackground(new Color(160, 160, 255)); return; }
        if (r == 7 && c >= 9 && c <= 13){ cell.setBackground(new Color(255, 255, 160)); return; }
        if (c == 7 && r >= 9 && r <= 13){ cell.setBackground(new Color(160, 230, 160)); return; }
        // Center
        if (r == 7 && c == 7) { cell.setBackground(new Color(220, 220, 220)); return; }
        cell.setBackground(Color.WHITE);
    }
 
    public void initializePawn(int row, int col, PlayerColor color) {
        JPanel cell = cells[row][col];
        cell.removeAll();
        JLabel token = new JLabel("●", SwingConstants.CENTER);
        token.setForeground(toAwtColor(color));
        token.setFont(new Font("Arial", Font.BOLD, 36));
        cell.add(token);
        cell.revalidate();
        cell.repaint();
    }
 
    public void removePawn(int row, int col) {
        cells[row][col].removeAll();
        cells[row][col].revalidate();
        cells[row][col].repaint();
    }
 
    public void highlightCell(int row, int col, boolean on) {
        cells[row][col].setBorder(BorderFactory.createLineBorder(
            on ? Color.ORANGE : Color.BLACK, on ? 3 : 1));
        cells[row][col].repaint();
    }
 
    private Color toAwtColor(PlayerColor color) {
        switch (color) {
            case RED:    return new Color(200, 0,   0);
            case BLUE:   return new Color(0,   0,   200);
            case YELLOW: return new Color(200, 160, 0);
            case GREEN:  return new Color(0,   160, 0);
            default:     return Color.GRAY;
        }
    }
 
    public static void runBoard() {
        JFrame frame = new JFrame("Ludo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new LudoBoard());
        frame.setVisible(true);
    }
}
