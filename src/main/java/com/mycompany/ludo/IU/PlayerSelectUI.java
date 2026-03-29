package com.mycompany.ludo.IU;

import com.mycompany.ludo.connection.Connectivity;
import com.mycompany.ludo.service.DiceService;
import com.mycompany.ludo.service.GameService;
import com.mycompany.ludo.service.PawnService;
import com.mycompany.ludo.service.PlayerService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class PlayerSelectUI extends JFrame {
    
    private Connection conn;
    private GameService gameService;
    private DiceService diceService;
    private PawnService pawnService;
    private PlayerService playerService;

    public PlayerSelectUI(Connection conn, GameService gameService, DiceService diceService, PawnService pawnService) {
        this.conn = conn;
        this.gameService = gameService;
        this.diceService = diceService;
        this.pawnService = pawnService;
        this.playerService = new PlayerService(conn, gameService, diceService, pawnService);

        setTitle("Ludo");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new MainPanel());
    }

    // ───────────────── PANEL ─────────────────
    class MainPanel extends JPanel {

        Color BG = new Color(18, 15, 40);
        Color CARD = new Color(45, 40, 80);
        Color CARD_HOVER = new Color(65, 60, 110);

        Font titleFont = loadFont(50f, true);
        Font subtitleFont = loadFont(15f, false);
        Font textFont = loadFont(17f, false);


        public MainPanel() {
            setLayout(null);
            setBackground(BG);

            // ── TITLE ──
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

            // ── CARDS ──
            add(buildCard(2, new String[]{"pawn_red.png", "pawn_blue.png"}, 240));
            add(buildCard(3, new String[]{"pawn_red.png", "pawn_blue.png", "pawn_yellow.png"}, 360));
            add(buildCard(4, new String[]{"pawn_red.png", "pawn_blue.png", "pawn_yellow.png", "pawn_green.png"}, 480));
        }

        // ── CARD BUILDER ──
        private JPanel buildCard(int numberOfPlayers, String[] images, int y) {

            JPanel card = new JPanel() {
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

            // HOVER EFFECT
            card.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    card.setBackground(CARD_HOVER);
                    card.repaint();
                }

                public void mouseExited(MouseEvent e) {
                    card.setBackground(CARD);
                    card.repaint();
                }
                
                public void mousePressed(MouseEvent e) {
                    try {
                        int gameId = gameService.createGame();
                        playerService.initializePlayer(gameId, numberOfPlayers);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            });

            // Pawn images (BIGGER + CENTERED)
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

            // Text
            JLabel label = new JLabel(numberOfPlayers + " Players", JLabel.CENTER);
            label.setBounds(0, 62, 500, 30);
            label.setFont(textFont.deriveFont(Font.BOLD, 20f));
            label.setForeground(Color.WHITE);
            card.add(label);

            return card;
        }

        // ── LOAD IMAGE FROM RESOURCES ──
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

        // ── LOAD POPPINS ──
        private Font loadFont(float size, boolean bold) {
            try {
                InputStream is = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
                Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
                return bold ? font.deriveFont(Font.BOLD) : font;
            } catch (Exception e) {
                return new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, (int) size);
            }
        }
    }
    
    private void onPlayerCountSelected(int count) {
        System.out.println(count + " players");
    }

    // ───────────────── MAIN ─────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        Connection conn = Connectivity.getConnection();
        if (conn == null) {
            System.out.println("Could not connect to database.");
            return;
        }
        GameService gameService = new GameService(conn);
        DiceService diceService = new DiceService();
        PawnService pawnService = new PawnService(conn);
        new PlayerSelectUI(conn, gameService, diceService, pawnService).setVisible(true);
    });
    }
}