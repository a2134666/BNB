import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Box extends Item {
	private ImageIcon[] icon = new ImageIcon[3];
	private type sel;
	private boolean bang;
	private int row, col, count;
	private static final int WIDTH = 64, BORDER = 30;
	
	public enum type{
		box(0),
		cubeR(1),
		cubeO(2);
		
		private int value;
		private type(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	};
	
	public Box(Zone l, type s) {
		super(l, true);
		row = l.getRow();
		col = l.getCol();
		sel = s;
		count = 500;
		bang = false;
		icon[0] = new ImageIcon(getClass().getResource("箱.png"));
		icon[1] = new ImageIcon(getClass().getResource("方塊_紅.png"));
		icon[2] = new ImageIcon(getClass().getResource("方塊_橘.png"));
		// TODO Auto-generated constructor stub
	}
	public void setBang(boolean bang){		//if the box if destroied
		this.bang = bang;
	}
	public boolean isBang() {
		return bang;
	}
	
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(icon[sel.getValue()].getImage(), col*WIDTH+BORDER-8, row*WIDTH+(WIDTH-70)+BORDER, WIDTH+8, 78, null);
	}
	public void count(int dt){		//box turn out to be buffitem
		if(bang){
			count -= dt;
			if(count <= 0){
				switch((int)(Math.random()*8)){
				case 0:	getLocation().setItem(new BuffItem(getLocation(), BuffItem.type.shoe));
						break;
				case 1:	getLocation().setItem(new BuffItem(getLocation(), BuffItem.type.ball));
						break;
				case 2:	getLocation().setItem(new BuffItem(getLocation(), BuffItem.type.vase));
						break;
				case 3:	getLocation().setItem(new BuffItem(getLocation(), BuffItem.type.bone));
						break;
				default:getLocation().setItem(null);
						break;
				}
			}
		}
	}
}
