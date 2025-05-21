import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private GameManager gm;
	Ball ball = new Ball(350,338,1,1);
	public GamePanel(GameManager gm) {
		this.gm = gm;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.playerImage, gm.player.getX(), gm.player.getY(), null);
		for(Ball ball : gm.getBalls()) {
			g.drawImage(gm.ballImage, ball.getX(), ball.getY(), null);
		}
	}
	
	
}
