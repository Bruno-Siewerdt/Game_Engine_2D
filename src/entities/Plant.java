package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Vector3;

public class Plant extends RigidBody {
	
	private BufferedImage sprite;

	public Plant(Vector3 position, Vector3 dimensions, BufferedImage sprite) {
		super(position, dimensions);
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX()-Game.camera.getX(), getY()-Game.camera.getY(), getWidth(), getHeight(), null);
	}

}
