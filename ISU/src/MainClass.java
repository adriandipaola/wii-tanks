/**
 * Adrian Di Paola and Elson Cao
 * 06-21-22
 * <p>
 * This is the main class of our Tanks game. Each of the other classes are used here and the Swing
 * graphics library is used in this class to create visuals for the game. This class also contains
 * other important methods relating to the game, such as checking if tanks have been shot or not.
 * This class also takes in key presses and mouse clicks and uses them, in conjunction with the other
 * classes, to move tanks and create bullets.
 * <p>
 * This class also contains a menu system which is comprised of images - instead of Jbuttons, rectangles
 * are used and the program checks if the rectangle was clicked on when MouseClicked is called.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass extends JPanel implements Runnable, KeyListener, MouseListener {

    static JFrame frame;
    boolean up, down, left, right;
    int screenWidth = 1000;
    int screenHeight = 600;
    Thread thread;
    int FPS = 60;
    int countFrames = 0;
    ArrayList<Bullet> playerBulletList = new ArrayList<>();
    ArrayList<Bullet> computerBulletList = new ArrayList<>();
    int xPos, yPos;
    ArrayList<Tank> tankList = new ArrayList<>();
    AbstractLevel[] levels = new AbstractLevel[7];
    ArrayList<Rectangle> wallsTesting = new ArrayList<>();
    private Tank playerTank;
    int levelNumber = 0;
    int currentScreen = 0;
    BufferedImage[] menuScreens = new BufferedImage[5];
    Rectangle startButton, instructionsButton, quitButton, backButton, nextButton, winButton;

    /*
    default constructor
     */
    public MainClass() {
        //importing images for the menu
        try {
            menuScreens[0] = ImageIO.read(new File("ISU/resources/TitleScreen.png"));
            menuScreens[1] = ImageIO.read(new File("ISU/resources/InstructionsScreen.png"));
            menuScreens[2] = ImageIO.read(new File("ISU/resources/PassScreen.png"));
            menuScreens[3] = ImageIO.read(new File("ISU/resources/FailedScreen.png"));
            menuScreens[4] = ImageIO.read(new File("ISU/resources/FailedScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //these are "buttons" (actually rectangles) for the menu screens
        startButton = new Rectangle(265, 510, 170, 57);
        instructionsButton = new Rectangle(436, 510, 170, 57);
        quitButton = new Rectangle(606, 510, 170, 57);
        backButton = new Rectangle(0, 0, 100, 100);
        nextButton = new Rectangle(130, 300, 210, 110);
        winButton = new Rectangle(380, 500, 140, 75);

        initialize();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setVisible(true);

        thread = new Thread(this);
        thread.start();
        MediaTracker tracker = new MediaTracker(this);

        tracker.addImage(tankList.get(0).getImgOfTank(), 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addMouseListener(this);
    }


    @Override
    public void run() {
        //this code runs 60 times per second
        while (true) {
            countFrames++;
            //main game loop
            update();
            this.repaint();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        //setups before the game starts running
        levels[0] = new BlankScreen();
        levels[1] = new Level1();
        levels[2] = new Level2();
        levels[3] = new Level3();
        levels[4] = new Level4();
        levels[5] = new Level5();
        levels[6] = new Level6();
        tankList = new ArrayList<>(levels[levelNumber].getTanks());
        //this line is for the getHitBox method
        playerTank = tankList.get(0);
        wallsTesting = new ArrayList<>(levels[levelNumber].getWalls());
    }

    public void update() {
        animateBullet();
        //checking if any of the computer bullets have come in contact with the player tank
        for (int j = 0; j < computerBulletList.size(); j++) {
            boolean isDead = checkDeath(tankList.get(0), computerBulletList.get(j));
            if (isDead) {
                gameOver();
            }
        }
        //this for loop goes through all tanks and does movement, and checks collision between tanks and walls
        for (int i = 0; i < tankList.size(); i++) {
            Tank tank = tankList.get(i);
            if (!tank.getIsPlayer() && countFrames % 150 == 0) {
                //shoot once every 150 frames if you are a computer tank
                addComputerBullet(tank);
            }
            if (tank.getIsPlayer()) {
                tank.moveTank(up, down, left, right);
            } else {
                tank.move(playerTank);
            }
            for (int j = 0; j < wallsTesting.size(); j++)
                checkCollision(tank, wallsTesting.get(j));
            //checking if bullets come in contact with tanks, ONLY if it is a computer tank
            if (i > 0) {
                for (int j = 0; j < playerBulletList.size(); j++) {
                    boolean isDead = checkDeath(tankList.get(i), playerBulletList.get(j));
                    if (isDead) {
                        tankList.remove(i);
                        playerBulletList.remove(j);
                        //if tankList.size() == 1 it will always be the player tank remaining
                        //because gameOver() would get called first
                        if (tankList.size() == 1) {
                            if (levelNumber == 6) {
                                currentScreen = 4;
                            } else {
                                for (int k = 0; k < computerBulletList.size(); k++) {
                                    computerBulletList.remove(i);
                                }
                                currentScreen = 2;
                            }
                        }
                    }
                }
            }

        }
    }


    /*
     * This method draws the tanks and levels
     * Takes in Graphics g
     * No Return
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //painting each menu image on screen depending on the currentScreen
        if (currentScreen < 5 ) {
            g.drawImage(menuScreens[currentScreen], 0, 0, this);
        } else {
            //set up the actual game
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.RED);
            //drawing walls
            for (int i = 0; i < wallsTesting.size(); i++)
                g2.fill(wallsTesting.get(i));
            //drawing tanks
            for (int i = 0; i < tankList.size(); i++) {
                Rectangle tankHitbox = tankList.get(i).getHitbox();
                g2.drawImage(tankList.get(i).getImgOfTank(), tankHitbox.x, tankHitbox.y,
                        tankHitbox.width, tankHitbox.height, this);
            }
            //player bullets are a different coulour than computer bullets for clarity
            for (int i = 0; i < playerBulletList.size(); i++) {
                g2.setColor(Color.GREEN);
                g2.fill(playerBulletList.get(i).getRectangle());
            }
            for (int i = 0; i < computerBulletList.size(); i++) {
                g2.setColor(Color.YELLOW);
                g2.fill(computerBulletList.get(i).getRectangle());
            }
            g2.setColor(Color.GREEN);
            drawLine(g);
        }
    }

    //draws a line between the top of the tank and the mouse cursor; a reticle of sorts
    public void drawLine(Graphics g) {
        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();

        g.drawLine(tankList.get(0).getTopOfTank().x, tankList.get(0).getTopOfTank().y, mouseX - frame.getX() - 5, mouseY - frame.getY() - 28);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * determines if w, a, s, and/or d is currently being pressed. Used in the move()
     * method for the player tank.
     *
     * @param e the Key Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = true;
            right = false;
        } else if (key == KeyEvent.VK_D) {
            right = true;
            left = false;
        } else if (key == KeyEvent.VK_W) {
            up = true;
            down = false;
        } else if (key == KeyEvent.VK_S) {
            down = true;
            up = false;
        } else if (key == KeyEvent.VK_0) {
            levelNumber++;
            initialize();
        }
    }

	@Override
	/*
	 * Sees when a key is released
	 * Takes in keyEvent e
	 * No Return
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A) {
			left = false;
		}else if(key == KeyEvent.VK_D) {
			right = false;
		}else if(key == KeyEvent.VK_W) {
			up = false;
		}else if(key == KeyEvent.VK_S) {
			down = false;
		}
	}


    void checkCollision(MovingShape movingShape, Rectangle wall) {
        Rectangle hitbox = movingShape.getRectangle();
        if (hitbox.intersects(wall)) {
            movingShape.collision(hitbox, wall);
        }
    }


    boolean checkDeath(Tank tank, Bullet bullet) {
        //this if statement is backup; a bullet will never kill a tank if it is past its lifetime
        if (System.currentTimeMillis() - bullet.creationTime < 200) {
            return false;
        }

        Rectangle hitbox = tank.getRectangle();
        Rectangle bulletHitbox = bullet.getRectangle();
        if (hitbox.intersects(bulletHitbox)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method gets called on mouse click, and adds a bullet starting at the top of the player
     * tank and moving towards the mouse cursor
     */
    public void addPlayerBullet() {
        //this if statement might not work once enemy bullets are implemented
        if (playerBulletList.size() != 4) {
            Bullet bullet = new Bullet();
            bullet.setSpawnPoint(new double[]{tankList.get(0).centerOfTank.x + 0.0, tankList.get(0).getTopOfTank().y + 0.0});
            playerBulletList.add(bullet);
            //figuring out the distance between the tank and the mouse cursor on click
            double x = xPos - tankList.get(0).centerOfTank.x;
            double y = yPos - tankList.get(0).getTopOfTank().y;
            System.out.println(x);
            System.out.println(y);
            double hypotenuse = Math.sqrt((x * x) + (y * y));
            System.out.println(hypotenuse);
            //determine the length of time it takes for the bullet to reach the cursor
            hypotenuse /= tankList.get(0).bulletSpeed;
            //setting velocities for the x and y axes - if you use pythagorean theorem with these values,
            // x^2 + y^2 = bulletSpeed^2
            x /= hypotenuse;
            y /= hypotenuse;
            bullet.setSlope(new double[]{x, y});
            //slope.add(new Double[]{x, y});
        }
    }

    /**
     * This method gets called every 150 frames, and adds a bullet starting at the top of the player
     * tank and moving towards the mouse cursor
     */
    public void addComputerBullet(Tank computerTank) {
        Bullet bullet = new Bullet();
        bullet.setSpawnPoint(new double[]{computerTank.centerOfTank.x + 0.0, computerTank.getTopOfTank().y + 0.0});
        computerBulletList.add(bullet);
        //figuring out the distance between the tank and the mouse cursor on click
        double x = tankList.get(0).centerOfTank.x - computerTank.centerOfTank.x;
        double y = tankList.get(0).centerOfTank.y - computerTank.getTopOfTank().y;
        System.out.println(x);
        System.out.println(y);
        double hypotenuse = Math.sqrt((x * x) + (y * y));
        System.out.println(hypotenuse);
        //determine the length of time it takes for the bullet to reach the cursor
        hypotenuse /= computerTank.bulletSpeed;
        //setting velocities for the x and y axes - if you use pythagorean theorem with these values,
        // x^2 + y^2 = bulletSpeed^2
        x /= hypotenuse;
        y /= hypotenuse;
        bullet.setSlope(new double[]{x, y});
    }

    /**
     * animates all bullets on the screen, given the bullet's speed. Also checks
     * the collision between bullets and walls, and removes bullets once they have
     * been alive for long enough
     */
    public void animateBullet() {
        for (int i = playerBulletList.size() - 1; i >= 0; i--) {
            Bullet bullet = playerBulletList.get(i);
            if (!bullet.isAlive()) {
                playerBulletList.remove(i);
            }
            for (int j = 0; j < wallsTesting.size(); j++) {
                checkCollision(bullet, wallsTesting.get(j));
            }
        }
        for (int i = computerBulletList.size() - 1; i >= 0; i--) {
            Bullet bullet = computerBulletList.get(i);
            if (!bullet.isAlive()) {
                computerBulletList.remove(i);
            }
            for (int j = 0; j < wallsTesting.size(); j++) {
                //tanks can shoot through walls on level 6
                if (levelNumber != 6) {
                    checkCollision(bullet, wallsTesting.get(j));
                }
            }
        }
    }


    void gameOver() {
        currentScreen = 3;
    }

    /**
     * Main method
     * The Frame and keyListener are initialized in this method
     *
     * @param args not used
     */
    public static void main(String[] args) {
        frame = new JFrame("Tanks!");
        MainClass myPanel = new MainClass();
        frame.add(myPanel);
        frame.addKeyListener(myPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //this if statement is here (and in this order) so the player can't accidentally click
        //a button while playing
        if (currentScreen > 4) {
            xPos = e.getX();
            yPos = e.getY();
            addPlayerBullet();
        } else if (currentScreen == 0 && startButton.contains(e.getPoint())) {
            //going into level 1
            currentScreen = 5;
            levelNumber = 1;
            initialize();
        } else if (instructionsButton.contains(e.getPoint())) {
            //go to the instructions screen
            currentScreen = 1;
        } else if (backButton.contains(e.getPoint())) {
            //go back to the main screen
            currentScreen = 0;
        } else if (nextButton.contains(e.getPoint())) {
            if (currentScreen == 2) {
                levelNumber++;
                currentScreen = 5;
                initialize();
            }
            if (winButton.contains(e.getPoint()) && currentScreen == 4) {
                levelNumber = 0;
                currentScreen = 0;
                initialize();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}