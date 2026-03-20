package entities;

import java.awt.Color;
import java.awt.Graphics;

import game.Game;
import game.Vector3;
import game.Vector3.Axis;

public class RigidBody extends Entity {
	
	

	public RigidBody(Vector3 P) {
		super(P);
		A.sum(200, Axis.X);
		System.out.println(A.getX());
	}
	
	@Override
	public void update(double Ts) {
		
		S.addSpeed(A, Ts);
		P.addSpeed(S, Ts);
		
		if (this.getX() > Game.WIDTH/2 && A.getX() > 0) {
			A.sum(-400, Axis.X);
		} else if (this.getX() < Game.WIDTH/2 && A.getX() < 0) {
			A.sum(400, Axis.X);
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX()-40, getY()-40, 80, 80);
	}

}
