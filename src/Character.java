import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import org.magiclen.media.AudioPlayer;

public class Character {
	private int x, y;
	private int power, ballnum, bomb;
	private double speed;
	private boolean moveU, moveD, moveL, moveR, enable;
	private state mode;
	
	private Color color;
	private ImageIcon[] red = new ImageIcon[5], blue = new ImageIcon[5];
	private Zone location;
	private AudioPlayer putBomb;
	
	private static final int WIDTH = 64, HEIGHT = 90;
	public static final int powerMax = 7, ballnumMax = 6;
	public static final double speedMax = 0.65;
	
	public enum state{
		up(0), 
		down(1), 
		left(2), 
		right(3),
		drown(4);
		
		private int value;
		private state(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	};
	
	public Character(int x, int y, Color c){
		this.x = x;
		this.y = y;
		speed = 0.25;
		power = 1;
		ballnum = 1;
		bomb = ballnum;
		color = c;
		mode = state.down;
		putBomb = new AudioPlayer(getClass().getResource("放炸彈.wav"));
		moveU = false;
		moveD = false;
		moveL = false;
		moveR = false;
		enable = true;
		
		red[0] = new ImageIcon(getClass().getResource("背.png"));
		red[1] = new ImageIcon(getClass().getResource("正.png"));
		red[2] = new ImageIcon(getClass().getResource("左.png"));
		red[3] = new ImageIcon(getClass().getResource("右.png"));
		red[4] = new ImageIcon(getClass().getResource("困.png"));
		
		blue[0] = new ImageIcon(getClass().getResource("背_2.png"));
		blue[1] = new ImageIcon(getClass().getResource("正_2.png"));
		blue[2] = new ImageIcon(getClass().getResource("左_2.png"));
		blue[3] = new ImageIcon(getClass().getResource("右_2.png"));
		blue[4] = new ImageIcon(getClass().getResource("困_2.png"));
	}
	
	public double getSpeed() {
		return speed;
	}
	/*public void setSpeed(double speed) {
		if(speed <= speedMax && speed > 0){
			this.speed = speed;
		}
	}
	public void setPower(int power) {
		if(power <= powerMax && power > 0){
			this.power = power;
		}
	}
	public void setBallnum(int balloon) {
		if(balloon <= ballnumMax && balloon > 0){
			ballnum = balloon;
			bomb = balloon;
		}
	}*/
	public void setBomb(int bomb) {
		this.bomb = bomb;
	}
	public int getBomb() {
		return bomb;
	}

	
	public Zone getLocation() {
		return location;
	}
	public void setLocation(Zone location) {
		this.location = location;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public Color getColor(){		//color determine player#
		return color;
	}
	public void setMode(state mode){		//0_up 1_down 2_left 3_right
		if(enable){
			this.mode = mode;
		}
	}
	public state getMode() {
		return mode;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	
	public void setMoveU(boolean moveU) {
		this.moveU = moveU;
	}

	public void setMoveD(boolean moveD) {
		this.moveD = moveD;
	}

	public void setMoveL(boolean moveL) {
		this.moveL = moveL;
	}

	public void setMoveR(boolean moveR) {
		this.moveR = moveR;
	}

	
	public void draw(Graphics g){		//coordinate base on rightbottom
		if(color == color.BLUE){
			if(mode == state.drown){
				g.drawImage(blue[mode.getValue()].getImage(), x-WIDTH-16, y-HEIGHT, WIDTH+32, 101, null);
			}
			else{
				g.drawImage(blue[mode.getValue()].getImage(), x-WIDTH, y-HEIGHT, WIDTH, HEIGHT, null);
			}
		}
		else{
			if(mode == state.drown){
				g.drawImage(red[mode.getValue()].getImage(), x-WIDTH-16, y-HEIGHT, WIDTH+32, 101, null);
			}
			else{
				g.drawImage(red[mode.getValue()].getImage(), x-WIDTH, y-HEIGHT, WIDTH, HEIGHT, null);
			}
		}
	}
	public void move(int dt, GamePanel game){		//use border of every side_64*64
		if((moveU || moveD || moveL || moveR) && enable){
			switch(mode){
			case up:	if(game.nextStep(x-WIDTH/2, (int)(y-WIDTH - speed*dt), this)){
							y = (int)(y - speed*dt);
						}
						break;
			case down:	if(game.nextStep(x-WIDTH/2, (int)(y + speed*dt), this)){
							y = (int)(y + speed*dt);
						}
						break;
			case left:	if(game.nextStep((int)(x-WIDTH - speed*dt), y-WIDTH/2, this)){
							x = (int)(x - speed*dt);
						}
						break;
			case right:	if(game.nextStep((int)(x + speed*dt), y-WIDTH/2, this)){
							x = (int)(x + speed*dt);
						}
						break;
			default:break;
			}
		}
	}
	public void checkBuff(){		//get BuffItem
		if(location.getItem().getClass().getName() == "BuffItem"){
			switch(((BuffItem) location.getItem()).getSel()){
			case shoe:	if(speed < speedMax){
							speed += BuffItem.dv;
						}
						break;
			case ball:	if(ballnum < ballnumMax){
							ballnum += BuffItem.dn;
							bomb += BuffItem.dn;
						}
						break;
			case vase:	if(power < powerMax){
							power += BuffItem.dp;
						}
						break;
			case bone:	power = powerMax;
						break;
			default:break;
			}
			location.setItem(null);
		}
	}
	public void putBomb(){
		if(bomb > 0 && location.getItem() == null && enable){
			location.setItem(new Bomb(location, this, power));
			putBomb.play();
			bomb--;
		}
	}
}
