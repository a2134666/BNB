import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Barrier extends Item{
	private ImageIcon treeI;
	private ImageIcon[] houseI = new ImageIcon[3];
	private type sel;
	private int row, col;
	private static final int WIDTH = 64, BORDER = 30;
	
	public enum type{
		tree, houseRed, houseYellow, houseBlue
	};

	public Barrier(Zone l, type s) {
		super(l, true);
		row = l.getRow();
		col = l.getCol();
		sel = s;
		treeI = new ImageIcon(getClass().getResource("樹.png"));
		houseI[0] = new ImageIcon(getClass().getResource("房1.png"));
		houseI[1] = new ImageIcon(getClass().getResource("房2.png"));
		houseI[2] = new ImageIcon(getClass().getResource("房3.png"));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		switch(sel){
		case tree:	g.drawImage(treeI.getImage(), col*WIDTH+BORDER, row*WIDTH+(WIDTH-110)+BORDER, WIDTH, 110, null);
					break;
		case houseRed:	g.drawImage(houseI[0].getImage(), col*WIDTH+BORDER-8, row*WIDTH+(WIDTH-91)+BORDER, WIDTH+8, 99, null);
						break;
		case houseYellow:	g.drawImage(houseI[1].getImage(), col*WIDTH+BORDER-8, row*WIDTH+(WIDTH-91)+BORDER, WIDTH+8, 99, null);
							break;
		case houseBlue:	g.drawImage(houseI[2].getImage(), col*WIDTH+BORDER-8, row*WIDTH+(WIDTH-91)+BORDER, WIDTH+8, 99, null);
						break;
		default:break;
		}
	}

}
