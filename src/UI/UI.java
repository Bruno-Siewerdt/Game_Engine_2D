package UI;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import entities.Clothing.ClothingType;
import game.Game;
import game.Game.GameState;
import game.Vector3;

public class UI {
	
	private List<UIComponent> components;
	
	public static Font fontTitle = new Font("Verdana", Font.BOLD, 40);
	public static Font fontButton = new Font("Verdana", Font.BOLD, 40);
	
	public UI() {
		components = new ArrayList<UIComponent>();
	}
	
	public void checkSelections(int x, int y) {
		for (UIComponent component : components) {
			component.checkSelection(x, y);
		}
	}
	
	public void checkClicks(int x, int y) {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).checkClick(x, y);
		}
	}
	
	public void setState(GameState state) {
		if (Game.game.getState() == GameState.RUNNING) {
			components.clear();
		} else if (Game.game.getState() == GameState.PAUSED) {
			components.add(new   Card("Menu",     new Vector3(Game.WIDTH/2-300, Game.HEIGHT/2 - 400, 0), new Vector3(600, 700, 0)));
			components.add(new Button("Continue", new Vector3(Game.WIDTH/2-200, Game.HEIGHT/2 - 300, 0), new Vector3(400, 100, 0), () -> {Game.game.setState(GameState.RUNNING);}));
			components.add(new Button("Head",     new Vector3(Game.WIDTH/2-200, Game.HEIGHT/2 - 150, 0), new Vector3(180, 100, 0), () -> {Game.player.changeClothing(ClothingType.Head);}));
			components.add(new Button("Torso",    new Vector3(Game.WIDTH/2+ 20, Game.HEIGHT/2 - 150, 0), new Vector3(180, 100, 0), () -> {Game.player.changeClothing(ClothingType.Torso);}));
			components.add(new Button("Legs",     new Vector3(Game.WIDTH/2-200, Game.HEIGHT/2 +   0, 0), new Vector3(180, 100, 0), () -> {Game.player.changeClothing(ClothingType.Legs);}));
			components.add(new Button("Feet",     new Vector3(Game.WIDTH/2+ 20, Game.HEIGHT/2 +   0, 0), new Vector3(180, 100, 0), () -> {Game.player.changeClothing(ClothingType.Feet);}));
			components.add(new Button("Quit",     new Vector3(Game.WIDTH/2-200, Game.HEIGHT/2 + 150, 0), new Vector3(400, 100, 0), () -> {Game.game.stop();}));
		}
	}
	
	public void render(Graphics g) {
		for (UIComponent component : components) {
			component.render(g);
		}
	}
}
