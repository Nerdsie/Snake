package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Apple {
	int x = 0, y = 0;
	public Color color;
	Snake game;
	
	public Apple(Snake g){
		game = g;
		color = Color.RED;
		regenApple();
	}
	
	public void render(Graphics g){
		g.setColor(color);
		//System.out.println(x + "-" + y);
		g.fillOval(x * 10, y * 10, 10, 10);
	}
	
	public void regenApple(){
		x = new Random().nextInt(Snake.wa);
		y = new Random().nextInt(Snake.ha);

		color = Color.RED;
		
		if(new Random().nextInt(10)==0)
			color = Color.YELLOW;
		if(new Random().nextInt(15)==0)
			color = Color.GRAY;
		if(new Random().nextInt(5)==0)
			color = Color.GREEN;
		
		for(SnakePart s: Snake.snake){
			if(s.x == x && s.y == y){
				regenApple();
				return;
			}
		}
	}
}
