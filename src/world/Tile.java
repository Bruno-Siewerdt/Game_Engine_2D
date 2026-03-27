package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.InvisibleBlock;
import entities.Plant;
import game.Game;
import game.Vector3;

public class Tile {
	
	enum TileType {
		GRASS, WATER, BRIDGE, PATH;
	}
	
	public Vector3 P;
	private BufferedImage sprite;
	private BufferedImage backSprite;
	TileType type;
	TileType backTileType;
	
	/* -----------  PADDERN SPRITES DEFINITION  -----------  */
	                                    //   X    Y   W   H
	private static final int[][] GRASS = {{ 85,   0, 16, 16}, 
								   	      { 85,  17, 16, 16}};
	
	private static final int[][] WATER = {{ 34,   0, 16, 16},
						   			      { 51,   0, 16, 16},
						   			      { 68,   0, 16, 16},
						   			      { 34,  17, 16, 16},
						   			      { 51,  17, 16, 16},
						   			      { 68,  17, 16, 16},
						   			      { 34,  34, 16, 16},
						   			      { 51,  34, 16, 16},
						   			      { 68,  34, 16, 16},
						   			      {  0,  17, 16, 16},
						   			      { 17,  17, 16, 16},
						   			      {  0,  34, 16, 16},
						   			      { 17,  34, 16, 16}};
	
	private static final int[][] PATH =  {{119, 153, 16, 16},
  								  		  {136, 153, 16, 16},
  								  		  {153, 153, 16, 16},
  								  		  {119, 170, 16, 16},
  								  		  {136, 170, 16, 16},
  								  		  {153, 170, 16, 16},
  								  		  {119, 187, 16, 16},
  								  		  {136, 187, 16, 16},
  								  		  {153, 187, 16, 16},
  								  		  { 85, 153, 16, 16},
  								  		  {102, 153, 16, 16},
  								  		  { 85, 170, 16, 16},
  								  		  {102, 170, 16, 16}};
	
	public Tile(Vector3 pos, TileType type) {
		this.type = type;
		this.P = pos;
		this.backTileType = null;
	}
	
	public Tile(Vector3 pos, TileType type, TileType backTileType) {
		this.type = type;
		this.P = pos;
		this.backTileType = backTileType;
	}
	
	int getTileIndex(int x, int y) {
		return x + y * (World.getWidth()/World.TILE_SIZE);
	}
	
	int getSpriteIndex(int X, int Y) {
		int spriteIndex = 4;
		
		int isUpGrass = World.tiles.get(getTileIndex(X, (Y == 0) ? Y : Y-1)).type == TileType.GRASS ? 1 : 0;
		int isDownGrass = World.tiles.get(getTileIndex(X, (Y == World.getHeight()/World.TILE_SIZE-1) ? Y : Y+1)).type == TileType.GRASS ? 1 : 0;
		int isLeftGrass = World.tiles.get(getTileIndex((X == 0) ? X : X-1, Y)).type == TileType.GRASS ? 1 : 0;
		int isRightGrass = World.tiles.get(getTileIndex((X == World.getWidth()/World.TILE_SIZE-1) ? X : X+1, Y)).type == TileType.GRASS ? 1 : 0;
		
		int map = isUpGrass << 3 | isLeftGrass << 2 | isDownGrass << 1 | isRightGrass;
		
		switch (map) {    // Make Logic better, is confusing - Idea: Use dual grid system
		case 1: 
			spriteIndex = 5;
			break;
		case 2: 
			spriteIndex = 7;
			break;
		case 3: 
			spriteIndex = 8;
			break;
		case 4: 
			spriteIndex = 3;
			break;
		case 6: 
			spriteIndex = 6;
			break;
		case 8: 
			spriteIndex = 1;
			break;
		case 9: 
			spriteIndex = 2;
			break;
		case 12: 
			spriteIndex = 0;
			break;
		}
		
		if (spriteIndex == 4) {
			if (X > 0 && Y > 0 && World.tiles.get(getTileIndex(X-1, Y-1)).type == TileType.GRASS) {
				 spriteIndex = 12;
			} else if (X < World.getWidth()/World.TILE_SIZE-1 && Y > 0 && World.tiles.get(getTileIndex(X+1, Y-1)).type == TileType.GRASS) {
				spriteIndex = 11;
			} else if (X > 0 && Y < World.getHeight()/World.TILE_SIZE-1 && World.tiles.get(getTileIndex(X-1, Y+1)).type == TileType.GRASS) {
				spriteIndex = 10;
			} else if (X < World.getWidth()/World.TILE_SIZE-1 && Y < World.getHeight()/World.TILE_SIZE-1 && World.tiles.get(getTileIndex(X+1, Y+1)).type == TileType.GRASS) {
				spriteIndex = 9;
			}
		} else if (this.type == TileType.WATER) {
			Game.entities.add(new InvisibleBlock(new Vector3(X*World.TILE_SIZE, Y*World.TILE_SIZE, 0), new Vector3(World.TILE_SIZE, World.TILE_SIZE, 0)));
		}
		return spriteIndex;
	}
	
	public void getSprite() {
		if (type == TileType.GRASS) {
			Random randomGenerator = new Random();
			int i = randomGenerator.nextInt(10) < 5 ? 0 : 1; 
			sprite = Game.spritesheet.getSprite(GRASS[i][0], GRASS[i][1], GRASS[i][2], GRASS[i][3]);
		} else {
			int X = this.P.getX()/World.TILE_SIZE;
			int Y = this.P.getY()/World.TILE_SIZE;
			int spriteIndex = getSpriteIndex(X, Y);
			
			int[][] spriteList = null;
			if (type == TileType.WATER) {
				spriteList = WATER;
			} else if (type == TileType.PATH || type == TileType.BRIDGE) {
				spriteList = PATH;
			}
			sprite = Game.spritesheet.getSprite(spriteList[spriteIndex][0], spriteList[spriteIndex][1], spriteList[spriteIndex][2], spriteList[spriteIndex][3]);
		}
		
		if (backTileType == TileType.GRASS) {
			backSprite = Game.spritesheet.getSprite(GRASS[0][0], GRASS[0][1], GRASS[0][2], GRASS[0][3]);
		}
	}
	
	public void render(Graphics g) {
		if (backTileType != null) {
			g.drawImage(backSprite, P.getX()-Game.camera.getX(), P.getY()-Game.camera.getY(), World.TILE_SIZE, World.TILE_SIZE, null);
		}
		g.drawImage(sprite, P.getX()-Game.camera.getX(), P.getY()-Game.camera.getY(), World.TILE_SIZE, World.TILE_SIZE, null);
	}
	
}
