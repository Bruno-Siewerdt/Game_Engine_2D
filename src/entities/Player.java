package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import game.Game;
import game.Vector3;
import game.Vector3.Axis;
import spritesheet.Spritesheet;

public class Player extends RigidBody {
	
	private final int PLAYER_SPEED = 4;
	private final int WALKING_ANIMATION_DELAY = 7;
	
	static Spritesheet spritesheet;
	BufferedImage sprites[][];
	private boolean walking;
	private int spriteIndex = 0;
	private int spriteChangeCount = 0;

	public Player(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
		begin();
	}
	
	public Player(Vector3 position, Vector3 dimensions, CollisionMask mask) {
		super(position, dimensions, mask);
		begin();
	}
	
	void begin() {
		spritesheet = new Spritesheet("/lpc_entry/png/walkcycle/BODY_skeleton.png");
		createSprites();
	}
	
	void createSprites() {
		sprites = new BufferedImage[4][9];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				sprites[i][j] = spritesheet.getSprite(j*64, i*64, 64, 64);
			}
		}
	}
	
	void setWalkingSprite() {
		if (speed.mod() > 0) { // if has speed
			if (!walking) {
				spriteIndex = 1;
				walking = true;
			}
			spriteChangeCount++;
			if (spriteChangeCount > WALKING_ANIMATION_DELAY) {
				spriteChangeCount = 0;
				spriteIndex++;
				if (spriteIndex >= 8) {
					spriteIndex = 0;
				}
			}
		} else {
			walking = false;
			spriteIndex = 0;
			spriteChangeCount = 0;
		}
	}
	
	@Override
	public void update(double Ts) {
		speed.integrate(acceleration, Ts);
		position.integrate(speed.mult(PLAYER_SPEED), Ts);
		
		resolveCollisions(Game.entities);
		
		setWalkingSprite();
		
		Game.camera.update(position, dimensions);
	}
	
	public void resolveCollisions(List<Entity> entities) {
        double playerHalfWidth =  this.getCollisionWidthD() * 0.5d;
        double playerHalfHeight =  this.getCollisionHeightD() * 0.5d;
        double playerCenterX = this.getCollisionXD() + playerHalfWidth;
        double playerCenterY = this.getCollisionYD() + playerHalfHeight;
 
        for (Entity entity : entities) {
            if (!entity.isSolid() || !this.isSolid()) {
            	continue;
            }
            
            final double entityHalfWidth = entity.getCollisionWidthD() * 0.5d;
            final double entityHalfHeight = entity.getCollisionHeightD() * 0.5d;
            final double entityCenterX = entity.getCollisionXD() + entityHalfWidth;
            final double entityCenterY = entity.getCollisionYD() + entityHalfHeight;
 
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
                this.position.add((dx > 0) ? overlapX : -overlapX, Axis.X);
                
                // Recalculate
                playerCenterX = this.getCollisionXD() + playerHalfWidth;
                playerCenterY = this.getCollisionYD() + playerHalfHeight;
            } else {
                this.position.add((dy > 0) ? overlapY : -overlapY, Axis.Y);
                
                // Recalculate
                playerCenterX = this.getCollisionXD() + playerHalfWidth;
                playerCenterY = this.getCollisionYD() + playerHalfHeight;
            }
        }
    }
	
	@Override
	public void render(Graphics g) {
		/*Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(250, 40, 40, 100));
		g2.fillRect(this.getCollisionX()-Game.camera.getX(), this.getCollisionY()-Game.camera.getY(), getCollisionWidth(), getCollisionHeight());*/
		g.drawImage(sprites[direction.getDirection()][spriteIndex], getX()-Game.camera.getX(), getY()-Game.camera.getY(), getWidth(), getHeight(), null);

	}

}
