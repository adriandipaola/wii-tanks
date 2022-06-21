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
		walls.add(new Rectangle(610, 330, 20, 220));
		walls.add(new Rectangle(240, 300, 120, 20)); 
		walls.add(new Rectangle(240, 300, 20, 150));
		walls.add(new Rectangle(240, 450, 150, 20));
		walls.add(new Rectangle(610, 310, 120, 20));
		walls.add(new Rectangle(710, 160, 20, 150));
		walls.add(new Rectangle(580, 160, 150, 20));
		tanks = new ArrayList<>();
		Tank player = new Tank("ISU/resources/tank.png");
		player.setIsPlayer(true);
		player.setCenterOfTank(200, 200);
		Tank computerTank1 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
		computerTank1.setIsPlayer(false);
		computerTank1.setCenterOfTank(750, 410);
		computerTank1.setSpeed(0);
		Tank computerTank2 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
		computerTank2.setIsPlayer(false);
		computerTank2.setCenterOfTank(320, 375);
		computerTank2.setSpeed(0);
		Tank computerTank3 = new RandomComputerTank("ISU/Resources/EnemyTank1.png");
		computerTank3.setIsPlayer(false);
		computerTank3.setCenterOfTank(650, 250);
		computerTank3.setSpeed(0);
		Tank computerTank4 = new RandomComputerTank("ISU/Resources/EnemyTank2.png");
		computerTank4.setIsPlayer(false);
		computerTank4.setCenterOfTank(500, 250);
		tanks.add(player);
		tanks.add(computerTank1);
		tanks.add(computerTank2);
		tanks.add(computerTank3);
		tanks.add(computerTank4);
    }
}