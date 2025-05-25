import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	private GameManager gm;
	//Ball ball = new Ball(350,338);
	public GamePanel(GameManager gm) {
		this.gm = gm;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.getPlayerImage(), gm.player.getX(), gm.player.getY(), null);
		synchronized (gm.getBalls()) {
			for(Ball ball : gm.getBalls()) {
				g.drawImage(gm.getBallImage(ball), ball.getX(), ball.getY(), null);
			}
		}
		if(gm.getArrows() != null) {
			for(Arrow arrow : gm.getArrows()) {
				g.drawImage(gm.getArrowImage(arrow), arrow.getX(), arrow.getY(), null);
			}
		}
		if(gm.getFallingObjects() != null) {
			for(FallingObject object : gm.getFallingObjects()) {
				g.drawImage(gm.getFallingObjectsImage(object),object.getX(), object.getY(),null);
			}
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("TIME: " + gm.getCountdown(),630, 50);
	}
	
	
}
	