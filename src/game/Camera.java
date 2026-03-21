package game;

import world.World;

public class Camera {
	
	private int x, y;
	
	public void update(Vector3 mainObjPosition, Vector3 mainObjDimensions) {
		x = mainObjPosition.getX() + mainObjDimensions.getX() - Game.WIDTH/2;
		y = mainObjPosition.getY() + mainObjDimensions.getY() - Game.HEIGHT/2;
		if (x < 0) {
			x = 0;
		} else if (x > World.getWidth() - Game.WIDTH) {
			x = World.getWidth() - Game.WIDTH;
		}
		if (y < 0) {
			y = 0;
		} else if (y > World.getHeight() - Game.HEIGHT) {
			y = World.getHeight() - Game.HEIGHT;
		}
	}
	
	public final int getX() {
		return x;
	}
	
	public final int getY() {
		return y;
	}
	
}
