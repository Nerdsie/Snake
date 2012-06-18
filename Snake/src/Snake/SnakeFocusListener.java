package Snake;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SnakeFocusListener implements FocusListener{
	public Snake game;
	
	public SnakeFocusListener(Snake g){
		game = g;
	}
	
	public void focusGained(FocusEvent e) {
		game.focus = true;
	}

	public void focusLost(FocusEvent e) {
		game.focus = false;
	}

}
