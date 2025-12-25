import java.awt.Image;
import javax.swing.ImageIcon;

public class Bomb extends HoleOccupant {
    private Image image = new ImageIcon("bomb.png").getImage(); 

    @Override
    public int whack() {
        hide();
        return -300; // Returns a large negative score  
    }

    @Override
    public Image getImage() {
        return image;
    }
}