import java.io.IOException;

public class HighScoreException extends Exception {
    // Custom checked exception for handling file I/O and deserialization errors  
    public HighScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
