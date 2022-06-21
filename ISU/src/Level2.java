import java.awt.*;
import java.util.ArrayList;

public class Level2 extends AbstractLevel {
    public Level2() {
        walls = new ArrayList<>();
        walls.add(new Rectangle(70, 70, 860, 30)); //border (x,y,width,height)
		walls.add(new Rectangle(70, 530, 860, 30));
		walls.add(new Rectangle(70, 70, 30, 460));
		walls.add(new Rectangle(900, 70, 30, 460));
		walls.add(new Rectangle(290, 220, 420, 15));
		walls.add(new Rectangle(290, 370, 420, 15));
		tanks = new ArrayList<>();
        Tank player = new Tank("ISU/resources/tank.png");
        player.setIsPlayer(true);
        player.setCenterOfTank(500, 450);
        Tank computerTank1 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank1.setIsPlayer(false);
        computerTank1.setCenterOfTank(600, 275);
        computerTank1.setSpeed(1);
        Tank computerTank2 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank2.setIsPlayer(false);
        computerTank2.setCenterOfTank(350, 275);
        computerTank2.setSpeed(1);
        Tank computerTank3 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank3.setIsPlayer(false);
        computerTank3.setCenterOfTank(500, 140);
        computerTank3.setSpeed(2);
        tanks.add(player);
        tanks.add(computerTank1);
        tanks.add(computerTank2);
        tanks.add(computerTank3);
    }
}