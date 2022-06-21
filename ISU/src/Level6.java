import java.awt.*;
import java.util.ArrayList;

public class Level6 extends AbstractLevel {
    public Level6() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
		walls.add(new Rectangle (198, 70, 20, 295));
		walls.add(new Rectangle (316, 240, 20, 295));
		walls.add(new Rectangle (434, 70, 20, 295));
		walls.add(new Rectangle (552, 240, 20, 295));
		walls.add(new Rectangle (670, 70, 20, 295));
		walls.add(new Rectangle (788, 167, 20, 295));
		tanks = new ArrayList<>();
        Tank player = new Tank("ISU/resources/tank.png");
        player.setIsPlayer(true);
        player.setCenterOfTank(150, 200);
        tanks.add(player);
    }
}

