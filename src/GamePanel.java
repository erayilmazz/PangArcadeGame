import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

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
		g.setColor(Color.BLACK); 
        Rectangle r = gm.player.getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        g.drawImage(gm.getLevelImage(),0,0,null);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.getPlayerImage(), gm.player.getX(), gm.player.getY(), null);
		synchronized (gm.getBalls()) {
			for(Ball ball : gm.getBalls()) {
				g.drawImage(gm.getBallImage(ball), ball.getX(), ball.getY(), null);
			}
		}
		if(gm.getArrows() != null) {
			for(Arrow arrow : gm.getArrows()) {
				g.drawImage(gm.getArrowImage(arrow), arrow.getX(), arrow.getDrawY(), arrow.getWidth(), arrow.getHeight(), null);
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
	