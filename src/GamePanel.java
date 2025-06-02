import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	private GameManager gm;
	private ResourceManager resourceManager;
	//Ball ball = new Ball(350,338);
	public GamePanel(GameManager gm) {
		this.gm = gm;
		resourceManager = new ResourceManager();
		resourceManager.loadResources();
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
        g.drawImage(resourceManager.getLevelImage(gm.getCurrentLevel()),0,0,null);
		g.drawImage(resourceManager.border, 0, 0, null);
		g.drawImage(resourceManager.getPlayerImage(gm.player,gm.currentImageIndex), gm.player.getX(), gm.player.getY(), null);
		synchronized (gm.getBalls()) {
			for(Ball ball : gm.getBalls()) {
				g.drawImage(resourceManager.getBallImage(ball), ball.getX(), ball.getY(), null);
				//Rectangle ballLeft = new Rectangle(ball.getX()+ball.getWidth()-1,ball.getY(),10,ball.getHeight());
				//g.fillRect(ballLeft.x, ballLeft.y, ballLeft.width, ballLeft.height);
			}
		}
		if(gm.getArrows() != null) {
			for(Arrow arrow : gm.getArrows()) {
				g.drawImage(resourceManager.getArrowImage(arrow), arrow.getX(), arrow.getDrawY(), arrow.getWidth(), arrow.getHeight(), null);
			}
		}
		if(gm.getFallingObjects() != null) {
			for(FallingObject object : gm.getFallingObjects()) {
				g.drawImage(resourceManager.getFallingObjectsImage(object),object.getX(), object.getY(),null);
			}
		}
		if(gm.getBlocks() != null) {
			for(Block block : gm.getBlocks()) {
				g.drawImage(resourceManager.getBlockImage(block),block.getX(), block.getY(),null);
			}
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("TIME: " + gm.getCountdown(),630, 50);
	}
	
	
}
	