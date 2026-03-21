package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.Vector3;

public class World {
	
	// #000000 path
	// #333333 tree
	// #666666 water
	// #999999 bush
	// #BBBBBB bridge
	// #FFFFFF grass
	
	public static final int TILE_SIZE = 64;
	private static int width;
	private static int height;
	public static List<Tile> tiles;
	
	private BufferedImage worldMap;
	
	final Color PATH = new Color(120, 69, 2);
	final Color GRASS = new Color(152, 252, 38);
	final Color WATER = new Color(62, 198, 240);
	final Color BRIDGE = new Color(61, 33, 2);
	final Color TREE = new Color(66, 120, 4);
	final Color BUSH = new Color(121, 219, 9);
	
	public World() {
		tiles = new ArrayList<Tile>();
		
		try {
			worldMap = ImageIO.read(getClass().getResource("/example_world.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = worldMap.getWidth()*TILE_SIZE;
		height = worldMap.getHeight()*TILE_SIZE;
		
		for (int w = 0; w < width; w+=TILE_SIZE) {
			for (int h = 0; h < height; h+=TILE_SIZE) {
				int rgb = worldMap.getRGB(w/TILE_SIZE, h/TILE_SIZE) & 0xFF;
				System.out.println(rgb);
				Color color;
				switch(rgb) {
				case 0:
					color = PATH;
					break;
				case 51:
					color = TREE;
					break;
				case 102:
					color = WATER;
					break;
				case 153:
					color = BUSH;
					break;
				case 187:
					color = BRIDGE;
					break;
				default:
					color = GRASS;
				}
				tiles.add(new Tile(new Vector3(w, h, 0), color));
			}
		}
	}
	
	public static final int getWidth() {
		return width;
	}
	
	public static final int getHeight() {
		return height;
	}
		
	public void render(Graphics g) {
		for (Tile tile : tiles) {
			tile.render(g);
		}
	}
	
}
