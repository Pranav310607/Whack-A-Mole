import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameUI extends JFrame {
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JLabel highScoreLabel = new JLabel("High Score: 0");
    private JLabel timeLabel = new JLabel("Time 30s"); 
    private JButton[] holeButtons;
    private JButton startButton = new JButton("Start Game");
    private JButton exitButton = new JButton("Exit"); 
    private GameEngine gameEngine;
    private Thread gameThread;
    private HighScoreManager highScoreManager;
    private int gridSize;
    private int gameTime;
    // Realistic hole icon  
    private static final ImageIcon EMPTY_HOLE_ICON = new ImageIcon("empty_hole.png");
    public GameUI(int gridSize, int gameTime, HighScoreManager highScoreManager) {
        this.gridSize = gridSize;
        this.gameTime = gameTime;
        this.highScoreManager = highScoreManager;
        // Frame and Background (simulating wood)
        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(139, 69, 19)); 
        setTitle("Whack-a-Mole");
        setLayout(new BorderLayout());

        // Info Panel (Dark Blue Bar)  
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        infoPanel.setBackground(new Color(23, 56, 73)); 
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        scoreLabel.setFont(labelFont); highScoreLabel.setFont(labelFont); timeLabel.setFont(labelFont);
        scoreLabel.setForeground(Color.WHITE); highScoreLabel.setForeground(Color.WHITE); timeLabel.setForeground(Color.WHITE);
        infoPanel.add(scoreLabel); infoPanel.add(highScoreLabel); infoPanel.add(timeLabel);

        // Grid Panel (Game Board - Light Wood/Beige for cohesion)  
        JPanel gridPanel = new JPanel(new GridLayout(gridSize, gridSize, 20, 20)); 
        gridPanel.setBackground(new Color(230, 210, 190)); 
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        holeButtons = new JButton[gridSize * gridSize];
        for (int i = 0; i < holeButtons.length; i++) {
            JButton button = new JButton();
            holeButtons[i] = button;
            int index = i;
            // Realism: Remove default button styling
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(false);
            button.setIcon(EMPTY_HOLE_ICON);

            // Event Handling using ActionListener  
            button.addActionListener(e -> {
                if (gameEngine != null) gameEngine.whackHole(index);
            });
            gridPanel.add(button);
        }

        // Control Panel (Bottom Bar - Dark Blue)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        controlPanel.setBackground(new Color(23, 56, 73));
        styleButton(startButton, new Color(103, 172, 85)); 
        styleButton(exitButton, new Color(130, 130, 130)); 
        controlPanel.add(startButton);
        controlPanel.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));
        startButton.addActionListener(e -> startGame());

        add(infoPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Window Listener for Thread Interruption 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gameThread != null && gameThread.isAlive()) {
                    // Call interrupt() on gameThread to signal for clean exit 
                    gameThread.interrupt(); 
                }
                super.windowClosing(e);
            }
        });

        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    private void startGame() {
        startButton.setEnabled(false);
        timeLabel.setText("Time " + gameTime + "s"); 
        
        resetHoles();
        gameEngine = new GameEngine(this, gridSize * gridSize, highScoreManager, gameTime);
        // Create and start GameEngine on a new Thread 
        gameThread = new Thread(gameEngine); 
        gameThread.start();
    }
    
    // All UI update methods use SwingUtilities.invokeLater for thread safety 
    public void enableStart() {
        SwingUtilities.invokeLater(() -> {
            startButton.setText("Play Again");
            startButton.setEnabled(true);
        });
    }

    public void updateScore(int score) {
        SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
    }

    public void updateHighScore(int highScore) {
        SwingUtilities.invokeLater(() -> highScoreLabel.setText("High Score: " + highScore));
    }

    public void updateTime(int time) {
        SwingUtilities.invokeLater(() -> timeLabel.setText("Time " + time + "s"));
    }

    public void updateHole(int index, HoleOccupant occupant) {
        SwingUtilities.invokeLater(() -> {
            JButton button = holeButtons[index];
            if (occupant != null && occupant.isVisible()) {
                button.setIcon(new ImageIcon(occupant.getImage()));
            } else {
                button.setIcon(EMPTY_HOLE_ICON);
            }
        });
    }

    public void resetHoles() {
        SwingUtilities.invokeLater(() -> {
            for (JButton button : holeButtons) {
                button.setIcon(EMPTY_HOLE_ICON);
            }
        });
    }

    public void showGameOver(int finalScore, int highScore) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + finalScore + "\nHigh Score: " + highScore);
        });
    }
}