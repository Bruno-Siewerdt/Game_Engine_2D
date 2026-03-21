package entities;

import java.awt.Graphics;

import game.Vector3;

public class Entity {
	
	public Vector3 P, S, A; // position, speed, acceleration
	public Vector3 dimensions;
	private boolean solid;
	
	public Entity(Vector3 position, Vector3 dimensions) {
		this.P = position;
		this.S = new Vector3(0,0,0);
		this.A = new Vector3(0,0,0);
		this.dimensions = dimensions;
		solid = true;
	}
	
	public final int getX() {
		return P.getX();
	}
	public final int getY() {
		return P.getY();
	}
	public final int getZ() {
		return P.getZ();
	}
	
	public final int getHeight() {
		return dimensions.getY();
	}
	public final int getWidth() {
		return dimensions.getX();
	}
	
	boolean isSolid() {
		return solid;
	}
	
	public void update(double Ts) {
		
	}
	
	public void render(Graphics g) {
		
	}
	
}
