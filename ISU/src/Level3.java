import java.awt.*;
import java.util.ArrayList;

public class Level3 extends AbstractLevel {
    public Level3() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
		walls.add(new Rectangle(340, 100, 20, 200));
		walls.add(new Rectangle(610, 330, 20, 200));
		walls.add(new Rectangle(290, 300, 50, 20));
    }
}