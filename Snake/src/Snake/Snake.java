package Snake;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int wa = 64, ha = 48;
	public static final int scale = 10;
	int count = 0;
	
	public Color backColor = Color.BLACK;
	
	public static ArrayList<SnakePart> snake = new ArrayList<SnakePart>();
	public int[][] board = new int[wa][ha];
	public Apple apple;
	public boolean over = false;
	public int countdown = 0, speedCount = 0;
	public int speed, defSpeed = 8;

	public int defCount = 300;
	
	public int width = 640;
	public int height = 480;
	
	public Image backBuffer;
	public static Graphics canvas;
	
	public boolean running = false;
	
	public int toAdd = 0;
	
	public void init(){
		
	}
	
	public void start(){
		this.setSize( width, height );
		
		running = true;
		
		backBuffer = createImage(this.width, this.height);
		canvas = backBuffer.getGraphics();
		
		this.addKeyListener(new SnakeKeyListener(this));
		
		new Thread(this).start();

		this.requestFocus();
	}
	
	public void tick(){
		advance();
	}
	
	public int getSW(String s){
		FontMetrics FM = getFontMetrics(canvas.getFont());
		return FM.stringWidth(s);
	}
	
	public void drawCString(String m, int y){
		canvas.drawString(m, getWidth() / 2 - getSW(m) / 2, y);
	}
	
	public void drawCString(String m){
		
	}
	
	public void render(){
		canvas.setColor(backColor);
		canvas.fillRect(0, 0, width, height);
		
		if(over){
			canvas.setColor(Color.RED);
			canvas.setFont(new Font("Arial", 0, 30));
			String toDisp = "GAME OVER! SCORE: " + snake.size();
			drawCString(toDisp, getHeight() / 2 - 14);
			toDisp = "Press 'r' to Restart.";
			drawCString(toDisp, getHeight() / 2 + 14);
			
			return;
		}
		
		for(SnakePart s: snake){
			s.render(canvas);
		}
		
		canvas.setColor(Color.BLACK);		
		for(int x = 0; x < wa; x++){
			canvas.drawLine(x * 10, 0, x * 10, getHeight());
		}

		for(int y = 0; y < ha; y++){
			canvas.drawLine(0, y * 10, getWidth(), y * 10);
		}
		
		apple.render(canvas);
		
		canvas.setColor(Color.WHITE);
		canvas.setFont(new Font("Arial", 0, 30));
		canvas.drawString("Score: " + snake.size(), 2, 26);
	}
	
	public void run(){
		setup();
		
		while(running){
			tick();
			render();
			repaint();
			
			try{
				Thread.sleep(17);
			}catch(Exception e){}
		}
	}
	
	public void setup(){
		setSize(wa * scale, ha * scale);
		
		over = false;
		backColor = Color.BLACK;
		count = 0;
		speed = defSpeed;
		speedCount = 0;
		countdown = 0;
		snake.clear();
		snake.add(new SnakePart(this, 2, new Random().nextInt(ha - 2) + 1).setDir(1, 0));
		apple = new Apple(this);
		
		for(int x = 0; x < wa; x++){
			for(int y = 0; y < ha; y++){
				board[x][y] = 0;
			}
		}
	}
	
	public void gameEnd(){
		over = true;
	}
	
	public void checkLose(SnakePart s){
		if(s!=snake.get(0)){
			if(s.x==snake.get(0).x && s.y == snake.get(0).y){
				gameEnd();
				return;
			}
		}
		
		if(s.x >= wa || s.y >= ha || s.x < 0 || s.y < 0){
			gameEnd();
			return;
		}
	}
	
	public void updateDirection(SnakePart s){
		if(s.x >= wa || s.y >= ha || s.x < 0 || s.y < 0){
			gameEnd();
			return;
		}
		
		int dir = board[(int) s.x][(int) s.y];
		
		if(dir==1){
			s.xD = 0;
			s.yD = 1;
		}
		if(dir==2){
			s.xD = -1;
			s.yD = 0;
		}
		if(dir==3){
			s.xD = 0;
			s.yD = -1;
		}
		if(dir==4){
			s.xD = 1;
			s.yD = 0;
		}
		
		if(snake.get(snake.size() - 1) == s){
			board[(int) s.x][(int) s.y] = 0;
		}
	}
	
	public void updateLocation(SnakePart s){
		s.x+=s.xD;
		s.y+=s.yD;
	}
	
	public void addEndPart(){
		if(toAdd <= 0)
			return;
			
		toAdd--;
		SnakePart last = snake.get(snake.size() - 1);
		snake.add(new SnakePart(this, last.x - last.xD, last.y - last.yD).setDir(last.xD, last.yD));//add new snake part thing;
	}
	
	public void update(){
		for(int i = 0; i < snake.size(); i++){
			SnakePart s = snake.get(i);

			checkLose(s);
			updateDirection(s);
			updateLocation(s);
		}
		
		addEndPart();
	}
	
	public void checkEatApple(){
		if(snake.get(0).isOnApple()){
			int add = new Random().nextInt(3) + 1;
			
			if(apple.color == Color.YELLOW){
				add = 12;
			}
			if(apple.color == Color.GREEN){
				speed = 5;
				speedCount = defCount * 2;
			}
			if(apple.color == Color.GRAY){
				speed = 10;
				speedCount = defCount * 2;
			}
			
			apple.regenApple();
			
			toAdd = add;
		}
	}
	
	public void updateFlash(){
		if(snake.size() % 50 == 0){
			countdown = defCount;
		}
		
		if(countdown > 0){
			backColor = new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
			countdown--;
		}else{
			backColor = Color.BLACK;
		}
	}
	
	public void updateSpeed(){
		if(speedCount>0){
			speedCount--;
		}else{
			speed = defSpeed;
		}
	}
	
	public void advance(){
		if(over){
			//checkRestart();
			return;
		}
		
		updateFlash();
		updateSpeed();
		
		count++;
		
		if(count>speed){
			update();
			count = 0;
		}
		
		checkEatApple();
	}
	
	public void paint(Graphics g){
		g.drawImage(backBuffer, 0, 0, null);
	}
	
	public void update(Graphics g){	
		paint(g);
	}
}
