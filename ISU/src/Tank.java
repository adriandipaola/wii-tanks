import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tank implements MovingShape {
	BufferedImage imgOfTank;
	Point centerOfTank = new Point(135, 487); //create a setter for this
	Point previousCenterOfTank = new Point(135, 487);
	boolean isPlayer;
	int speed = 2;
	ArrayList <Tank> tankList = new ArrayList<>();


	public Tank(String imageLocation) {
		try {
			imgOfTank = ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public BufferedImage getImgOfTank() {
		return imgOfTank;
	}


	public void setIsPlayer(boolean b) {
		this.isPlayer = b;
	}


	public void setCenterOfTank(int xPosition, int yPosition) {
		centerOfTank.x = xPosition;
		centerOfTank.y = yPosition;
	}


	public boolean getIsPlayer() {
		return isPlayer;
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


	public void move(Tank otherTank) {

	}

	/**
	 *
	 * @param direction 0 is straight up, and every number after is 45 degrees more
	 */
	public void moveComputerTank(int direction) {
//		for (int i = 1; i<tankList.size(); i++) {
			if (direction == 0) {
				centerOfTank.y -= speed;
			} else if (direction == 1) {
				centerOfTank.y -= speed;
				centerOfTank.x += speed;
			} else if (direction == 2) {
				centerOfTank.x += speed;
			} else if (direction == 3) {
				centerOfTank.y += speed;
				centerOfTank.x += speed;
			} else if (direction == 4) {
				centerOfTank.y += speed;
			} else if (direction == 5) {
				centerOfTank.y += speed;
				centerOfTank.x -= speed;
			} else if (direction == 6) {
				centerOfTank.x -= speed;
			} else if (direction == 7) {
				centerOfTank.y -= speed;
				centerOfTank.x -= speed;
			}
		}
		

	@Override
	public Rectangle getRectangle() {
		return getHitbox();
	}


	public void collision(Rectangle hitbox, Rectangle wall) {
		centerOfTank.x = previousCenterOfTank.x;
		centerOfTank.y = previousCenterOfTank.y;
	}

}



