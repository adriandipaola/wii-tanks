import java.awt.*;

public class RandomComputerTank extends Tank {
    int duration = 0;
    int previousDirection;

    public RandomComputerTank(String imageLocation) {
        super(imageLocation);
        centerOfTank = new Point(300, 500);
    }

    @Override
    public void move(Tank otherTank) {
        int random = (int) (Math.random() * 8);

        if (duration > 0) {
            duration--;
            random = previousDirection;
        } else {
            previousDirection = random;
            duration = (int) (Math.random() * 60) + 60;
        }

        if (random == 0) {
            moveTank(true, false, false, false);
        } else if (random == 1) {
            moveTank(true, false, false, true);
        } else if (random == 2) {
            moveTank(false, false, false, true);
        } else if (random == 3) {
            moveTank(false, true, false, true);
        } else if (random == 4) {
            moveTank(false, true, false, false);
        } else if (random == 5) {
            moveTank(false, true, true, false);
        } else if (random == 6) {
            moveTank(false, false, true, false);
        } else if (random == 7) {
            moveTank(true, false, true, false);
        }
    }
}
