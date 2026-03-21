package entities;

import java.awt.Color;
import java.awt.Graphics;

import game.Game;
import game.Vector3;

public class RigidBody extends Entity {
	
	public RigidBody(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
	}
	
	@Override
	public void update(double Ts) {
		S.addSpeed(A, Ts);
		P.addSpeed(S, Ts);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX()-Game.camera.getX(), getY()-Game.camera.getY(), getWidth(), getHeight());
	}

}
