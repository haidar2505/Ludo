    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Haidar
 */
public class LudoBoard extends JPanel {
    
    private final JPanel[][] cells = new JPanel[15][15];
 
    public LudoBoard() {
        setLayout(new GridLayout(15, 15));
 
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
 
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
 
                // COLORING BOARD ZONES
                setCellColor(row, col, cell);
 
                final int x = row;
                final int y = col;
 
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("Cell " + x + "," + y);
                    }
                });
 
                cells[row][col] = cell;
                add(cell);
            }
        }
 
        placeStartingTokens();
    }
 
    private void setCellColor(int r, int c, JPanel cell) {
 
        // RED HOME
        if (r < 6 && c < 6) {
            cell.setBackground(new Color(255, 80, 80));
            return;
        }
        // GREEN HOME
        if (r < 6 && c > 8) {
            cell.setBackground(new Color(80, 255, 80));
            return;
        }
        // YELLOW HOME
        if (r > 8 && c < 6) {
            cell.setBackground(new Color(255, 255, 120));
            return;
        }
        // BLUE HOME
        if (r > 8 && c > 8) {
            cell.setBackground(new Color(120, 160, 255));
            return;
        }
 
        // NORMAL PATH CELLS
        cell.setBackground(Color.WHITE);
    }
 
    private void placeStartingTokens() {
 
        // Four tokens per color
        addToken(1, 1, Color.RED);
        addToken(1, 4, Color.RED);
        addToken(4, 1, Color.RED);
        addToken(4, 4, Color.RED);
 
        addToken(1, 10, Color.GREEN);
        addToken(1, 13, Color.GREEN);
        addToken(4, 10, Color.GREEN);
        addToken(4, 13, Color.GREEN);
 
        addToken(10, 1, Color.YELLOW);
        addToken(10, 4, Color.YELLOW);
        addToken(13, 1, Color.YELLOW);
        addToken(13, 4, Color.YELLOW);
 
        addToken(10, 10, Color.BLUE);
        addToken(10, 13, Color.BLUE);
        addToken(13, 10, Color.BLUE);
        addToken(13, 13, Color.BLUE);
    }
 
    private void addToken(int row, int col, Color color) {
        JLabel token = new JLabel("●");
        token.setForeground(color);
        token.setFont(new Font("Arial", Font.BOLD, 40));
        token.setHorizontalAlignment(SwingConstants.CENTER);
        cells[row][col].setLayout(new GridLayout(1, 1));
        cells[row][col].add(token);
        cells[row][col].revalidate();
        
    }
 
    // MAIN FOR TESTING
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ludo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new LudoBoard());
        frame.setVisible(true);
    }
}
