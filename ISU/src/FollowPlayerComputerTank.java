import java.awt.*;

public class FollowPlayerComputerTank extends Tank{

    public FollowPlayerComputerTank(String imageLocation) {
        super(imageLocation);
        centerOfTank = new Point(500, 500);
        speed = 1;
    }

    @Override
    public void move(Tank otherTank) {

        //these are false by default.
        boolean up = false, down = false, left = false, right = false;

        if (otherTank.getHitbox().x > getHitbox().x) {
            right = true;
        }
        else {
            left = true;
        }
        if (otherTank.getHitbox().y > getHitbox().y) {
            down = true;
        }
        else {
            up = true;
        }

        moveTank(up, down, left, right);
    }
}
