package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import game.Game;
import game.Vector3;
import game.Vector3.Axis;

public class Player extends RigidBody {
	
	private final int PLAYER_SPEED = 10;

	public Player(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
	}
	
	@Override
	public void update(double Ts) {
		S.addSpeed(A, Ts);
		P.addSpeed(S.mult(PLAYER_SPEED), Ts);
		
		resolveCollisions(Game.entities);
		
		Game.camera.update(P, dimensions);
	}
	
	public void resolveCollisions(List<Entity> entities) {
        final double playerHalfWidth = this.dimensions.getXD() * 0.5d;
        final double playerHalfHeight = this.dimensions.getYD() * 0.5d;
        final double playerCenterX = this.P.getXD() + playerHalfWidth;
        final double playerCenterY = this.P.getYD() + playerHalfHeight;
 
        for (Entity entity : entities) {
            if (!entity.isSolid() || !this.isSolid()) {
            	continue;
            }
            
            final double entityHalfWidth = entity.dimensions.getXD() * 0.5d;
            final double entityHalfHeight = entity.dimensions.getYD() * 0.5d;
            final double entityCenterX = entity.P.getXD() + entityHalfWidth;
            final double entityCenterY = entity.P.getYD() + entityHalfHeight;
 
            // Filter 1 - Bounding-sphere (without sqrt)
            double dx = playerCenterX - entityCenterX;
            double dy = playerCenterY - entityCenterY;
            double sumRadii  = playerHalfWidth + playerHalfHeight + entityHalfWidth + entityHalfHeight;
            if ((dx * dx + dy * dy) > (sumRadii * sumRadii)) {
            	continue;
            }
            
            // Filter 2 - axis-aligned bounding boxes (AABB)
            double overlapX = (playerHalfWidth + entityHalfWidth) - Math.abs(dx);
            if (overlapX <= 0) {
            	continue;  
            }
            double overlapY = (playerHalfHeight + entityHalfHeight) - Math.abs(dy);
            if (overlapY <= 0) {
            	continue;  
            }
        	
            // Filter 3 - Correction of Player position
            if (overlapX < overlapY) {
                this.P.add((dx > 0) ? overlapX : -overlapX, Axis.X);
            } else {
                this.P.add((dy > 0) ? overlapY : -overlapY, Axis.Y);
            }
        }
    }
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(250, 40, 40));
		g.fillRect(getX()-Game.camera.getX(), getY()-Game.camera.getY(), getWidth(), getHeight());
	}

}
