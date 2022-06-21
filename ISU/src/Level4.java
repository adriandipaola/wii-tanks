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
		tanks = new ArrayList<>();
        Tank player = new Tank("ISU/resources/tank.png");
        player.setIsPlayer(true);
        player.setCenterOfTank(200, 460);
        Tank computerTank1 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank1.setIsPlayer(false);
        computerTank1.setCenterOfTank(600, 265);
        computerTank1.setSpeed(3);
        Tank computerTank2 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank2.setIsPlayer(false);
        computerTank2.setCenterOfTank(350, 265);
        computerTank2.setSpeed(3);
        Tank computerTank3 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank3.setIsPlayer(false);
        computerTank3.setCenterOfTank(500, 130);
        computerTank3.setSpeed(2);
        Tank computerTank4 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank4.setIsPlayer(false);
        computerTank4.setCenterOfTank(350, 355);
        computerTank4.setSpeed(4);
        Tank computerTank5 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank5.setIsPlayer(false);
        computerTank5.setCenterOfTank(500, 355);
        computerTank5.setSpeed(4);
        Tank computerTank6 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank6.setIsPlayer(false);
        computerTank6.setCenterOfTank(500, 465);
        computerTank6.setSpeed(4);
        tanks.add(player);
        tanks.add(computerTank1);
        tanks.add(computerTank2);
        tanks.add(computerTank3);
        tanks.add(computerTank4);
        tanks.add(computerTank5);
        tanks.add(computerTank6);
        
    }
}

