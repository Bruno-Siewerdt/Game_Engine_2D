package world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.Vector3;

public class World {
	
	private static final long SEED = 0;
	private static final int WIDTH = 512;
	private static final int HEIGHT= 512;
	private static final double FREQUENCY = 1.0 / 48.0;
	public static int TILE_SIZE = 64;
	public static List<Tile> tiles;
	public int FloorLength;
	
	public World() {
		tiles = new ArrayList<Tile>();
	}
		
			
	
	public void render(Graphics g) {
		
	}
}
