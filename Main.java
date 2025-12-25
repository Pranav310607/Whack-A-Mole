import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        int gridSize = 3;        
        int gameTime = 10;       

        HighScoreManager highScoreManager = new HighScoreManager();
        try {
            highScoreManager.loadScores(); // Attempt to load scores on startup
        } catch (HighScoreException e) {
            JOptionPane.showMessageDialog(null, "Warning: Could not load high scores. Starting with an empty list.", "File Error", JOptionPane.WARNING_MESSAGE);
        }
        
        GameUI gui = new GameUI(gridSize, gameTime, highScoreManager);
    }
}
