package UI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.Vector3;

public class Button extends UIComponent {
	
	@FunctionalInterface
	interface Action {
	    void execute();
	}
	
	String content;
	Action action;
	
	public Button(String content, Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
		this.content = content;
		this.action = null;
	}
	
	public Button(String content, Vector3 position, Vector3 dimensions, Action action) {
		super(position, dimensions);
		this.content = content;
		this.action = action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	@Override
	public void checkSelection(int x, int y) {
		selected = isMouseOver(x, y) ? true : false;
	}
	
	@Override
	public void checkClick(int x, int y) {
		if (isMouseOver(x, y)) {
			action.execute();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(selected ? Color.CYAN : Color.BLUE);
		g.fillRect(position.getX(), position.getY(), dimensions.getX(), dimensions.getY());
		
		g.setColor(Color.BLACK);
		g.setFont(UI.fontButton);
		FontMetrics metrics = g.getFontMetrics(UI.fontTitle);
		g.drawString(content, position.getX() + dimensions.getX()/2 - metrics.stringWidth(content)/2, position.getY()+ dimensions.getY()/2+15);
	}
}
