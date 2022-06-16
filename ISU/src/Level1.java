import java.awt.*;
import java.util.ArrayList;

public class Level1 extends AbstractLevel {
    public Level1() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(200, 50, 400, 10));
        walls.add(new Rectangle(200, 50, 10, 400));
        walls.add(new Rectangle(600, 450, 10, 10));
        walls.add(new Rectangle(600, 470, 10, 10));
    }
}
