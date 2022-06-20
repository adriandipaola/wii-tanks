import java.awt.*;
import java.util.ArrayList;

public class Level1 extends AbstractLevel {
    public Level1() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
        walls.add(new Rectangle(170, 270, 200, 20));
        walls.add(new Rectangle(370, 270, 20, 200));
        walls.add(new Rectangle(640, 160, 20, 200));
        walls.add(new Rectangle(640, 360, 200, 20));
        tanks = new ArrayList<>();
        Tank player = new Tank("ISU/resources/tank.png");
        player.setIsPlayer(true);
        player.setCenterOfTank(120, 400);
        Tank computerTank1 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank1.setIsPlayer(false);
        computerTank1.setCenterOfTank(750, 250);
        computerTank1.setSpeed(0);
        tanks.add(player);
        tanks.add(computerTank1);
    }
}
