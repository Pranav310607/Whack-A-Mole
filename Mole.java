import java.awt.Image;
import javax.swing.ImageIcon;

public class Mole extends HoleOccupant {
    private Image image = new ImageIcon("mole.png").getImage(); 

    @Override
    public int whack() {
        hide();
        return 100;  
    }

    @Override
    public Image getImage() {
        return image;
    }
}