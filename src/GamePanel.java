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
		if(gm.isScoreScreen()) {
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.BOLD, 24));
			g.drawString("SCORE: " + gm.getScore(),300, 150);
			g.drawString("TIME BONUS: "+ gm.getTimeBonus(),300,200);
			g.drawString("TOTAL SCORE: "+ gm.getTotalScore(),300,250);
			return;
		}
		if(gm.isGameOver()) {
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.BOLD, 24));
			g.drawString("GAME OVER",300, 150);
			//g.drawString("SCORE: "+ gm.getTimeBonus(),300,200);
			g.drawString("SCORE: "+ gm.getTotalScore(),300,250);
			return;
		}
        Rectangle r = gm.player.getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        g.drawImage(gm.getLevelImage(),0,0,null);
		g.drawImage(gm.border, 0, 0, null);
		g.drawImage(gm.getPlayerImage(), gm.player.getX(), gm.player.getY(), null);
		synchronized (gm.getBalls()) {
			for(Ball ball : gm.getBalls()) {
				g.drawImage(gm.getBallImage(ball), ball.getX(), ball.getY(), null);
				//Rectangle ballLeft = new Rectangle(ball.getX()+ball.getWidth()-1,ball.getY(),10,ball.getHeight());
				//g.fillRect(ballLeft.x, ballLeft.y, ballLeft.width, ballLeft.height);
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
		if(gm.getBlocks() != null) {
			for(Block block : gm.getBlocks()) {
				g.drawImage(gm.getBlockImage(block),block.getX(), block.getY(),null);
				Rectangle blockRight = new Rectangle(block.getX()+block.getWidth()-1,block.getY(),1,block.getHeight());
				Rectangle blockLeft = new Rectangle(block.getX(),block.getY(),1,block.getHeight());
				Rectangle blockTop = new Rectangle(block.getX(),block.getY(),block.getWidth(),1);
				Rectangle blockBottom = new Rectangle(block.getX(),block.getY() + block.getHeight() - 1,block.getWidth(),1);
				g.fillRect(blockRight.x, blockRight.y, blockRight.width, blockRight.height);
				g.fillRect(blockLeft.x, blockLeft.y, blockLeft.width, blockLeft.height);
				g.fillRect(blockTop.x, blockTop.y, blockTop.width, blockTop.height);
				g.fillRect(blockBottom.x, blockBottom.y, blockBottom.width, blockBottom.height);
			}
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("TIME: " + gm.getCountdown(),630, 50);
	}
	
	
}
	