import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MovingAndCollisions1 extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener {
	
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
	AbstractLevel[] levels = new AbstractLevel[7];
	ArrayList<Rectangle> wallsTesting = new ArrayList<>();
	private Tank playerTank;
	private Tank computerTank;
	int levelNumber = 0;
	static JFrame startingScreen = new JFrame ("starting Screen");
	JPanel titleScreenPanel, instructionsPage, levelSurvive, levelDeath, levelScreen;
	JButton play, instructions, quit, back;
	BufferedImage[] menuScreens = new BufferedImage[5];
	int currentMenuScreen = 0;
	

	public MovingAndCollisions1() { //Constructor
		try {
			menuScreens[0] = ImageIO.read(new File("ISU/resources/TitleScreen.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		openFrame();
		instructions();
//		levelSurvive;
//		levelDeath;
//		levelScreen;
		initialize();
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		thread = new Thread(this);
		thread.start();
		MediaTracker tracker = new MediaTracker (this);

		tracker.addImage (tankList.get(0).getImgOfTank(), 0);
		try
		{
			tracker.waitForAll ();
		}
		catch (InterruptedException e)
		{
		}
		addMouseListener (this);
	}
	
	public void openFrame () { //graphics g
		JPanel button = new JPanel (new FlowLayout()); 
		button.setBackground(Color.BLACK); 	
		JLabel background = new JLabel(new ImageIcon("tanks.gif")); 
		
		play = new JButton ("Play");
		play.setActionCommand("play"); 
		play.addActionListener(this);
		instructions = new JButton ("Instructions");
		instructions.setActionCommand("instructions");
		instructions.addActionListener(this);
		quit = new JButton ("Quit");
		quit.setActionCommand("quit");
		quit.addActionListener(this);

		button.add(play);
		button.add(instructions);
		button.add(quit);

		frame.setLayout(new BorderLayout());
		frame.add(background, BorderLayout.CENTER);
		frame.add(button, BorderLayout.SOUTH);
	}
	
	public void instructions () {
		instructionsPage = new JPanel (new BorderLayout());
		instructionsPage.setBackground(Color.black);
		instructionsPage.setSize(MovingAndCollisions.WIDTH, MovingAndCollisions.HEIGHT);
		JPanel instructionPanel = new JPanel( new BorderLayout());
		JLabel instruction = new JLabel ("TANK GAME");
		instruction.setFont(new Font("Monospaced", Font.BOLD, 20));
		JLabel instruction2 = new JLabel ("Your goal is to move and shoot the enemy tanks in given levels. "); 
		instruction2.setFont(new Font("Monospaced", Font.BOLD, 60));
		JLabel instruction3 = new JLabel ("To do so, use WASD to move your tank and click to wherever pointed to shoot. Good Luck Soldier!"); 
		instruction3.setFont(new Font("Monospaced", Font.BOLD, 60));
		
		instructionPanel.add(instruction, BorderLayout.NORTH); 
		instructionPanel.add(instruction2, BorderLayout.CENTER);
		instructionPanel.add(instruction3, BorderLayout.SOUTH); 
		
		back = new JButton ("back");
		back.addActionListener(this);
		back.setActionCommand("goBack");
		
		instructionsPage.add(instruction, BorderLayout.NORTH);
		instructionsPage.add(instructionPanel, BorderLayout.CENTER);
		instructionsPage.add(back, BorderLayout.SOUTH);
		//frame.add(instructionsPage);
		
	}
	
	public void levelSurvive () {
//		endPanel = new JPanel(new BorderLayout()); 
//		endPanel.setBackground(Color.BLACK);
//		endPanel.setPreferredSize(new Dimension (700, 500));
//
//		JPanel buttonPanelTwo = new JPanel(new FlowLayout()); 
//		buttonPanelTwo.setBackground(Color.RED); 
//
//		JButton next = new JButton ("next level"); 
//		next.addActionListener(this);
//		next.setActionCommand("Next Level");
//
//		JButton exitTwo = new JButton ("quit Game"); 
//		exitTwo.addActionListener(this);
//		exitTwo.setActionCommand("Quit Game"); 
//
//		JLabel endInfo1Two = new JLabel(); 
//		endInfo1Two.setText("Yay! You PASSED");
//		endInfo1Two.setFont(new Font("Calibri", Font.BOLD, 60));
//		endInfo1Two.setForeground(Color.WHITE);
//
//		endInfo2Two = new JLabel ("You passed Level "+ (level-1));
//		endInfo2Two.setFont(new Font("Calibri", Font.BOLD, 30));
//		endInfo2Two.setForeground(Color.WHITE);
//
//		buttonPanelTwo.add(next); 
//		buttonPanelTwo.add(exitTwo); 
//
//		endPanel.add(buttonPanelTwo, BorderLayout.PAGE_END); 
	}
	
	public void levelDeath () {
		
	}
	
	public void levelScreen () {
		
	}
	@Override
	public void run() {
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
		levels[0] = new BlankScreen();
		levels[1] = new Level1();
		levels[2] = new Level2();
		levels[3] = new Level3();
		levels[4] = new Level4();
		levels[5] = new Level5();
		levels[6] = new Level6();
//		if (levelNumber > 0) {

			tankList = new ArrayList<Tank>(levels[levelNumber].getTanks());
			//this line is for the getHitBox method
			//playerTank = tankList.get(0);
			playerTank = tankList.get(0);
			wallsTesting = new ArrayList<Rectangle>(levels[levelNumber].getWalls());
//		}
	}
	
	public void update() {

		keepInBound();
		animateBullet ();
//		rotate();
		for (int j = 0; j < computerBulletList.size(); j++) {
			boolean isDead = checkDeath(tankList.get(0), computerBulletList.get(j));
			if (isDead) {
				gameOver();
			}
		}
		for (int i = 0; i < tankList.size(); i++) {
			Tank tank = tankList.get(i);
			if (!tank.getIsPlayer() && countFrames % 150 == 0) {
				addComputerBullet(tank);
			}
			if (tank.getIsPlayer()) {
				tank.moveTank(up, down, left, right);
			} 
			else {
				tank.move(playerTank);
				//tankList.get(i).moveComputerTank(direction);
			}
			for (int j = 0; j < wallsTesting.size(); j++)
				checkCollision(tank, wallsTesting.get(j));
			if (i > 0) {
				for (int j = 0; j < playerBulletList.size(); j++) {
					boolean isDead = checkDeath(tankList.get(i), playerBulletList.get(j));
					if (isDead) {
						tankList.remove(i);
						playerBulletList.remove(j);
						if (tankList.size()==1) {
							levelNumber++;
							initialize();
						}
					}
				}
			}

		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (currentMenuScreen == 0) {
			g.drawImage(menuScreens[0], 0, 0, this);
		} else {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, screenWidth, screenHeight);
			g2.setColor(Color.RED);
			for (int i = 0; i < wallsTesting.size(); i++)
				g2.fill(wallsTesting.get(i));

			for (int i = 0; i < tankList.size(); i++) {
				Rectangle tankHitbox = tankList.get(i).getHitbox();
				g2.drawImage(tankList.get(i).getImgOfTank(), tankHitbox.x, tankHitbox.y,
						tankHitbox.width, tankHitbox.height, this);
			}
			for (int i = 0; i < playerBulletList.size(); i++) {
				g2.setColor(Color.GREEN);
				g2.fill(playerBulletList.get(i).getRectangle());
			}
			for (int i = 0; i < computerBulletList.size(); i++) {
				g2.setColor(Color.YELLOW);
				g2.fill(computerBulletList.get(i).getRectangle());
			}
			g2.setColor(Color.GREEN);
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
		System.out.println("test");
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


	void checkCollision(MovingShape movingShape, Rectangle wall) {
		Rectangle hitbox = movingShape.getRectangle();
		if(hitbox.intersects(wall)) {
			movingShape.collision(hitbox, wall);
		}
	}


	boolean checkDeath(Tank tank, Bullet bullet) {
		if (System.currentTimeMillis() - bullet.creationTime < 200) {
			return false;
		}

		Rectangle hitbox = tank.getRectangle();
		Rectangle bulletHitbox = bullet.getRectangle();
		if(hitbox.intersects(bulletHitbox)) {
			System.out.println("death");
			return true;
		} else {
			return false;
		}
	}


	public void addPlayerBullet() {
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
			bullet.setSlope(new double[]{x,y});
		}
	}


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
		bullet.setSlope(new double[]{x,y});
	}


	public void animateBullet() {

		for(int i = playerBulletList.size() - 1; i >= 0; i--) {
			Bullet bullet = playerBulletList.get(i);
			if (!bullet.isAlive()) {
				playerBulletList.remove(i);
			}
			for (int j = 0; j < wallsTesting.size(); j++) {
				checkCollision(bullet, wallsTesting.get(j));
			}
		}
		for(int i = computerBulletList.size() - 1; i >= 0; i--) {
			Bullet bullet = computerBulletList.get(i);
			if (!bullet.isAlive()) {
				computerBulletList.remove(i);
			}
			for (int j = 0; j < wallsTesting.size(); j++) {
				checkCollision(bullet, wallsTesting.get(j));
			}
		}
	}


	void gameOver() {
		// TODO Auto-generated method stub
		System.out.println("Game Over");
		System.exit(0);
	}


    public static void main(String[] args) {
        frame = new JFrame("Tanks!");
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

	@Override
	public void actionPerformed(ActionEvent e) {

		play.setVisible(false);
		instructions.setVisible(false);
		quit.setVisible(false);
		if (e.getActionCommand().equals("play")) {
			levelNumber = 1;
			currentMenuScreen = 1;
			initialize();
		} else if (e.getActionCommand().equals("quit")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("instructions")) {
			instructions();
		} else if (e.getActionCommand().equals("goBack")) {
			back.setVisible(false);
			play.setVisible(true);
			instructions.setVisible(true);
			quit.setVisible(true);
		}
		
	}
}
