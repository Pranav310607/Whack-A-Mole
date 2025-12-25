public class GameEngine implements Runnable {
    private volatile boolean gameIsRunning = true; 
    private int timeRemaining;
    private HoleOccupant[] holes;
    private GameUI gui;
    private int score = 0;
    private HighScoreManager highScoreManager;
    public GameEngine(GameUI gui, int totalHoles, HighScoreManager highScoreManager, int gameTime) {
        this.gui = gui;
        this.highScoreManager = highScoreManager;
        holes = new HoleOccupant[totalHoles];
        timeRemaining = gameTime;
        
        gui.updateHighScore(highScoreManager.getHighScore());
        gui.updateScore(0);
        gui.updateTime(gameTime);
    }

    @Override
    public void run() {
        while (gameIsRunning && timeRemaining > 0) { // Game loop executes for duration of game 
            try {
                Thread.sleep(1000); // Game loop must pause to control pace
                timeRemaining--;
                updateGameObjects();
                spawnRandomObjects(); // Randomly spawn HoleOccupant objects 
                
                // UI updates must be marshaled via gui methods 
                gui.updateTime(timeRemaining);
                gui.updateScore(score);
                gui.updateHighScore(highScoreManager.getHighScore());
                updateGUIHoles();
            } catch (InterruptedException e) {
                // Must handle InterruptedException 
                // Cleanly exit the run() loop for graceful shutdown 
                gameIsRunning = false; 
                Thread.currentThread().interrupt();
            }
        }
        
        // Game Over logic
        highScoreManager.addScore("Player", score);
        int highScore = highScoreManager.getHighScore();
        gui.showGameOver(score, highScore);
        gui.enableStart(); 
    }

    private void updateGameObjects() {
        for (int i = 0; i < holes.length; i++) {
            if (holes[i] != null) {
                holes[i].tick(); // Manage lifespan of HoleOccupant objects 
                if (!holes[i].isVisible()) holes[i] = null;
            }
        }
    }
    private void spawnRandomObjects() {
        int index = (int)(Math.random() * holes.length);
        if (holes[index] == null) {
            int rand = (int)(Math.random() * 10);
            if (rand < 7) { 
                holes[index] = new Mole();
            } else if (rand < 9) { 
                holes[index] = new Bomb();
            } else { 
                holes[index] = new BonusMole();
            }
            holes[index].show(3); 
        } else {
             // throw new InvalidGameStateException("Attempted to spawn object in occupied hole at index " + index);
        }
    }
    private void updateGUIHoles() {
        for (int i = 0; i < holes.length; i++) {
            gui.updateHole(i, holes[i]);
        }
    }
    public void whackHole(int index) {
        if (holes[index] != null && holes[index].isVisible()) {
            // Simply invoke the polymorphic method; behavior determined at runtime  
            score += holes[index].whack(); 
            gui.updateScore(score);
            gui.updateHighScore(highScoreManager.getHighScore());
            holes[index] = null;
            gui.updateHole(index, null); 
        }
    }
}