import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	private GameManager gm;
	private Timer animationTimer;
	Ball ball = new Ball(350,338);
	public GamePanel(GameManager gm) {
		this.gm = gm;
		startRepaintThread();
		startAnimationTimer();
	}
	private void startRepaintThread() {
        new Thread(() -> {
            while (true) {
                repaint(); 
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
	private void startAnimationTimer() {
		animationTimer = new Timer(100, e -> gm.updateAnimation());
		animationTimer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.getPlayerImage(), gm.player.getX(), gm.player.getY(), null);
		for(Ball ball : gm.getBalls()) {
			g.drawImage(gm.ballImage, ball.getX(), ball.getY(), null);
		}
	}
	
	
}
