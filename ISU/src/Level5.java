import java.awt.*;
import java.util.ArrayList;

public class Level5 extends AbstractLevel {
    public Level5() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
		walls.add(new Rectangle(180, 180, 20, 350));
		walls.add(new Rectangle(180, 180, 610, 20));
		walls.add(new Rectangle(790, 180, 20, 260));
		walls.add(new Rectangle(300, 420, 490, 20));
		walls.add(new Rectangle(300, 280, 20, 140));
		walls.add(new Rectangle(300, 280, 390, 20));
    }
}

