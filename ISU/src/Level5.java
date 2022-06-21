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
		tanks = new ArrayList<>();
		Tank player = new Tank("ISU/resources/tank.png");
		player.setIsPlayer(true);
		player.setCenterOfTank(130, 500);
		Tank computerTank1 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank3.png");
		computerTank1.setIsPlayer(false);
		computerTank1.setCenterOfTank(390, 350);
		computerTank1.setSpeed(0);
		computerTank1.setBulletSpeed(7);
		Tank computerTank2 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank3.png");
		computerTank2.setIsPlayer(false);
		computerTank2.setCenterOfTank(130, 130);
		computerTank2.setSpeed(1);
		computerTank2.setBulletSpeed(7);
		Tank computerTank3 = new FollowPlayerComputerTank("ISU/Resources/EnemyTank3.png");
		computerTank3.setIsPlayer(false);
		computerTank3.setCenterOfTank(800, 130);
		computerTank3.setSpeed(1);
		computerTank3.setBulletSpeed(7);
		Tank computerTank4 = new RandomComputerTank("ISU/Resources/EnemyTank3.png");
		computerTank4.setIsPlayer(false);
		computerTank4.setCenterOfTank(800, 500);
		computerTank4.setSpeed(1);
		computerTank4.setBulletSpeed(7);
		tanks.add(player);
		tanks.add(computerTank1);
		tanks.add(computerTank2);
		tanks.add(computerTank3);
		tanks.add(computerTank4);
    }
}

