package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Vector3;

public class Tile {
	
	public Vector3 P;
	public BufferedImage sprite;
	private Color color;
	
	public Tile(Vector3 pos, Color color/*, BufferedImage sprite*/) {
		//this.sprite = sprite;
		this.P = pos;
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(P.getX()-Game.camera.getX(), P.getY()-Game.camera.getY(), World.TILE_SIZE, World.TILE_SIZE);
	}
	
}
