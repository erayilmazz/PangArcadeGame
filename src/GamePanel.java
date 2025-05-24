import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	private GameManager gm;
	private Timer animationTimer;
	//Ball ball = new Ball(350,338);
	public GamePanel(GameManager gm) {
		this.gm = gm;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.getPlayerImage(), gm.player.getX(), gm.player.getY(), null);
		for(Ball ball : gm.getBalls()) {
			g.drawImage(gm.getBallImage(ball), ball.getX(), ball.getY(), null);
		}
		if(gm.getArrows() != null) {
			for(Arrow arrow : gm.getArrows()) {
				g.drawImage(gm.getArrowImage(arrow), arrow.getX(), arrow.getY(), null);
			}
		}
	}
	
	
}
	