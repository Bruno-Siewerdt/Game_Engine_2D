package entities;

import java.awt.Graphics;

import game.Vector3;

public class SoftBody extends Entity {

	public SoftBody(Vector3 P) {
		super(P);
	}
	
	@Override
	public void update(double Ts) {
		
		S.addSpeed(A, Ts);
		P.addSpeed(S, Ts);
		
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}

}
