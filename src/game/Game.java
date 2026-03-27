package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

import entities.*;
import entities.Entity.Direction;
import game.Vector3.Axis;
import spritesheet.*;
import world.*;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	private Thread thread;
	public static JFrame frame;
	
	public static final int SCALE = 1; // Pixel scale
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height/SCALE;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/SCALE;
	
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
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initFrame();
		addKeyListener(this);
		image = new BufferedImage(WIDTH/SCALE, HEIGHT/SCALE, BufferedImage.TYPE_INT_RGB);
		
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/roguelikeSheet_transparent.png");
		//ui = new UI();
		world = new World();
		camera = new Camera();
		
		player = new Player(new Vector3(350, 50, 0), new Vector3(128, 128, 1), new CollisionMask(new Vector3(32, 28, 0), new Vector3(64, 100, 0)));
	}
	
	public void initFrame() {
		frame = new JFrame("2D Game");
		frame.add(this);
		frame.setUndecorated(true);
		frame.setResizable(true);
		frame.pack();
		//Image icon = Entities.PLAYER[0];
		//frame.setIconImage(icon);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update(double Ts) {
		// ----------------- Update Here -----------------
		
		for (Entity entity : entities) {
			entity.update(Ts);
		}
		player.update(Ts);
		
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
		
		// -----------------  Ends Here  -----------------
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		//ui.render(g);
		/*if(GameState == "MENU") {
			menu.render(g);
		}*/
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

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = Character.toUpperCase(e.getKeyChar());
		if (key == 'W' && player.S.getY() >= 0) {
			player.S.add(-50, Axis.Y);
			((Entity)player).setDirection(Direction.UP);
		}
		if (key == 'S' && player.S.getY() <= 0) {
			player.S.add(50, Axis.Y);
			((Entity)player).setDirection(Direction.DOWN);
		}
		if (key == 'D' && player.S.getX() <= 0) {
			player.S.add(50, Axis.X);
			((Entity)player).setDirection(Direction.RIGHT);
		}
		if (key == 'A' && player.S.getX() >= 0) {
			player.S.add(-50, Axis.X);
			((Entity)player).setDirection(Direction.LEFT);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = Character.toUpperCase(e.getKeyChar());
		if (key == 'W' && player.S.getY() <= 0) {
			player.S.add(50, Axis.Y);
		}
		if (key == 'S' && player.S.getY() >= 0) {
			player.S.add(-50, Axis.Y);
		}
		if (key == 'D' && player.S.getX() >= 0) {
			player.S.add(-50, Axis.X);
		}
		if (key == 'A' && player.S.getX() <= 0) {
			player.S.add(50, Axis.X);
		}
	}
}