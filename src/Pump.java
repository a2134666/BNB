import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Pump extends Item {
	private int count, row, col;
	private direction sel;
	private ImageIcon[] imgI = new ImageIcon[9];
	private static final int FRAME = 64, BORDER = 30, life = 500;
	
	public enum direction{
		center, up, upEdge, down, downEdge, left, leftEdge, right, rightEdge
	};
	public Pump(Zone l, direction s) {
		super(l, false);
		count = life;
		row = l.getRow();
		col = l.getCol();
		sel = s;
		imgI[0] = new ImageIcon(getClass().getResource("水柱_中.png"));
		imgI[1] = new ImageIcon(getClass().getResource("水柱_上.png"));
		imgI[2] = new ImageIcon(getClass().getResource("水柱_上底.png"));
		imgI[3] = new ImageIcon(getClass().getResource("水柱_下.png"));
		imgI[4] = new ImageIcon(getClass().getResource("水柱_下底.png"));
		imgI[5] = new ImageIcon(getClass().getResource("水柱_左.png"));
		imgI[6] = new ImageIcon(getClass().getResource("水柱_左底.png"));
		imgI[7] = new ImageIcon(getClass().getResource("水柱_右.png"));
		imgI[8] = new ImageIcon(getClass().getResource("水柱_右底.png"));
		
		// TODO Auto-generated constructor stub
	}
	
	public void count(int dt, Character player1, Character player2, GamePanel game){		//pump preserve time
		count = count - dt;
		if(getLocation() == player1.getLocation()){
			player1.setMode(Character.state.drown);
			player1.setEnable(false);
		}
		if(getLocation() == player2.getLocation()){
			player2.setMode(Character.state.drown);
			player2.setEnable(false);
		}
		if(count <= 0){
			game.result();		//check result after life of pump run out
			getLocation().setItem(null);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		switch(sel){
		case center:	g.drawImage(imgI[0].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
						break;
		case up:	g.drawImage(imgI[1].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
					break;
		case upEdge:	g.drawImage(imgI[2].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
						break;
		case down:	g.drawImage(imgI[3].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
					break;
		case downEdge:	g.drawImage(imgI[4].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
						break;
		case left:	g.drawImage(imgI[5].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
					break;
		case leftEdge:	g.drawImage(imgI[6].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
						break;
		case right:	g.drawImage(imgI[7].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
					break;
		case rightEdge:	g.drawImage(imgI[8].getImage(), col*FRAME+BORDER, row*FRAME+BORDER, FRAME, FRAME, null);
						break;
		default:break;
		}
	}

}
