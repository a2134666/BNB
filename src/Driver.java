import java.awt.Dimension;
import javax.swing.JFrame;

public class Driver {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();	//use screensize to control window size
		JFrame frame = new JFrame("BNB");
		int panelWidth, panelHeight;
		
		frame.setLocation(0, 0);
		if((int)screenSize.getHeight()-40 >= GamePanel.HEIGHT+38){		//工作列_40
			frame.setSize(1035, 930);
			panelHeight = GamePanel.HEIGHT;
			panelWidth = GamePanel.WIDTH;
		}
		else{
			panelHeight = (int)screenSize.getHeight()-40-38;
			panelWidth = panelHeight * GamePanel.WIDTH / GamePanel.HEIGHT;
			frame.setSize(panelWidth+15, panelHeight+38);
			
		}
		
		frame.setContentPane(new GamePanel(panelWidth, panelHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
