import java.awt.Color;
import java.awt.Graphics;

public class Zone {
	public static final int FRAME = 64;
	private int x, y, row, col;
	private Color color;
	private Item item;
	private boolean player1, player2;
	
	public Zone(int x, int y, int r, int c, Color color, Item obj){
		this.x = x;
		this.y = y;
		row = r;
		col = c;
		this.color = color;
		item = obj;
		player1 = false;
		player2 = false;
	}
	
	public void setColor(Color color) {
		this.color = color;
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
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	
	public boolean isPlayer1() {
		return player1;
	}
	public void setPlayer1(boolean player1) {
		this.player1 = player1;
	}
	public boolean isPlayer2() {
		return player2;
	}
	public void setPlayer2(boolean player2) {
		this.player2 = player2;
	}
	
	
	public void setItem(Item item) {
		this.item = item;
	}
	public Item getItem() {
		return item;
	}

	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, FRAME, FRAME);
	}
	public void drawItem(Graphics g){
		if(item != null){
			item.draw(g);
		}
	}

}
