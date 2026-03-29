package UI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.Vector3;

public class Card extends UIComponent{
	
	String title;

	public Card(Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
		this.title = "";
	}
	
	public Card(String title, Vector3 position, Vector3 dimensions) {
		super(position, dimensions);
		this.title = title;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(position.getX(), position.getY(), dimensions.getX(), dimensions.getY());
		if (title != "") {
			g.setColor(Color.BLACK);
			g.setFont(UI.fontTitle);
			FontMetrics metrics = g.getFontMetrics(UI.fontTitle);
			g.drawString(title, position.getX() + dimensions.getX()/2 - metrics.stringWidth(title)/2, position.getY()+50);
		}
	}

}
