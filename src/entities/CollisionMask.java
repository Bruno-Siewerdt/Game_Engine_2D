package entities;

import game.Vector3;

public class CollisionMask {
	
	private Vector3 P, dimensions;

	public CollisionMask(Vector3 P, Vector3 dimensions) {
		this.P = P;
		this.dimensions = dimensions;
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
	
	public double getXD() {
		return P.getXD();
	}
	public double getYD() {
		return P.getYD();
	}
	public double getZD() {
		return P.getZD();
	}
	
	public final int getHeight() {
		return dimensions.getY();
	}
	public final int getWidth() {
		return dimensions.getX();
	}
	
	public final double getHeightD() {
		return dimensions.getYD();
	}
	public final double getWidthD() {
		return dimensions.getXD();
	}
	
}
