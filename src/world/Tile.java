package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Vector3;

public class Tile {
	
	public Vector3 P;
	public BufferedImage sprite;
	
	public Tile(Vector3 pos, BufferedImage sprite) {
		this.sprite = sprite;
		this.P = pos;
	}
	
	public void render(Graphics g) {
		
	}
	
}
