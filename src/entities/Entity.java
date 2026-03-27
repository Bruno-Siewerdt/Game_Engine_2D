package entities;

import java.awt.Graphics;

import game.Vector3;

public class Entity {
	
	public enum Direction {
		UP(0), LEFT(1), DOWN(2), RIGHT(3);
		
		private final int direction;
		
		Direction(int direction) {
	        this.direction = direction;
	    }
		
		public int getDirection() {
	        return this.direction;
	    }
	}
	
	public Vector3 P, S, A; // position, speed, acceleration
	public Vector3 dimensions;
	private boolean solid;
	public Direction direction;
	CollisionMask collisionMask;
	
	public Entity(Vector3 position, Vector3 dimensions) {
		this.P = position;
		this.S = new Vector3(0,0,0);
		this.A = new Vector3(0,0,0);
		this.dimensions = dimensions;
		this.collisionMask = new CollisionMask(new Vector3(0,0,0), dimensions);
		solid = true;
		direction = Direction.RIGHT;
	}
	
	public Entity(Vector3 position, Vector3 dimensions, CollisionMask mask) {
		this.P = position;
		this.S = new Vector3(0,0,0);
		this.A = new Vector3(0,0,0);
		this.dimensions = dimensions;
		this.collisionMask = mask;
		solid = true;
		direction = Direction.RIGHT;
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
	
	// Collisions
	
	public final int getCollisionX() {
		return P.getX()+collisionMask.getX();
	}
	public final int getCollisionY() {
		return P.getY()+collisionMask.getY();
	}
	public final int getCollisionZ() {
		return P.getZ()+collisionMask.getZ();
	}
	
	public final double getCollisionXD() {
		return P.getXD()+collisionMask.getXD();
	}
	public final double getCollisionYD() {
		return P.getYD()+collisionMask.getYD();
	}
	public final double getCollisionZD() {
		return P.getZD()+collisionMask.getZD();
	}
	
	public final int getCollisionHeight() {
		return collisionMask.getHeight();
	}
	public final int getCollisionWidth() {
		return collisionMask.getWidth();
	}
	
	public final double getCollisionHeightD() {
		return collisionMask.getHeightD();
	}
	public final double getCollisionWidthD() {
		return collisionMask.getWidthD();
	}
	
	// ------------------------
	
	public boolean isSolid() {
		return solid;
	}
	
	public void setDirection(Direction dir) {
		direction = dir;
	}
	
	public void update(double Ts) {
		
	}
	
	public void render(Graphics g) {
		
	}
	
}
