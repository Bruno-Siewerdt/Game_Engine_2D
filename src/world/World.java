package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.InvisibleBlock;
import entities.Plant;
import game.Game;
import game.Vector3;
import spritesheet.Spritesheet;
import world.Tile.TileType;

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
	
	public World() {
		tiles = new ArrayList<Tile>();
		
		try {
			worldMap = ImageIO.read(getClass().getResource("/example_world.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = worldMap.getWidth()*TILE_SIZE;
		height = worldMap.getHeight()*TILE_SIZE;
		
		createTiles();
	}
	
	void createTiles() {
		Random randomGenerator = new Random();
		for (int y = 0; y < height; y+=TILE_SIZE) {
			for (int x = 0; x < width; x+=TILE_SIZE) {
				int rgb = worldMap.getRGB(x/TILE_SIZE, y/TILE_SIZE) & 0xFF;
				
				TileType type = TileType.GRASS;
				TileType backType = null;
				
				switch(rgb) {
				case 0:
					type = TileType.PATH;
					backType = TileType.GRASS;
					break;
				case 51:
					int i = randomGenerator.nextInt(6);
					BufferedImage treeSprite = Game.spritesheet.getSprite(221 + (i*17), 171, 16, 32);
					Game.entities.add(new Plant(new Vector3(x, y, 0), new Vector3(TILE_SIZE, 2*TILE_SIZE, 0), treeSprite));
					type = TileType.GRASS;
					break;
				case 102:
					type = TileType.WATER;
					break;
				case 153:
					int n = randomGenerator.nextInt(3);
					BufferedImage bushSprite = Game.spritesheet.getSprite(323 + (n*17), 157, 16, 12);
					Game.entities.add(new Plant(new Vector3(x, y+(TILE_SIZE/4), 0), new Vector3(TILE_SIZE, 3*TILE_SIZE/4, 0), bushSprite));
					type = TileType.GRASS;
					break;
				case 187:
					type = TileType.BRIDGE;
					backType = TileType.GRASS;
					break;
				}
				
				tiles.add(new Tile(new Vector3(x, y, 0), type, backType));
			}
		}
		setTilesSprites();
	}
	
	void setTilesSprites() { // program first configures all tiles, then finds the proper sprite for each case (e.g. meeting of water and land)
		for (Tile tile: tiles) {
			tile.getSprite();
		}
	}
	
	public static final int getWidth() {
		return width;
	}
	
	public static final int getHeight() {
		return height;
	}
		
	public void render(Graphics g) {
		int initX = Game.camera.getX()/TILE_SIZE;
		int endX = (Game.camera.getX() + Game.WIDTH)/TILE_SIZE;
		int initY = Game.camera.getY()/TILE_SIZE;
		int endY = (Game.camera.getY() + Game.HEIGHT)/TILE_SIZE;
		
		endY = endY == height/TILE_SIZE ? endY-1 : endY;
		endX = endX == width/TILE_SIZE ? endX-1 : endX;
		
		for (int x = initX; x <= endX; x++) {
			for (int y = initY; y <= endY; y++) {
				Tile tile = tiles.get(x + y * (width/TILE_SIZE));
				tile.render(g);
			}
		}
	}
	
}
