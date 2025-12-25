import java.awt.Image;
import javax.swing.ImageIcon;

public class BonusMole extends HoleOccupant {
    private Image image = new ImageIcon("bonusmole.png").getImage();

    @Override
    public int whack() {
        hide();
        return 500; // Returns a large positive score  
    }

    @Override
    public Image getImage() {
        return image;
    }
}