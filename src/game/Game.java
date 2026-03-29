package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import UI.UI;
import entities.*;
import entities.Entity.Direction;
import spritesheet.*;
import world.*;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	private Thread thread;
	public static JFrame frame;
	
	public static final int SCALE = 1; // Pixel scale
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height/SCALE;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/SCALE;
	public static Game game;
	
	public enum GameState {
		RUNNING, PAUSED;
	}
	
	/*  defines scale of world in X and Y axis  */
	public static final double WORLD_X_SCALE = 1.0;
	public static final double WORLD_Y_SCALE = 1.0;
	
	private BufferedImage image;
	public static Spritesheet spritesheet;
	public static UI ui;
	public static Camera camera;
	
	public static Player player;
	public static List<Entity> entities;
	World world;
	private GameState state;
	
	
	public static void main(String[] args) {
		game = new Game();
		game.start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initFrame();
		image = new BufferedImage(WIDTH/SCALE, HEIGHT/SCALE, BufferedImage.TYPE_INT_RGB);
		
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/roguelikeSheet_transparent.png");
		ui = new UI();
		world = new World();
		camera = new Camera();
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		player = new Player(new Vector3(350, 50, 0), new Vector3(128, 128, 1), new CollisionMask(new Vector3(32, 28, 0), new Vector3(64, 100, 0)));
	}
	
	public void initFrame() {
		frame = new JFrame("2D Game");
		frame.add(this);
		frame.setUndecorated(false);
		frame.setResizable(true);
		frame.pack();
		//Image icon = Entities.PLAYER[0];
		//frame.setIconImage(icon);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		state = GameState.RUNNING;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		thread.interrupt();
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	public void update(double Ts) {
		// ----------------- Update Here -----------------
		
		if (state != GameState.PAUSED) {
			for (Entity entity : entities) {
				entity.update(Ts);
			}
			player.update(Ts);
		}
		
		// -----------------  Ends Here  -----------------
	}
	
	public void render(double Ts) {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(120, 120, 255));
		g.fillRect(0, 0, WIDTH/SCALE, HEIGHT/SCALE);
		
		// ----------------- Render Here -----------------
		
		world.render(g);
		for (Entity entity : entities) {
			entity.render(g);
		}
		player.render(g);
		
		if (state == GameState.PAUSED) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0f, 0f, 0f, 0.5f));
			g2.fillRect(0, 0, WIDTH, HEIGHT);
		}
		
		ui.render(g);
		
		// -----------------  Ends Here  -----------------
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);

		bs.show();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double maxAmountOfTicks = 120.0;
		double ns = 1000000000 / maxAmountOfTicks;
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = now - lastTime;
			if(delta >= ns) {
				double Ts = delta/1000000000;
				update(Ts);
				render(Ts);
				lastTime = now;
			}
		}
		stop();
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
		ui.setState(state);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = Character.toUpperCase(e.getKeyChar());
		if (key == 'W') {
			player.setSpeed(new Vector3(0, -50, 0));
			player.setDirection(Direction.UP);
		}
		if (key == 'S') {
			player.setSpeed(new Vector3(0, 50, 0));
			player.setDirection(Direction.DOWN);
		}
		if (key == 'D') {
			player.setSpeed(new Vector3(50, 0, 0));
			player.setDirection(Direction.RIGHT);
		}
		if (key == 'A') {
			player.setSpeed(new Vector3(-50, 0, 0));
			player.setDirection(Direction.LEFT);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = Character.toUpperCase(e.getKeyChar());
		if (key == 'W' && player.getSpeedY() < 0) {
			player.setSpeed(new Vector3(0, 0, 0));
		}
		if (key == 'S' && player.getSpeedY() > 0) {
			player.setSpeed(new Vector3(0, 0, 0));
		}
		if (key == 'D' && player.getSpeedX() > 0) {
			player.setSpeed(new Vector3(0, 0, 0));
		}
		if (key == 'A' && player.getSpeedX() < 0) {
			player.setSpeed(new Vector3(0, 0, 0));
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (state == GameState.PAUSED) {
				setState(GameState.RUNNING);
			} else {
				setState(GameState.PAUSED);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ui.checkSelections(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ui.checkClicks(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}