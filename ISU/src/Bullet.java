import java.awt.*;

public class Bullet implements MovingShape {
	final int SPEED = 4;
	final int DURATION = 4;
	double[] slope;
	Point spawnPoint;
	long creationTime;
	long bulletTime;

	public Bullet() {
		this.creationTime = System.currentTimeMillis();
		this.bulletTime = System.currentTimeMillis();
	}

	public boolean isAlive() {
		long millis = System.currentTimeMillis() - bulletTime; //need to keep track of this in moving and collisions run game
		return (millis / 1000) < DURATION;
	}


	public double[] getSlope() {
		return slope;
	}

	public void setSlope(double[] slope) {
		this.slope = slope;
	}

	public Point getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(double[] spawnPoint) {
		this.spawnPoint = new Point();
		this.spawnPoint.x = (int) spawnPoint[0];
		this.spawnPoint.y = (int) spawnPoint[1];
	}
	
//	public void setSpeed(int speed) {
//		this.SPEED=speed;
//	}


	@Override
	public Rectangle getRectangle() {
		long millis = System.currentTimeMillis() - creationTime;
		long frames = millis / 16;
		int xPosition = (int) Math.round(spawnPoint.x + frames * slope[0]);
		int yPosition = (int) Math.round(spawnPoint.y + frames * slope[1]);
		return new Rectangle(xPosition, yPosition, 10, 10);
	}

	/**
	 * reverses the velocity of the bullet based on the side of the wall it hits
	 */
	public void collision(Rectangle hitbox, Rectangle wall) {
		double left1 = hitbox.getX();
		double right1 = hitbox.getX() + hitbox.getWidth();
		double top1 = hitbox.getY();
		double bottom1 = hitbox.getY() + hitbox.getHeight();
		double left2 = wall.getX();
		double right2 = wall.getX() + wall.getWidth();
		double top2 = wall.getY();
		double bottom2 = wall.getY() + wall.getHeight();

		if(right1 > left2 && 
				left1 < left2 && 
				right1 - left2 < bottom1 - top2 && 
				right1 - left2 < bottom2 - top1)
		{
			//rect collides from left side of the wall
			hitbox.x = wall.x - hitbox.width;
			slope[0]*= -1;
			this.creationTime = System.currentTimeMillis();
			spawnPoint.x = hitbox.x;
			spawnPoint.y = hitbox.y;
		}
		else if(left1 < right2 &&
				right1 > right2 && 
				right2 - left1 < bottom1 - top2 && 
				right2 - left1 < bottom2 - top1)
		{
			//rect collides from right side of the wall
			hitbox.x = wall.x + wall.width;
			slope[0]*= -1;
			this.creationTime = System.currentTimeMillis(); // need to make the time limit
			spawnPoint.x = hitbox.x;
			spawnPoint.y = hitbox.y;
		}
		else if(bottom1 > top2 && top1 < top2)
		{
			//rect collides from top side of the wall
			hitbox.y = wall.y - hitbox.height;
			slope[1]*= -1;
			this.creationTime = System.currentTimeMillis();
			spawnPoint.x = hitbox.x;
			spawnPoint.y = hitbox.y;
		}
		else if(top1 < bottom2 && bottom1 > bottom2)
		{
			//rect collides from bottom side of the wall
			hitbox.y = wall.y + wall.height;
			slope[1]*= -1;
			this.creationTime = System.currentTimeMillis();
			spawnPoint.x = hitbox.x;
			spawnPoint.y = hitbox.y;
		}

	}
}