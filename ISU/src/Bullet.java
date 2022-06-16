import java.awt.*;

public class Bullet implements MovingShape {
    final int SPEED = 4;
    final int DURATION = 3;
    double[] slope;
    Point spawnPoint;
    long creationTime;


    public Bullet() {
        this.creationTime = System.currentTimeMillis();
    }

    public boolean isAlive() {
        long millis = System.currentTimeMillis() - creationTime;
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
    public void collision() {
        // TODO Auto-generated method stub
    }
}