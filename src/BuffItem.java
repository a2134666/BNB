import java.awt.Graphics;
import javax.swing.ImageIcon;

public class BuffItem extends Item {
	private ImageIcon shoeI, ballI, vaseI, boneI;
	private type sel;
	private int row, col;
	private static final int WIDTH = 64, BORDER = 30;
	public static final double dv = 0.05;
	public static final int dp = 1, dn = 1;
	
	public enum type{
		shoe, ball, vase, bone
	};

	public BuffItem(Zone l, type s) {
		super(l, false);
		row = l.getRow();
		col = l.getCol();
		sel = s;
		shoeI = new ImageIcon(getClass().getResource("鞋子.png"));
		ballI = new ImageIcon(getClass().getResource("水球.png"));
		vaseI = new ImageIcon(getClass().getResource("水瓶.png"));
		boneI = new ImageIcon(getClass().getResource("炸藥.png"));
		// TODO Auto-generated constructor stub
	}
	
	public type getSel() {		//use on powerup
		return sel;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		switch(sel){
		case shoe:	g.drawImage(shoeI.getImage(), col*WIDTH+BORDER+2, row*WIDTH+BORDER-41, WIDTH-4, 82, null);
					break;
		case ball:	g.drawImage(ballI.getImage(), col*WIDTH+BORDER+2, row*WIDTH+BORDER-43, WIDTH-4, 87, null);
					break;
		case vase:	g.drawImage(vaseI.getImage(), col*WIDTH+BORDER+2, row*WIDTH+BORDER-43, WIDTH-4, 87, null);
					break;
		case bone: 	g.drawImage(boneI.getImage(), col*WIDTH+BORDER, row*WIDTH+BORDER-39, WIDTH, 78, null);
					break;
		default:break;
		}
	}

}
