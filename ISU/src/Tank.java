import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tank implements MovingShape {
    BufferedImage imgOfTank;
    Point centerOfTank = new Point(135, 487);
    Point previousCenterOfTank = new Point(135, 487);
    boolean isPlayer;
    int speed = 2;


    public Tank() {
        try {
            imgOfTank = ImageIO.read(new File("ISU/Resources/Tank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getImgOfTank() {
        return imgOfTank;
    }


    public Rectangle getHitbox(){
        return new Rectangle(centerOfTank.x - 15, centerOfTank.y - 27, 30, 55);
    }


    public Point getTopOfTank() {
        return new Point(centerOfTank.x, centerOfTank.y - 27);
    }


    public void moveTank(boolean up, boolean down, boolean left, boolean right) {
        previousCenterOfTank.x = centerOfTank.x;
        previousCenterOfTank.y = centerOfTank.y;
        if (left) {
            centerOfTank.x -= speed;
        } else if (right) {
            centerOfTank.x += speed;
        }
        if (up) {
            centerOfTank.y -= speed;
        } else if (down) {
            centerOfTank.y += speed;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return getHitbox();
    }


    public void collision() {
        centerOfTank.x = previousCenterOfTank.x;
        centerOfTank.y = previousCenterOfTank.y;
    }
}



