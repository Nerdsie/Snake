package Snake;

import java.awt.Color;
import java.awt.Graphics;

public class SnakePart{
	int x = 0, y = 0, xD = 0, yD = 0;
	public Snake game;
	public Color color;
	
	public SnakePart(Snake g, int x, int y) {
		game = g;
		color = Color.CYAN;
		
		this.x = x;
		this.y = y;
	}
	
	public SnakePart(Snake g, int x, int y, Color c) {
		game = g;
		
		color = c;
		
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillRect(x * 10, y * 10, 10, 10);
	}
	
	public SnakePart setDir(int x, int y){
		xD = x;
		yD = y;
		
		return this;
	}
	
	public int getID(){
		int id = 0;
		
		return id;
	}
	
	public boolean isOnApple(){
		if(x == game.apple.x && y == game.apple.y){
			return true;
		}
		
		return false;
	}
}
