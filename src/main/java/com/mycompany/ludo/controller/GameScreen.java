/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ludo.controller;

/**
 *
 * @author Haidar
 */
import com.mycompany.ludo.model.Pawn;
import com.mycompany.ludo.model.Player;
import com.mycompany.ludo.model.PlayerColor;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
 
public class GameScreen extends JFrame {
 
    private static final int[][] PATH = {
        {6,1},{6,2},{6,3},{6,4},{6,5},           
        {5,6},{4,6},{3,6},{2,6},{1,6},{0,6},      
        {0,7},                                     
        {0,8},{1,8},{2,8},{3,8},{4,8},{5,8},      
        {6,9},{6,10},{6,11},{6,12},{6,13},{6,14}, 
        {7,14},                                    
        {8,14},{8,13},{8,12},{8,11},{8,10},{8,9}, 
        {9,8},{10,8},{11,8},{12,8},{13,8},{14,8}, 
        {14,7},                                    
        {14,6},{13,6},{12,6},{11,6},{10,6},{9,6}, 
        {8,5},{8,4},{8,3},{8,2},{8,1},{8,0},      
        {7,0}                                      
    };
 
    private static final int[] ENTRY_INDEX = {0, 13, 26, 39};
 
    private static final int[][][] HOME_STRETCH = {
        {{7,1},{7,2},{7,3},{7,4},{7,5},{7,6}},    
        {{1,7},{2,7},{3,7},{4,7},{5,7},{6,7}},    
        {{7,13},{7,12},{7,11},{7,10},{7,9},{7,8}}, 
        {{13,7},{12,7},{11,7},{10,7},{9,7},{8,7}}  
    };
 
    private final DiceController diceCtrl = new DiceController();
    private final PawnController pawnCtrl;
    private final PlayerController playerCtrl;
 
    private List<Player> players;
    private List<Pawn> pawns;
    private int currentPlayerIndex = 0;
    private int lastRoll = 0;
    private boolean diceRolled = false;
 
    private LudoBoard board;
    private JLabel diceLabel;
    private JButton rollBtn;
    private JLabel turnLabel;
    private JLabel statusLabel;
 
    public GameScreen(PlayerController playerCtrl, PawnController pawnCtrl,
                      List<Player> players, List<Pawn> pawns) {
        this.playerCtrl = playerCtrl;
        this.pawnCtrl   = pawnCtrl;
        this.players    = players;
        this.pawns      = pawns;
 
        setTitle("Ludo Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        buildUI();
        pack();
        setLocationRelativeTo(null);
        refreshTurnUI();
        setVisible(true);
    }
 
    private void buildUI() {
        getContentPane().setBackground(new Color(20, 20, 50));
        setLayout(new BorderLayout(8, 8));
 
        board = pawnCtrl.getBoard();
        board.setPreferredSize(new Dimension(630, 630));
        board.setPawnClickListener(this::onCellClicked);
        add(board, BorderLayout.CENTER);
 
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(new Color(20, 20, 50));
        side.setPreferredSize(new Dimension(190, 630));
        side.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
 
        // Turn
        turnLabel = new JLabel("", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial Black", Font.BOLD, 17));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(turnLabel);
        side.add(Box.createVerticalStrut(6));
 
        JLabel turnSub = new JLabel("is playing", SwingConstants.CENTER);
        turnSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        turnSub.setForeground(new Color(180, 180, 210));
        turnSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(turnSub);
        side.add(Box.createVerticalStrut(20));
 
        // Dice face
        diceLabel = new JLabel("?", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 14, 14);
                g2.setColor(new Color(180,180,180));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, 14, 14);
                super.paintComponent(g);
            }
        };
        diceLabel.setFont(new Font("Arial Black", Font.BOLD, 52));
        diceLabel.setForeground(new Color(30, 30, 30));
        diceLabel.setMaximumSize(new Dimension(90, 90));
        diceLabel.setPreferredSize(new Dimension(90, 90));
        diceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        diceLabel.setOpaque(false);
        side.add(diceLabel);
        side.add(Box.createVerticalStrut(14));
 
        // Roll button
        rollBtn = new JButton("Roll Dice");
        rollBtn.setFont(new Font("Arial Black", Font.BOLD, 13));
        rollBtn.setFocusPainted(false);
        rollBtn.setBorderPainted(false);
        rollBtn.setForeground(Color.WHITE);
        rollBtn.setBackground(new Color(50, 180, 80));
        rollBtn.setMaximumSize(new Dimension(150, 44));
        rollBtn.setPreferredSize(new Dimension(150, 44));
        rollBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rollBtn.addActionListener(e -> onRollDice());
        side.add(rollBtn);
        side.add(Box.createVerticalStrut(18));
 
        // Status
        statusLabel = new JLabel("<html><center>Press Roll<br>to play!</center></html>", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(200, 200, 230));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(statusLabel);
        side.add(Box.createVerticalStrut(24));
 
        // Player list
        JLabel playersTitle = new JLabel("── Players ──", SwingConstants.CENTER);
        playersTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        playersTitle.setForeground(new Color(150, 150, 180));
        playersTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(playersTitle);
        side.add(Box.createVerticalStrut(8));
 
        for (Player p : players) {
            JLabel pl = new JLabel("● " + p.getName(), SwingConstants.CENTER);
            pl.setFont(new Font("SansSerif", Font.BOLD, 13));
            pl.setForeground(toAwtColor(p.getColor()));
            pl.setAlignmentX(Component.CENTER_ALIGNMENT);
            side.add(pl);
            side.add(Box.createVerticalStrut(4));
        }
 
        add(side, BorderLayout.EAST);
    }
 
    // Roll dice
    private void onRollDice() {
        if (diceRolled) {
            setStatus("Already rolled!<br>Select a pawn.");
            return;
        }
        lastRoll = diceCtrl.rollDice();
        diceLabel.setText(String.valueOf(lastRoll));
        diceRolled = true;
        rollBtn.setEnabled(false);
 
        Player current = players.get(currentPlayerIndex);
        List<Pawn> movable = getMovablePawns(current);
 
        if (movable.isEmpty()) {
            setStatus("No moves!<br>Skipping turn.");
            Timer t = new Timer(1200, e -> nextTurn());
            t.setRepeats(false);
            t.start();
        } else if (movable.size() == 1) {
            setStatus("Auto-moving pawn...");
            Timer t = new Timer(600, e -> movePawn(movable.get(0)));
            t.setRepeats(false);
            t.start();
        } else {
            setStatus("Click a pawn<br>to move!");
            for (Pawn p : movable) board.highlightCell(p.getRow(), p.getCol(), true);
        }
    }
 
    private void onCellClicked(int row, int col) {
        if (!diceRolled) return;
        Player current = players.get(currentPlayerIndex);
        List<Pawn> movable = getMovablePawns(current);
        if (movable.size() <= 1) return;
 
        for (Pawn p : movable) {
            if (p.getRow() == row && p.getCol() == col) {
                clearHighlights();
                movePawn(p);
                return;
            }
        }
    }
 
    private List<Pawn> getMovablePawns(Player player) {
        List<Pawn> movable = new ArrayList<>();
        for (Pawn p : pawns) {
            if (p.getPlayerId() == player.getPlayerId() && !p.getIsFinished()) {
                if (p.getIsHome() && lastRoll == 6) movable.add(p);
                else if (!p.getIsHome()) movable.add(p);
            }
        }
        return movable;
    }
 
    // Move a pawn
    private void movePawn(Pawn pawn) {
        int ci = colorIndex(pawn.getColor());
        int oldRow = pawn.getRow();
        int oldCol = pawn.getCol();
        int newRow, newCol;
 
        if (pawn.getIsHome()) {
            int[] entry = PATH[ENTRY_INDEX[ci]];
            newRow = entry[0];
            newCol = entry[1];
            pawn.setIsHome(false);
            pawn.setPathPosition(0);
        } else {
            int newPos = pawn.getPathPosition() + lastRoll;
 
            if (newPos > 57) {
                setStatus("Can't move,<br>roll exact!");
                diceRolled = false;
                rollBtn.setEnabled(true);
                return;
            } else if (newPos == 57) {
                pawn.setIsFinished(true);
                board.removePawn(oldRow, oldCol);
                board.initializePawn(7, 7, pawn.getColor());
                pawn.setRow(7); pawn.setCol(7);
                savePawn(pawn);
                setStatus(players.get(currentPlayerIndex).getName() + "<br>finished a pawn!");
                checkWinner();
                if (lastRoll == 6) bonusTurn(); else nextTurn();
                return;
            } else if (newPos >= 52) {
                int[] cell = HOME_STRETCH[ci][newPos - 52];
                newRow = cell[0]; newCol = cell[1];
            } else {
                int[] cell = PATH[(ENTRY_INDEX[ci] + newPos) % 52];
                newRow = cell[0]; newCol = cell[1];
            }
            pawn.setPathPosition(newPos);
        }
 
        checkCapture(newRow, newCol, pawn);
 
        board.removePawn(oldRow, oldCol);
        pawn.setRow(newRow); pawn.setCol(newCol);
        board.initializePawn(newRow, newCol, pawn.getColor());
        savePawn(pawn);
 
        if (lastRoll == 6) bonusTurn();
        else nextTurn();
    }
 
    // Capture enemy pawn
    private void checkCapture(int row, int col, Pawn mover) {
        for (Pawn p : pawns) {
            if (p.getPawnId() == mover.getPawnId()) continue;
            if (p.getPlayerId() == mover.getPlayerId()) continue;
            if (p.getRow() == row && p.getCol() == col && !p.getIsHome() && !p.getIsFinished()) {
                int ci = colorIndex(p.getColor());
                int[][][] sp = playerCtrl.getStartingPositions();
                int pawnIndex = getPawnIndexForPlayer(p);
                int homeRow = sp[ci][pawnIndex][0];
                int homeCol = sp[ci][pawnIndex][1];
 
                board.removePawn(p.getRow(), p.getCol());
                p.setRow(homeRow); p.setCol(homeCol);
                p.setIsHome(true);
                p.setPathPosition(0);
                board.initializePawn(homeRow, homeCol, p.getColor());
                savePawn(p);
                setStatus("Captured!<br>Enemy sent home.");
            }
        }
    }
 
    private int getPawnIndexForPlayer(Pawn target) {
        int idx = 0;
        for (Pawn p : pawns) {
            if (p.getPlayerId() == target.getPlayerId()) {
                if (p.getPawnId() == target.getPawnId()) return idx;
                idx++;
            }
        }
        return 0;
    }
 
    private void bonusTurn() {
        diceRolled = false;
        lastRoll = 0;
        diceLabel.setText("?");
        rollBtn.setEnabled(true);
        setStatus("Rolled 6!<br>Roll again!");
    }
 
    private void nextTurn() {
        clearHighlights();
        diceRolled = false;
        lastRoll = 0;
        diceLabel.setText("?");
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        rollBtn.setEnabled(true);
        refreshTurnUI();
    }
 
    private void checkWinner() {
        Player current = players.get(currentPlayerIndex);
        boolean won = pawns.stream()
            .filter(p -> p.getPlayerId() == current.getPlayerId())
            .allMatch(Pawn::getIsFinished);
        if (won) {
            JOptionPane.showMessageDialog(this,
                "🎉 " + current.getName() + " wins the game!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
 
    private void clearHighlights() {
        for (Pawn p : pawns) board.highlightCell(p.getRow(), p.getCol(), false);
    }
 
    private void savePawn(Pawn pawn) {
        try { pawnCtrl.updatePawn(pawn); }
        catch (SQLException ex) { ex.printStackTrace(); }
    }
 
    private void refreshTurnUI() {
        Player current = players.get(currentPlayerIndex);
        Color c = toAwtColor(current.getColor());
        turnLabel.setText(current.getName());
        turnLabel.setForeground(c);
        rollBtn.setBackground(c);
        setStatus("Press Roll<br>to play!");
    }
 
    private void setStatus(String html) {
        statusLabel.setText("<html><center>" + html + "</center></html>");
    }
 
    private int colorIndex(PlayerColor color) {
        switch (color) {
            case RED:    return 0;
            case BLUE:   return 1;
            case YELLOW: return 2;
            case GREEN:  return 3;
            default:     return 0;
        }
    }
 
    private Color toAwtColor(PlayerColor color) {
        switch (color) {
            case RED:    return new Color(210, 40,  40);
            case BLUE:   return new Color(40,  80,  210);
            case YELLOW: return new Color(200, 160, 0);
            case GREEN:  return new Color(30,  150, 30);
            default:     return Color.GRAY;
        }
    }
}