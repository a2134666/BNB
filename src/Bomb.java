import java.awt.Graphics;
import javax.swing.ImageIcon;
import org.magiclen.media.AudioPlayer;

public class Bomb extends Item {
	private ImageIcon imgI1, imgI2;
	private Character player;
	private AudioPlayer boom;
	private boolean bang;
	private int count, row, col, sel, power;
	private static final int WIDTH = 64, BORDER = 30, life = 3000;
	
	
	public Bomb(Zone l, Character player, int p) {
		super(l, true);
		row = l.getRow();
		col = l.getCol();
		sel = 0;
		count = life;
		this.player = player;
		power = p;
		bang = false;
		boom = new AudioPlayer(getClass().getResource("爆炸.wav"));
		imgI1 = new ImageIcon(getClass().getResource("炸彈.png"));
		imgI2 = new ImageIcon(getClass().getResource("炸彈2.png"));
		// TODO Auto-generated constructor stub
	}
	
	public void count(int dt, Zone[][] block){		//bomb life
		count = count - dt;
		if(count <= 0){
			explosion(block);
			boom.play();
		}
		else if((count/500)%2 == 0){
			sel = 1;
		}
		else{
			sel = 0;
		}
	}
	public void explosion(Zone[][] block){
		bang = true;	//prevent infinite explosion call
		int u, d, l, r;
		u = explosionOneDirction(block, Pump.direction.up, 0, -1);		//get null blocks of every direction
		d =	explosionOneDirction(block, Pump.direction.down, 0, 1);
		l =	explosionOneDirction(block, Pump.direction.left, -1, 0);
		r = explosionOneDirction(block, Pump.direction.right, 1, 0);
		
		for(int i=1; i<u; i++){
			block[row-i][col].setItem(new Pump(block[row-i][col], Pump.direction.up));		//draw pump
		}
		if(u == power+1) block[row-power][col].setItem(new Pump(block[row-power][col], Pump.direction.upEdge));
		
		for(int i=1; i<d; i++){
			block[row+i][col].setItem(new Pump(block[row+i][col], Pump.direction.down));
		}
		if(d == power+1) block[row+power][col].setItem(new Pump(block[row+power][col], Pump.direction.downEdge));
		
		for(int i=1; i<l; i++){
			block[row][col-i].setItem(new Pump(block[row][col-i], Pump.direction.left));
		}
		if(l == power+1) block[row][col-power].setItem(new Pump(block[row][col-power], Pump.direction.leftEdge));
		
		for(int i=1; i<r; i++){
			block[row][col+i].setItem(new Pump(block[row][col+i], Pump.direction.right));
		}
		if(r == power+1) block[row][col+power].setItem(new Pump(block[row][col+power], Pump.direction.rightEdge));
		
		
		getLocation().setItem(new Pump(getLocation(), Pump.direction.center));
		player.setBomb(player.getBomb()+1);
	}
	public int explosionOneDirction(Zone[][] block, Pump.direction d, int x, int y){		//check bomb and box 
		for(int i=1; i<=power; i++){
			if(row+i*y<0 || row+i*y>12 || col+i*x<0 || col+i*x>14){
				return i;
			}
			else{
				if(block[row+i*y][col+i*x].getItem() != null){
					if(block[row+i*y][col+i*x].getItem().getClass().getName() == "Bomb"){
						if(!((Bomb)block[row+i*y][col+i*x].getItem()).bang){
							((Bomb)block[row+i*y][col+i*x].getItem()).explosion(block);
						}
					}
					else if(block[row+i*y][col+i*x].getItem().isWall()){
						if(block[row+i*y][col+i*x].getItem().getClass().getName() == "Box"){
							((Box)block[row+i*y][col+i*x].getItem()).setBang(true);
							
						}
						return i;		//return the blockth that can't set pump
					}
				}
			}
			
		}
		return power+1;		//if all clear
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		switch(sel){
		case 0:	g.drawImage(imgI1.getImage(), col*WIDTH+BORDER, row*WIDTH+BORDER+WIDTH-69, WIDTH, 69, null);
				break;
		case 1:	g.drawImage(imgI2.getImage(), col*WIDTH+BORDER-4, row*WIDTH+BORDER+WIDTH-50, WIDTH+6, 61, null);
		default:break;
		}
		
	}

}
