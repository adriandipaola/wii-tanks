import java.util.ArrayList;

public class BlankScreen extends AbstractLevel {
    public BlankScreen() {
        //for some reason the code would not work without me putting this here...
        //This is a little scuffed
        walls = new ArrayList<>();
        tanks = new ArrayList<>();
        Tank player = new Tank("ISU/resources/tank.png");
        player.setIsPlayer(true);
        player.setCenterOfTank(200, 200);
        tanks.add(player);
    }
}
