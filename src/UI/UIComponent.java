package UI;

import java.awt.Graphics;

import game.Vector3;

public class UIComponent {
	
	boolean selected;
	Vector3 position, dimensions;

	public UIComponent(Vector3 position, Vector3 dimensions) {
		this.position = position;
		this.dimensions = dimensions;
	}
	
	boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX >= position.getX() && mouseX <= position.getX()+dimensions.getX() && mouseY >= position.getY() && mouseY <= position.getY()+dimensions.getY();
	}
	
	public void checkSelection(int x, int y) {
	}
	
	public void checkClick(int x, int y) {
	}
	
	public void render(Graphics g) {
	}
}
