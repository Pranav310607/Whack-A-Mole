import java.awt.Image;
import javax.swing.ImageIcon;
public abstract class HoleOccupant {
    protected boolean visible = false;
    protected int timeRemaining = 0;
    // Common implemented behavior 
    public void show(int duration) {
        visible = true;
        timeRemaining = duration;
    }
    public void hide() {
        visible = false;
        timeRemaining = 0;
    }
    public void tick() {
        if (visible && timeRemaining > 0) {
            timeRemaining--;
            if (timeRemaining == 0) hide();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    // Abstract contract for Polymorphism  
    public abstract int whack(); 
    public abstract Image getImage();
}