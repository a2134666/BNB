import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.magiclen.media.AudioPlayer;

public class GamePanel extends JPanel {
	private Zone[][] block = new Zone[13][15];
	private BufferedImage scene, image;
	private Graphics pen;
	private ImageIcon picture;
	private Character player1, player2;
	private Timer play;
	private AudioPlayer bgm;
	private int width, height, count;
	private type result;
	private boolean pause, gameover;
	
	public static final int WIDTH = 1020, HEIGHT = 892, dt = 16, BORDER = 30;
	public enum type{
		dual, red, blue
	}
	
	public GamePanel(int w, int h){		//constructor
		width = w;
		height = h;
		count = 5000;
		pause = true;
		gameover = false;
		
		image = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);		//initial scene
		pen = image.getGraphics();
		picture = new ImageIcon(getClass().getResource("Initial.png"));
		
		pen.drawImage(picture.getImage(), 0, 0, GamePanel.WIDTH, 709, null);
		pen.setColor(Color.CYAN);
		pen.fillRect(0, 709, GamePanel.WIDTH, GamePanel.HEIGHT-709);
		pen.setColor(Color.red);
		pen.setFont(new Font("標楷體", Font.PLAIN, 52));
		pen.drawString("玩家１：↑↓←→", 60, 775);
		pen.drawString("玩家２：ＲＦＤＧ", 536, 775);
		pen.setFont(new Font("標楷體", Font.PLAIN, 40));
		pen.drawString("放炸彈：Shift", 45, 850);
		pen.drawString("暫停：ＥＳＣ", 350, 850);
		pen.drawString("開始／繼續：Enter", 635, 850);
		
		scene = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		//game scene
		pen = scene.getGraphics();
		
		for(int i=0; i<13; i++){		//yellow background
			for(int j=0; j<15; j++){
				block[i][j] = new Zone(j*Zone.FRAME+BORDER, i*Zone.FRAME+BORDER, i, j, Color.yellow, null);
			}
		}
		for(int i=0; i<13; i++){		//orange cross
			for(int j=0; j<15; j+=2){
				if(j + (i%2) < 15){
					block[i][j + (i%2)].setColor(Color.orange);
				}
			}
		}
		for(int i=0; i<13; i++){		//gray road
			for(int j=6; j<=8; j++){
				block[i][j].setColor(Color.GRAY);
			}
		}
		map();
		bgm = new AudioPlayer(getClass().getResource("BGM.wav"));
		
		player1 = new Character(94, 94, Color.red);
		player2 = new Character(990, 862, Color.blue);
//		player1.setPower(Character.powerMax);
//		player1.setSpeed(Character.speedMax);
//		player1.setBallnum(Character.ballnumMax);
		
		play = new Timer(dt, new Listener(this));
		play.start();
		addKeyListener(new Key());
		setFocusable(true);
	}
	public void paintComponent(Graphics g){
		if(pause){
			g.drawImage(image, 0, 0, width, height, null);
		}
		else{
			g.drawImage(scene, 0, 0, width, height, null);
		}
		
	}
	
	
	private class Listener implements ActionListener{		//pass nextStep() to Character
		private GamePanel game;
		public Listener(GamePanel gamePanel) {
			super();
			game = gamePanel;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {		//update
			// TODO Auto-generated method stub
			if(!bgm.getStatus().equals(AudioPlayer.Status.START)) bgm.play();
			if(!pause){
				paint();
				zoneNum(player1);
				player1.move(dt, game);
				zoneNum(player2);
				player2.move(dt, game);
				update();
				
				if(gameover){
					switch(result){
					case dual:	pen.setColor(Color.white);
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("平手！！", 259, 382);
								pen.setColor(Color.green);
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("平手！！", 254, 382);
								break;
					case red:	pen.setColor(Color.white);
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("玩家１勝利！！", 67, 382);
								pen.setColor(Color.red);
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("玩家１勝利！！", 62, 382);
								break;
					case blue:	pen.setColor(Color.white);
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("玩家２勝利！！", 67, 382);
								pen.setColor(new Color(30, 144, 255));
								pen.setFont(new Font("標楷體", Font.BOLD, 128));
								pen.drawString("玩家２勝利！！", 62, 382);
								break;
					default:break;
					}
					count -= dt;
					if(count <= 0){
						pause = true;
					}
				}
			}
			
			repaint();
		}
		
	}
	private class Key extends KeyAdapter{
		public void keyPressed(KeyEvent e){		//keyboard input
			if(!pause){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:player1.setMode(Character.state.up);
									player1.setMoveU(true);
									break;
				case KeyEvent.VK_DOWN:	player1.setMode(Character.state.down);
										player1.setMoveD(true);
										break;
				case KeyEvent.VK_LEFT:	player1.setMode(Character.state.left);
										player1.setMoveL(true);
										break;
				case KeyEvent.VK_RIGHT:	player1.setMode(Character.state.right);
										player1.setMoveR(true);
										break;
				case KeyEvent.VK_SHIFT:	if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT){
											player2.putBomb();
										}
										else if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT){
											player1.putBomb();
										}
										break;
				case KeyEvent.VK_R:	player2.setMode(Character.state.up);
									player2.setMoveU(true);
									break;
				case KeyEvent.VK_F:	player2.setMode(Character.state.down);
									player2.setMoveD(true);
									break;
				case KeyEvent.VK_D:	player2.setMode(Character.state.left);
									player2.setMoveL(true);
									break;
				case KeyEvent.VK_G:	player2.setMode(Character.state.right);
									player2.setMoveR(true);
									break;
				case KeyEvent.VK_ESCAPE:	pause = true;
											break;
				default:break;
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(gameover){
					restart();
				}
				else{
					pause = false;
				}
			}
		}
		public void keyReleased(KeyEvent e){
			switch(e.getKeyCode()){
			case KeyEvent.VK_UP:player1.setMoveU(false);
								break;
			case KeyEvent.VK_DOWN:	player1.setMoveD(false);
									break;
			case KeyEvent.VK_LEFT:	player1.setMoveL(false);
									break;
			case KeyEvent.VK_RIGHT:	player1.setMoveR(false);
									break;
			case KeyEvent.VK_R:	player2.setMoveU(false);
								break;
			case KeyEvent.VK_F:	player2.setMoveD(false);
								break;
			case KeyEvent.VK_D:	player2.setMoveL(false);
								break;
			case KeyEvent.VK_G:	player2.setMoveR(false);
								break;						
			default:break;
			}
		}
	}
	
	
	public void map(){
		for(int i=1; i<=5; i+=2){
			for(int j=1; j<=3; j+=2){
				block[i][j].setItem(new Barrier(block[i][j], Barrier.type.houseRed));		//barrier
			}
		}
		for(int i=7; i<=11; i+=2){
			for(int j=11; j<=13; j+=2){
				block[i][j].setItem(new Barrier(block[i][j], Barrier.type.houseRed));
			}
		}
		for(int i=0; i<=4; i+=2){
			for(int j=10; j<=14; j+=2){
				block[i][j].setItem(new Barrier(block[i][j], Barrier.type.houseYellow));
			}
		}
		for(int i=8; i<=12; i+=2){
			for(int j=0; j<=4; j+=2){
				block[i][j].setItem(new Barrier(block[i][j], Barrier.type.houseBlue));
			}
		}
		
		for(int i=1; i<=5; i+=2){
			block[i][5].setItem(new Barrier(block[i][5], Barrier.type.tree));
		}
		for(int i=7; i<=11; i+=2){
			block[i][9].setItem(new Barrier(block[i][9], Barrier.type.tree));
		}
		for(int j=0; j<=4; j+=2){
			block[6][j].setItem(new Barrier(block[6][j], Barrier.type.tree));
		}
		for(int j=10; j<=14; j+=2){
			block[6][j].setItem(new Barrier(block[6][j], Barrier.type.tree));
		}
		block[9][5].setItem(new Barrier(block[9][5], Barrier.type.tree));
		block[11][5].setItem(new Barrier(block[11][5], Barrier.type.tree));
		block[1][9].setItem(new Barrier(block[1][9], Barrier.type.tree));
		block[3][9].setItem(new Barrier(block[3][9], Barrier.type.tree));
		
		
		for(int i=1; i<=5; i+=2){
			for(int j=2; j<=4; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeO));		//box_leftup
			}
		}
		for(int i=0; i<=4; i+=2){
			for(int j=2; j<=4; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeR));
			}
		}
		for(int i=0; i<=4; i+=2){
			for(int j=1; j<=5; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.box));
			}
		}
		block[2][1].setItem(null);
		block[3][0].setItem(new Box(block[3][0], Box.type.cubeO));
		block[4][0].setItem(new Box(block[4][0], Box.type.cubeR));
		block[5][0].setItem(new Box(block[5][0], Box.type.cubeO));
		
		
		for(int i=7; i<=11; i+=2){
			for(int j=10; j<=12; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeO));		//box_rightbottom
			}
		}
		for(int i=8; i<=12; i+=2){
			for(int j=10; j<=12; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeR));
			}
		}
		for(int i=8; i<=12; i+=2){
			for(int j=9; j<=13; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.box));
			}
		}
		block[12][13].setItem(null);
		block[7][14].setItem(new Box(block[7][14], Box.type.cubeO));
		block[8][14].setItem(new Box(block[8][14], Box.type.cubeR));
		block[9][14].setItem(new Box(block[9][14], Box.type.cubeO));
		
		
		for(int i=3; i<=5; i+=2){
			for(int j=10; j<=14; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeO));		//box_rightup
			}
		}
		for(int i=3; i<=5; i+=2){
			for(int j=11; j<=13; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeR));
			}
		}
		for(int i=2; i<=6; i+=2){
			for(int j=11; j<=13; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.box));
			}
		}
		block[1][10].setItem(new Box(block[1][10], Box.type.cubeO));
		block[1][11].setItem(new Box(block[1][11], Box.type.cubeR));
		block[0][11].setItem(new Box(block[0][11], Box.type.box));
		
		
		for(int i=7; i<=11; i+=2){
			for(int j=0; j<=4; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeO));		//box_leftbottom
			}
		}
		for(int i=7; i<=9; i+=2){
			for(int j=1; j<=3; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.cubeR));
			}
		}
		for(int i=6; i<=8; i+=2){
			for(int j=1; j<=3; j+=2){
				block[i][j].setItem(new Box(block[i][j], Box.type.box));
			}
		}
		block[11][0].setItem(null);
		block[10][3].setItem(new Box(block[10][3], Box.type.box));
		block[11][3].setItem(new Box(block[11][3], Box.type.cubeR));
		block[12][3].setItem(new Box(block[12][3], Box.type.box));
	}
	public void paint(){		
		pen.setColor(Color.CYAN);
		pen.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i=0; i<13; i++){		//paint background
			for(int j=0; j<15; j++){
				block[i][j].draw(pen);
			}
		}
		pen.setColor(Color.WHITE);		//road line
		for(int y=45; y<=813; y+=Zone.FRAME){
			pen.fillRect(505, y, 10, 34);
		}
		
		for(int i=0; i<13; i++){		//paint item and player from rightup to leftbottom
			for(int j=14; j>=0; j--){
				block[i][j].drawItem(pen);
				if(block[i][j].isPlayer1()){
					player1.draw(pen);
				}
				if(block[i][j].isPlayer2()){
					player2.draw(pen);
				}
			}
		}
	}
	
	
	public void zoneNum(Character player){		//determine player's location
		if(player.getLocation() != null){
			if(player.getColor() == Color.blue){
				player.getLocation().setPlayer2(false);
			}
			else{
				player.getLocation().setPlayer1(false);
			}
		}
		
		int r, c;
		r = (player.getY()-BORDER) / Zone.FRAME;
		if((player.getY()-BORDER)%Zone.FRAME < (Zone.FRAME/2)){
			r = r-1;
		}
		c = (player.getX()-BORDER) / Zone.FRAME;
		if(((player.getX()-BORDER)%Zone.FRAME) < (Zone.FRAME/2)){
			c = c-1;
		}
		
		player.setLocation(block[r][c]);
		if(player.getColor() == Color.blue){
			block[r][c].setPlayer2(true);
		}
		else{
			block[r][c].setPlayer1(true);
		}
		
		if(player.getLocation().getItem() != null){
			player.checkBuff();
		}
	}
	public boolean nextStep(int x, int y, Character player){		//moveable_map edge, barrier and bomb_use center and border
		int r, c;
		r = (y-BORDER) / Zone.FRAME;
		c = (x-BORDER) / Zone.FRAME;
		
		if(x <= BORDER){
			player.setX(Zone.FRAME+BORDER);
			return false;
		}
		else if(x >= WIDTH-BORDER){
			player.setX(WIDTH-BORDER);
			return false;
		}
		else if(y <= BORDER){
			player.setY(Zone.FRAME+BORDER);
			return false;
		}
		else if(y >= HEIGHT-BORDER){
			player.setY(HEIGHT-BORDER);
			return false;
		}
		else{
			if(block[r][c].getItem() == null){
				switch(player.getMode()){
				case up:
				case down:	if(player.getX() == player.getLocation().getX()+Zone.FRAME) return true;		//corner adjustment
							if(isBarrier(x+Zone.FRAME/2, y)){
								if(isBarrier(x+Zone.FRAME/2-(int)(dt*player.getSpeed()), y)){
									player.setX(player.getX()-(int)(dt*player.getSpeed()));
								}
								else{
									player.setX(player.getLocation().getX()+Zone.FRAME);
								}
								return false;
							}
							if(isBarrier(x-Zone.FRAME/2, y)){
								if(isBarrier(x-Zone.FRAME/2+(int)(dt*player.getSpeed()), y)){
									player.setX(player.getX()+(int)(dt*player.getSpeed()));
								}
								else{
									player.setX(player.getLocation().getX()+Zone.FRAME);
								}
								return false;
							}
							break;
				case left:
				case right:	if(player.getY() == player.getLocation().getY()+Zone.FRAME) return true;
							if(isBarrier(x, y+Zone.FRAME/2)){
								if(isBarrier(x, y+Zone.FRAME/2-(int)(dt*player.getSpeed()))){
									player.setY(player.getY()-(int)(dt*player.getSpeed()));
								}
								else{
									player.setY(player.getLocation().getY()+Zone.FRAME);
								}
								return false;
							}
							if(isBarrier(x, y-Zone.FRAME/2)){
								if(isBarrier(x, y-Zone.FRAME/2+(int)(dt*player.getSpeed()))){
									player.setY(player.getY()+(int)(dt*player.getSpeed()));
								}
								else{
							player.setY(player.getLocation().getY()+Zone.FRAME);
								}
								return false;
							}
							break;
				default:break;
				}
				return true;
			}
			else if(player.getLocation().getItem() != null && player.getLocation() == block[r][c]){		//stand on bomb
				return true;
			}
			else if(block[r][c].getItem().isWall()){		//offset
				switch(player.getMode()){
				case up:	player.setY(block[r][c].getY()+Zone.FRAME*2);
							break;
				case down:	player.setY(block[r][c].getY());
							break;
				case left:	player.setX(block[r][c].getX()+Zone.FRAME*2);
							break;
				case right:	player.setX(block[r][c].getX());
							break;
				default:break;
				}
				return false;
			}
			else{
				
				return true;
			}
		}
		
	}
	private boolean isBarrier(int x, int y){		//determine if an input point inside a barrier_left up border
		int r, c;
		r = (y-BORDER) / Zone.FRAME;
		c = (x-BORDER) / Zone.FRAME;
		if(block[r][c].getItem() == null){
			return false;
		}
		else{
			return block[r][c].getItem().isWall();
		}
	}
	
	
	public void update(){		//count life
		for(int i=0; i<13; i++){
			for(int j=0; j<15; j++){
				if(block[i][j].getItem() != null){
					if(block[i][j].getItem().getClass().getName() == "Bomb"){
						((Bomb)block[i][j].getItem()).count(dt, block);
					}
					else if(block[i][j].getItem().getClass().getName() == "Pump"){
						((Pump)block[i][j].getItem()).count(dt, player1, player2, this);
					}
					else if(block[i][j].getItem().getClass().getName() == "Box"){
						if(((Box)block[i][j].getItem()).isBang()){
							((Box)block[i][j].getItem()).count(dt);
						}
					}
				}
			}
		}
	}
	public void result(){
		if(player1.getMode() == Character.state.drown && player2.getMode() == Character.state.drown){		//check if gameover
			result = type.dual;
			gameover = true;
		}
		else if(player2.getMode() == Character.state.drown){
			result = type.red;
			player1.setEnable(false);
			gameover = true;
		}
		else if(player1.getMode() == Character.state.drown){
			result = type.blue;
			player2.setEnable(false);
			gameover = true;
		}
	}
	public void restart(){		//new game
		player1 = new Character(94, 94, Color.red);
		player2 = new Character(990, 862, Color.blue);
		map();
		pause = false;
		gameover = false;
		count = 5000;
	}
}
