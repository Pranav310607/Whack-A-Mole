import java.util.List;
import java.util.ArrayList;
import java.io.*;
public class HighScoreManager {
    private static final String FILE_NAME = "scores.dat";
    // saveScores method declared to throw HighScoreException  
    public void saveScores(List<PlayerScore> scores) throws HighScoreException {
        // Must use ObjectOutputStream to serialize score data  
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException e) {
            // Catch IOException, wrap in HighScoreException, and re-throw 
            throw new HighScoreException("Failed to save scores.", e); 
        }
    }
    // loadScores method declared to throw HighScoreException  
    @SuppressWarnings("unchecked")
    public List<PlayerScore> loadScores() throws HighScoreException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        // Must use ObjectInputStream to deserialize score data  
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<PlayerScore>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Catch, wrap in HighScoreException, and re-throw  
            throw new HighScoreException("Failed to load scores.", e);
        }
    }
    public int getHighScore() {
        try {
            List<PlayerScore> scores = loadScores();
            return scores.stream()
                .mapToInt(PlayerScore::getScore)
                .max()
                .orElse(0);
        } catch (HighScoreException e) {
            // Handle the custom exception internally for the getter
            e.printStackTrace();
            return 0;
        }
    }
    public void addScore(String playerName, int score) {
        try {
            List<PlayerScore> scores = loadScores();
            scores.add(new PlayerScore(playerName, score));
            saveScores(scores);
        } catch (HighScoreException e) {
            e.printStackTrace();
        }
    }
}