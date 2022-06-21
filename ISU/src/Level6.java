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
        Tank computerTank1 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank1.setIsPlayer(false);
        computerTank1.setCenterOfTank(350, 200);
        computerTank1.setSpeed(3);
        Tank computerTank2 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank2.setIsPlayer(false);
        computerTank2.setCenterOfTank(550, 200);
        computerTank2.setSpeed(3);
        Tank computerTank3 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank3.setIsPlayer(false);
        computerTank3.setCenterOfTank(750, 200);
        computerTank3.setSpeed(5);
        Tank computerTank4 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank4.setIsPlayer(false);
        computerTank4.setCenterOfTank(450, 400);
        computerTank4.setSpeed(6);
        Tank computerTank5 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank5.setIsPlayer(false);
        computerTank5.setCenterOfTank(650, 400);
        computerTank5.setSpeed(7);
        Tank computerTank6 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank6.setIsPlayer(false);
        computerTank6.setCenterOfTank(850, 400);
        computerTank6.setSpeed(8);
        Tank computerTank7 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
        computerTank7.setIsPlayer(false);
        computerTank7.setCenterOfTank(900, 300);
        computerTank7.setSpeed(8);
        tanks.add(player);
        tanks.add(computerTank1);
        tanks.add(computerTank2);
        tanks.add(computerTank3);
        tanks.add(computerTank4);
        tanks.add(computerTank5);
        tanks.add(computerTank6);
        tanks.add(computerTank7);
    }
}

