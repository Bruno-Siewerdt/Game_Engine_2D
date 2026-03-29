package entities;

import java.awt.Graphics;

import game.Vector3;
import game.Vector3.Axis;

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
	
	protected Vector3 position, speed, acceleration;
	protected Vector3 dimensions;
	private boolean solid;
	protected Direction direction;
	CollisionMask collisionMask;
	
	public Entity(Vector3 position, Vector3 dimensions) {
		this.position = position;
		this.speed = new Vector3(0,0,0);
		this.acceleration = new Vector3(0,0,0);
		this.dimensions = dimensions;
		this.collisionMask = new CollisionMask(new Vector3(0,0,0), dimensions);
		solid = true;
		direction = Direction.RIGHT;
	}
	
	public Entity(Vector3 position, Vector3 dimensions, CollisionMask mask) {
		this.position = position;
		this.speed = new Vector3(0,0,0);
		this.acceleration = new Vector3(0,0,0);
		this.dimensions = dimensions;
		this.collisionMask = mask;
		solid = true;
		direction = Direction.RIGHT;
	}
	
	// Position
	
	public final int getX() {
		return position.getX();
	}
	public final int getY() {
		return position.getY();
	}
	public final int getZ() {
		return position.getZ();
	}
	
	public final int getHeight() {
		return dimensions.getY();
	}
	public final int getWidth() {
		return dimensions.getX();
	}
	
	// Speed
	
	public final int getSpeedX() {
		return speed.getX();
	}
	public final int getSpeedY() {
		return speed.getY();
	}
	public final int getSpeedZ() {
		return speed.getZ();
	}
	
	public void addSpeed(Vector3 speed) {
		this.speed.integrate(speed, 1);
	}
	
	public void addSpeedX(double speed) {
		this.speed.add(speed, Axis.X);
	}
	
	public void addSpeedY(double speed) {
		this.speed.add(speed, Axis.Y);
	}
	
	public void addSpeedZ(double speed) {
		this.speed.add(speed, Axis.Z);
	}
	
	public void setSpeed(Vector3 speed) {
		this.speed = speed;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	// Collisions
	
	public final int getCollisionX() {
		return position.getX()+collisionMask.getX();
	}
	public final int getCollisionY() {
		return position.getY()+collisionMask.getY();
	}
	public final int getCollisionZ() {
		return position.getZ()+collisionMask.getZ();
	}
	
	public final double getCollisionXD() {
		return position.getXD()+collisionMask.getXD();
	}
	public final double getCollisionYD() {
		return position.getYD()+collisionMask.getYD();
	}
	public final double getCollisionZD() {
		return position.getZD()+collisionMask.getZD();
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
