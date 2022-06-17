import java.awt.*;
import java.util.ArrayList;

public class Level4 extends AbstractLevel {
    public Level4() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
		walls.add(new Rectangle(70, 185, 680, 20));
		walls.add(new Rectangle(240, 300, 680, 20));
		walls.add(new Rectangle(70, 415, 680, 20));
    }
}

