package com.mycompany.ludo.IU;

import com.mycompany.ludo.service.GameService;
import com.mycompany.ludo.service.PlayerService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class PlayerSelectUI extends JFrame {

    private final GameService gameService;
    private final PlayerService playerService;

    public PlayerSelectUI(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;

        setTitle("Ludo");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new MainPanel());
    }

    class MainPanel extends JPanel {

        Color BG = new Color(18, 15, 40);
        Color CARD = new Color(45, 40, 80);
        Color CARD_HOVER = new Color(65, 60, 110);

        Font titleFont = new Font("SansSerif", Font.BOLD, 50);
        Font subtitleFont = new Font("SansSerif", Font.PLAIN, 15);
        Font textFont = new Font("SansSerif", Font.PLAIN, 17);

        public MainPanel() {
            setLayout(null);
            setBackground(BG);

            JLabel title = new JLabel("LUDO", JLabel.CENTER);
            title.setBounds(0, 40, 1280, 60);
            title.setFont(titleFont);
            title.setForeground(Color.WHITE);
            add(title);

            JLabel subtitle = new JLabel("CLASSIC BOARD GAME", JLabel.CENTER);
            subtitle.setBounds(0, 115, 1280, 16);
            subtitle.setFont(subtitleFont);
            subtitle.setForeground(new Color(180, 180, 200));
            add(subtitle);

            JLabel select = new JLabel("SELECT NUMBER OF PLAYERS", JLabel.CENTER);
            select.setBounds(0, 160, 1280, 30);
            select.setFont(textFont);
            select.setForeground(new Color(160, 160, 190));
            add(select);

            add(buildCard(2, new String[]{"pawn_red.png", "pawn_blue.png"}, 240));
            add(buildCard(3, new String[]{"pawn_red.png", "pawn_blue.png", "pawn_yellow.png"}, 360));
            add(buildCard(4, new String[]{"pawn_red.png", "pawn_blue.png", "pawn_yellow.png", "pawn_green.png"}, 480));
            
            add(linedPawns(new String[]{"pawn_red.png", "pawn_blue.png", "pawn_yellow.png", "pawn_green.png"}));
        }

        // Card
        private JPanel buildCard(int numberOfPlayers, String[] images, int y) {

            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                }
            };

            card.setLayout(null);
            card.setBounds(390, y, 500, 100);
            card.setBackground(CARD);
            card.setOpaque(false);

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    card.setBackground(CARD_HOVER);
                    card.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    card.setBackground(CARD);
                    card.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        int gameId = gameService.createGame();
                        playerService.initializePlayer(gameId, numberOfPlayers);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            });

            // Pawn images
            int totalWidth = images.length * 20;
            int startX = (500 - totalWidth) / 2;

            for (String img : images) {
                JLabel pawn = new JLabel();
                pawn.setBounds(startX, 20, 50, 40);

                ImageIcon icon = loadImage(img);
                if (icon != null) {
                    pawn.setIcon(icon);
                }

                card.add(pawn);
                startX += 20;
            }

            JLabel label = new JLabel(numberOfPlayers + " Players", JLabel.CENTER);
            label.setBounds(0, 62, 500, 30);
            label.setFont(textFont.deriveFont(Font.BOLD, 20f));
            label.setForeground(Color.WHITE);
            card.add(label);

            return card;
        }
        
        public JPanel linedPawns(String[] images) {
            
            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                }
            };

            card.setLayout(null);
            card.setBounds(965, 622, 500, 100);
            card.setBackground(null);
            card.setOpaque(false);
            
            int totalWidth = images.length * 20;
            int startX = (500 - totalWidth) / 2;

            for (String img : images) {
                JLabel pawn = new JLabel();
                pawn.setBounds(startX, 20, 50, 40);

                ImageIcon icon = loadImage(img);
                if (icon != null) {
                    pawn.setIcon(icon);
                }

                card.add(pawn);
                startX += 20;
            }
            
            return card;
        }

        // Load images
        private ImageIcon loadImage(String name) {
            try {
                ImageIcon icon = new ImageIcon(
                        getClass().getResource("/images/" + name)
                );
                return new ImageIcon(icon.getImage());
            } catch (Exception e) {
                System.out.println("Image not found: " + name);
                return null;
            }
        }
    }
}
