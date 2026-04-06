package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import spritesheet.Spritesheet;

public class Clothing {
	
	public enum ClothingType {
		Head(0), Torso(1), Legs(2), Feet(3);
		
		private final int index;
		
		ClothingType(int index) {
	        this.index = index;
	    }
		
		public int getIndex() {
	        return this.index;
	    }
	}
	
	private static final String HEAD[]  = {"HEAD_chain_armor_helmet.png", "HEAD_chain_armor_hood.png", "HEAD_hair_blonde.png", "HEAD_leather_armor_hat.png", "HEAD_plate_armor_helmet.png", "HEAD_robe_hood.png"};	
	private static final String TORSO[] = {"TORSO_chain_armor_jacket_purple.png", "TORSO_chain_armor_torso.png", "TORSO_leather_armor_bracers.png", "TORSO_leather_armor_shirt_white.png", "TORSO_leather_armor_shoulders.png", 
										   "TORSO_leather_armor_torso.png", "TORSO_plate_armor_arms_shoulders.png", "TORSO_plate_armor_torso.png", "TORSO_robe_shirt_brown.png"};
	private static final String LEGS[]  = {"LEGS_pants_greenish.png", "LEGS_plate_armor_pants.png", "LEGS_robe_skirt.png"};
	private static final String FEET[]  = {"FEET_plate_armor_shoes.png", "FEET_shoes_brown.png"};
	
	private Spritesheet spritesheet;
	private BufferedImage sprites[][];
	private ClothingType type;
	private int clothingSelector = 0;
	
	public Clothing(ClothingType type) {
		this.type = type;
		createSprites();
	}
	
	void createSprites() {
		
		switch (type) {
		case Head:
			spritesheet = new Spritesheet("/lpc_entry/png/walkcycle/" + HEAD[clothingSelector]);
			break;
		case Torso:
			spritesheet = new Spritesheet("/lpc_entry/png/walkcycle/" + TORSO[clothingSelector]);
			break;
		case Legs:
			spritesheet = new Spritesheet("/lpc_entry/png/walkcycle/" + LEGS[clothingSelector]);
			break;
		default:
			spritesheet = new Spritesheet("/lpc_entry/png/walkcycle/" + FEET[clothingSelector]);
			break;
		}
		
		sprites = new BufferedImage[4][9];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				sprites[i][j] = spritesheet.getSprite(j*64, i*64, 64, 64);
			}
		}
	}
	
	public void changeClothing() {
		switch (type) {
		case Head:
			clothingSelector = clothingSelector == HEAD.length-1 ? 0 : clothingSelector+1;
			break;
		case Torso:
			clothingSelector = clothingSelector == TORSO.length-1 ? 0 : clothingSelector+1;
			break;
		case Legs:
			clothingSelector = clothingSelector == LEGS.length-1 ? 0 : clothingSelector+1;
			break;
		default:
			clothingSelector = clothingSelector == FEET.length-1 ? 0 : clothingSelector+1;
			break;
		}
		createSprites();
	}
	
	public void render(Graphics g, int x, int y, int width, int height, int index, int direction) {
		g.drawImage(sprites[direction][index], x, y, width, height, null);
	}
}