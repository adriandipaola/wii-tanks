import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MovingAndCollisions1 extends JPanel implements Runnable, KeyListener, MouseListener {
	
	static JFrame frame;
	Rectangle rect = new Rectangle(120, 460, 30, 55);
	Point centerOfTank = new Point(135, 487);
	Point topOfTank = new Point(135, 460);
	Rectangle[] walls = new Rectangle[12];
	boolean up, down, left, right;
	int speed = 2;
	int bulletSpeed = 4;
	int screenWidth = 1000;
	int screenHeight = 600;
	Thread thread;
	int FPS = 60;
	int countFrames = 0;
	ArrayList <Bullet> playerBulletList = new ArrayList<>();
	ArrayList <Bullet> computerBulletList = new ArrayList<>();
	int xPos, yPos;
	ArrayList <Tank> tankList = new ArrayList<>();
//	Tank player1 = new Tank("ISU/Resources/Tank.png");
	AbstractLevel[] levels = new AbstractLevel[6];
	ArrayList<Rectangle> wallsTesting = new ArrayList<>();
	private Tank playerTank;
	private Tank computerTank;

	public MovingAndCollisions1() { //Constructor

		//this is temporary; i dont know where to put this
		playerTank = new Tank("ISU/Resources/Tank.png");
		playerTank.setIsPlayer(true);
		computerTank = new FollowPlayerComputerTank("ISU/Resources/EnemyTank1.png");
		computerTank.setIsPlayer(false);

		tankList.add(playerTank);
		tankList.add(computerTank);


		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		thread = new Thread(this);
		thread.start();
		MediaTracker tracker = new MediaTracker (this);
		//firstImage = Toolkit.getDefaultToolkit ().getImage ("ISU\\resources\\Tank.png");

		tracker.addImage (tankList.get(0).getImgOfTank(), 0);
		try
		{
			tracker.waitForAll ();
		}
		catch (InterruptedException e)
		{
		}
		addMouseListener (this);
//		image = Toolkit.getDefaultToolkit().getImage("crosshair.PNG");
//		Point hotspot = new Point (0, 0);
//		Toolkit toolkit = Toolkit.getDefaultToolkit ();
//		Cursor cursor = toolkit.createCustomCursor (image, hotspot, "pen");
//		frame.setCursor (cursor);

		//change this to a for loop to create all the levels

	}
	
	@Override
	public void run() {
		initialize();
		while(true) {
			countFrames ++;
			//main game loop
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initialize() {
		//setups before the game starts running
		levels[1] = new Level1();
		wallsTesting = new ArrayList<Rectangle>(levels[1].getWalls());
	}
	
	public void update() {

		keepInBound();
		animateBullet ();
//		rotate();
		for (Tank tank : tankList) {
			if (!tank.getIsPlayer() && countFrames % 60 == 0) {
				addComputerBullet();
			}
			if (tank.getIsPlayer()) {
				tank.moveTank(up, down, left, right);
			} else {
				tank.move(playerTank);
				//tankList.get(i).moveComputerTank(direction);
			}
			for (int j = 0; j < wallsTesting.size(); j++)
				checkCollision(tank, wallsTesting.get(j));
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, screenWidth, screenHeight);
		g2.setColor(Color.ORANGE);
		for(int i = 0; i < wallsTesting.size(); i++)
			g2.fill(wallsTesting.get(i));
		g2.setColor(Color.magenta);

		for (int i = 0; i < tankList.size(); i++) {
			Rectangle tankHitbox = tankList.get(i).getHitbox();
			g2.drawImage(tankList.get(i).getImgOfTank(), tankHitbox.x, tankHitbox.y,
					tankHitbox.width, tankHitbox.height, this);
		}
		for (int i = 0; i< playerBulletList.size(); i++) {
			g2.fill(playerBulletList.get(i).getRectangle());
		}
		for (int i = 0; i< computerBulletList.size(); i++) {
			g2.fill(computerBulletList.get(i).getRectangle());
		}
	}

	public void drawLine (Graphics g) {
		int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();

		g.drawLine(tankList.get(0).getTopOfTank().x, tankList.get(0).getTopOfTank().y, mouseX - frame.getX() - 5, mouseY - frame.getY() - 28);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A) {
			left = true;
			right = false;
		}else if(key == KeyEvent.VK_D) {
			right = true;
			left = false;
		}else if(key == KeyEvent.VK_W) {
			up = true;
			down = false;
		}else if(key == KeyEvent.VK_S) {
			down = true;
			up = false;
		}
	}

	@Override
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


	void keepInBound() {
		if(rect.x < 0)
			rect.x = 0;
		else if(rect.x > screenWidth - rect.width)
			rect.x = screenWidth - rect.width;
		
		if(rect.y < 0)
			rect.y = 0;
		else if(rect.y > screenHeight - rect.height)
			rect.y = screenHeight - rect.height;
	}

//	void rotate() {
//		int x = mouseX - centerOfTank.x - frame.getX();
//		int y = (mouseY - centerOfTank.y - frame.getY()) * -1;
//		if (x == 0)
//			x = 1;
//		double tangent = (double)y / x;
//		double theta = Math.atan(tangent);
//		theta = Math.toDegrees(theta);
//		if (x < 0 && y >= 0)
//			theta = 180 + theta;
//		else if (x > 0 && y <= 0)
//			theta = 360 + theta;
//		else if (x < 0 && y <= 0)
//			theta = 180 + theta;
//	}


	void checkCollision(MovingShape movingShape, Rectangle wall) {
		Rectangle hitbox = movingShape.getRectangle();
		if(hitbox.intersects(wall)) {
			System.out.println("collision");
			movingShape.collision();
		}
	}


	void checkCollision1(MovingShape movingShape, Rectangle wall) {
		Rectangle hitbox = movingShape.getRectangle();
		//check if rect touches wall
		if(hitbox.intersects(wall)) {
			System.out.println("collision");
			//stop the hitbox from moving
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
	        }
	        else if(left1 < right2 &&
	        		right1 > right2 && 
	        		right2 - left1 < bottom1 - top2 && 
	        		right2 - left1 < bottom2 - top1)
	        {
	            //rect collides from right side of the wall
				hitbox.x = wall.x + wall.width;
	        }
	        else if(bottom1 > top2 && top1 < top2)
	        {
	            //rect collides from top side of the wall
				hitbox.y = wall.y - hitbox.height;
	        }
	        else if(top1 < bottom2 && bottom1 > bottom2)
	        {
	            //rect collides from bottom side of the wall
				hitbox.y = wall.y + wall.height;
	        }
		}
	}
	
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
			hypotenuse /= bullet.SPEED;
			//setting velocities for the x and y axes - if you use pythagorean theorem with these values,
			// x^2 + y^2 = bulletSpeed^2
			x /= hypotenuse;
			y /= hypotenuse;
			bullet.setSlope(new double[]{x,y});
			//slope.add(new Double[]{x, y});
		}
	}


	public void addComputerBullet() {
		Bullet bullet = new Bullet();
		bullet.setSpawnPoint(new double[]{tankList.get(1).centerOfTank.x + 0.0, tankList.get(1).getTopOfTank().y + 0.0});
		computerBulletList.add(bullet);
		//figuring out the distance between the tank and the mouse cursor on click
		double x = tankList.get(0).centerOfTank.x - tankList.get(1).centerOfTank.x;
		double y = tankList.get(0).centerOfTank.y - tankList.get(1).getTopOfTank().y;
		System.out.println(x);
		System.out.println(y);
		double hypotenuse = Math.sqrt((x * x) + (y * y));
		System.out.println(hypotenuse);
		//determine the length of time it takes for the bullet to reach the cursor
		hypotenuse /= bullet.SPEED;
		//setting velocities for the x and y axes - if you use pythagorean theorem with these values,
		// x^2 + y^2 = bulletSpeed^2
		x /= hypotenuse;
		y /= hypotenuse;
		bullet.setSlope(new double[]{x,y});
	}


	public void animateBullet() {

		for(int i = playerBulletList.size() - 1; i >= 0; i--) {
			Bullet bullet = playerBulletList.get(i);
			if (!bullet.isAlive()) {
				playerBulletList.remove(i);
			}
		}
		for(int i = computerBulletList.size() - 1; i >= 0; i--) {
			Bullet bullet = computerBulletList.get(i);
			if (!bullet.isAlive()) {
				computerBulletList.remove(i);
			}
		}
	}


	public static void main(String[] args) {
		frame = new JFrame ("Tanks!");
		MovingAndCollisions1 myPanel = new MovingAndCollisions1();
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
		xPos = e.getX();
		yPos = e.getY();
		addPlayerBullet();

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
