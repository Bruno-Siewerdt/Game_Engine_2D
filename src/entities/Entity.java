package entities;

import java.awt.Graphics;

import game.Vector3;

public class Entity {
	
	public Vector3 P, S, A; // position, speed, acceleration
	
	public Entity(Vector3 P) {
		this.P = P;
		this.S = new Vector3(0,0,0);
		this.A = new Vector3(0,0,0);
	}
	
	public int getX() {
		return P.getX();
	}
	public int getY() {
		return P.getY();
	}
	public int getZ() {
		return P.getZ();
	}
	
	public void update(double Ts) {
		
	}
	
	public void render(Graphics g) {
		
	}
	
}
