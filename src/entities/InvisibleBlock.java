package entities;

import java.awt.Graphics;

import game.Vector3;

public class InvisibleBlock extends RigidBody {

	public InvisibleBlock(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
	}
	
	public void render(Graphics g) {
		// Don't render
	}

}
