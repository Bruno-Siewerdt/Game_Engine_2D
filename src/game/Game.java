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
import spritesheet.*;
import world.*;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	private Thread thread;
	public static JFrame frame;
	
	public static final int SCALE = 1;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height/SCALE;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/SCALE;
	
	/*  Physics  */
	public static final double GRAVITY = 980;
	public static final double AIR_RESISTANCE = 100;
	
	private BufferedImage image;
	public static Spritesheet spritesheet;
	public static UI ui;
	
	public List<Entity> entities;
	World world;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		//spritesheet = new Spritesheet("/Spritesheet.png");
		initFrame();
		addKeyListener(this);
		image = new BufferedImage(WIDTH/SCALE, HEIGHT/SCALE, BufferedImage.TYPE_INT_RGB);
		//ui = new UI();
		world = new World();
		
		entities = new ArrayList<Entity>();
		entities.add(new RigidBody(new Vector3(WIDTH/4, HEIGHT/2, 0)));
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
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}