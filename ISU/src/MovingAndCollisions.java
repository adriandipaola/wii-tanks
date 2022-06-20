import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class MovingAndCollisions extends JPanel implements Runnable, KeyListener, MouseListener {

	Rectangle rect = new Rectangle(120, 460, 30, 55);
	Point centerOfTank = new Point(135, 487);
	Rectangle[] walls = new Rectangle[12];
	static JFrame frame;
	boolean up, down, left, right;
	int speed = 4;
	int screenWidth = 1000;
	int screenHeight = 600;
	Thread thread;
	int FPS = 60;
	BufferedImage firstImage = ImageIO.read(new File("Tank.png"));
	BufferedImage rotatedImage = firstImage;
	
	public MovingAndCollisions() throws IOException { //Constructor
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		thread = new Thread(this);
		thread.start();
		MediaTracker tracker = new MediaTracker (this);
		tracker.addImage (firstImage, 0);
		try
		{
			tracker.waitForAll ();
		}
		catch (InterruptedException e)
		{
		}
	}
	
	@Override
	public void run() {
		initialize();
		while(true) {
			//main game loop
			try {
				update();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		walls[0] = new Rectangle(70, 70, 860, 30); //border (x,y,width,height)
		walls[1] = new Rectangle(70, 530, 860, 30);
		walls[2] = new Rectangle(70, 70, 30, 460);
		walls[3] = new Rectangle(900, 70, 30, 460);
		walls[4] = new Rectangle(900, 70, 30, 460);
		walls[5] = new Rectangle(900, 70, 30, 460);
		walls[6] = new Rectangle(900, 70, 30, 460);
		walls[7] = new Rectangle(900, 70, 30, 460);
		walls[8] = new Rectangle(900, 70, 30, 460);
		walls[9] = new Rectangle(900, 70, 30, 460);
		walls[10] = new Rectangle(900, 70, 30, 460);
		walls[11] = new Rectangle(900, 70, 30, 460);

	}
	
	public void update() throws IOException {
		move();
		keepInBound();
//		rotate();
		for(int i = 0; i < walls.length; i++)
			checkCollision(walls[i]);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, screenWidth, screenHeight);
		g2.setColor(Color.ORANGE);
		for(int i = 0; i < walls.length; i++)
			g2.fill(walls[i]);
		g2.setColor(Color.magenta);
		try {
			rotatedImage = ImageIO.read(new File("Tank.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.drawImage(rotatedImage, rect.x, rect.y, rect.width, rect.height, this);
		
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
	
	void move() {
		if(left)
			rect.x -= speed;
		else if(right)
			rect.x += speed;
		if(up)
			rect.y += -speed;
		else if(down)
			rect.y += speed;
		centerOfTank.x = rect.x + 15;
		centerOfTank.y = rect.y + 27;
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

//	void rotate() throws IOException {
//		int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
//		int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
//		int x = mouseX - centerOfTank.x - frame.getX();
//		int y = (mouseY - centerOfTank.y - frame.getY()) * -1;
//		System.out.println (x + ", " + y);
//		if (x == 0)
//			x = 1;
//		double tangent = (double)y / x;
//		double theta = Math.atan(tangent);
//		theta = Math.toDegrees(theta);
//		if (x > 0 && y >= 0)
//			theta += 270;
//		else if (x < 0 && y >= 0)
//			theta += 90;
//		else if (x > 0 && y <= 0)
//			theta += 270;
//		else if (x < 0 && y <= 0)
//			theta += 90;
//		theta *= -1;
//		double thetaRads = Math.toRadians(theta);
//		double sin = Math.abs(Math.sin(thetaRads));
//		double cos = Math.abs(Math.cos(thetaRads));
//		System.out.println(sin);
//		System.out.println(cos);
//		int w = (int) Math.floor(firstImage.getWidth() * cos + firstImage.getHeight() * sin);
//		int h = (int) Math.floor(firstImage.getHeight() * cos + firstImage.getWidth() * sin);
//		rotatedImage = new BufferedImage(w, h, firstImage.getType());
//		final AffineTransform at = new AffineTransform();
//		at.translate(w / 2, h / 2);
//		at.rotate(thetaRads,0, 0);
//		double offset = (firstImage.getWidth() - firstImage.getHeight()) / 2;
//		at.translate(offset, offset);
//		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//		//rotateOp.filter(firstImage,rotatedImage);
//		ImageIO.write(rotatedImage, "png", new File("RotatedTank.png"));
//	}
	void checkCollision(Rectangle wall) {
		//check if rect touches wall
		if(rect.intersects(wall)) {
			System.out.println("collision");
			//stop the rect from moving
			double left1 = rect.getX();
			double right1 = rect.getX() + rect.getWidth();
			double top1 = rect.getY();
			double bottom1 = rect.getY() + rect.getHeight();
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
				rect.x = wall.x - rect.width;
	        }
	        else if(left1 < right2 &&
	        		right1 > right2 && 
	        		right2 - left1 < bottom1 - top2 && 
	        		right2 - left1 < bottom2 - top1)
	        {
	            //rect collides from right side of the wall
	        	rect.x = wall.x + wall.width;
	        }
	        else if(bottom1 > top2 && top1 < top2)
	        {
	            //rect collides from top side of the wall
	        	rect.y = wall.y - rect.height;
	        }
	        else if(top1 < bottom2 && bottom1 > bottom2)
	        {
	            //rect collides from bottom side of the wall
	        	rect.y = wall.y + wall.height;
	        }
		}
	}
	
//	public static void main(String[] args) throws IOException {
//		frame = new JFrame ("Example");
//		MovingAndCollisions1 myPanel = new MovingAndCollisions1();
//		frame.add(myPanel);
//		frame.addKeyListener(myPanel);
//		frame.setVisible(true);
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
//	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
