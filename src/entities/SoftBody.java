package entities;

import java.awt.Graphics;

import game.Vector3;

public class SoftBody extends Entity {

	public SoftBody(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
	}
	
	@Override
	public void update(double Ts) {
		
		speed.integrate(acceleration, Ts);
		position.integrate(speed, Ts);
		
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}

}
