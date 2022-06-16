import java.awt.*;
import java.util.ArrayList;

public class Level1 extends AbstractLevel {
    public Level1() {
        walls = new ArrayList<Rectangle>();
        walls.add(new Rectangle(0, 0, 10, 10));
    }
}
