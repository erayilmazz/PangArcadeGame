import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CollisionManager{
	private GameManager gm;
	public CollisionManager(GameManager gm){
		this.gm = gm;
	}
	public void checkPlayerBallCollision(Player player, LinkedList<Ball> balls) {
		if(!player.isInvisible()) {
			for(Ball ball : balls) {
				//System.out.println(ball.getBounds().getX());
				if(ball.getCircleBounds().intersects(player.getBounds())) {
					//System.out.println("Game over");
					player.decreaseHealthBar();
					if(player.getHealthBar() <= 0) {
						gm.gameOver();
						break;
					}
					player.setInvisible(true);
					gm.invisibleTime = gm.countdown - 3;
				}
			}
		}
	}
	public void checkArrowBallCollision(List <Arrow> arrows, LinkedList<Ball> balls) {
		for(Arrow arrow : arrows) {
			synchronized (balls) {
				for(Ball ball : balls) {
					//System.out.println(ball.getBounds().getX());
					if(ball.getCircleBounds().intersects(arrow.getBounds())) {
						if(ball instanceof SmallBall) gm.score += 200;
						if(ball instanceof MediumBall) gm.score += 150;
						if(ball instanceof LargeBall) gm.score += 100;
						if(ball instanceof ExtraLargeBall) gm.score += 50;
						ball.setExploded(true);
						arrows.remove(arrow);
						Random rand = new Random();
						String randomItem = gm.fallingObjectsList.get(rand.nextInt(gm.fallingObjectsList.size()));
						gm.fallingObjects.add(new FallingObject(ball.getX(),ball.getY(),24,24,randomItem));
						
					}
				}
			}
		}
	}

	public void checkPlayerItemCollision(Player player, LinkedList<FallingObject> fallingObjects) {
		Iterator<FallingObject> it = fallingObjects.iterator();
		while(it.hasNext()) {
			FallingObject object = it.next();
			if(object.getBounds().intersects(player.getBounds())) {
				//"health","dynamite","clock","fixedArrow","doubleArrow"
				switch(object.getObject()) {
				case "health" : 
					player.increaseHealthBar();
					break;
				case "dynamite":
					for(Ball ball : gm.getBalls()) {
						if(!(ball instanceof SmallBall)) {
							ball.setExploded(true);
						}
					}
					break;
				case "clock":
					gm.freezeTime = gm.countdown - 5;
					System.out.println(gm.freezeTime);
					gm.isFreeze = true;
					break;
				case "fixedArrow":
					gm.arrowType = "fixed";
					gm.arrowTime = gm.countdown - 20;
					break;
				case "doubleArrow":
					gm.arrowType = "double";
					gm.arrowTime = gm.countdown - 20;
					break;
				}
				gm.score += 100;
				it.remove();
			}
		}
		
	}
	public void checkArrowBlockCollision(LinkedList <Arrow> arrows, LinkedList<Block> blocks) {
		Iterator<Arrow> it = arrows.iterator();
		while(it.hasNext()) {
			Arrow arrow = it.next();
			for(Block block:blocks) {
				if(block.getBounds().intersects(arrow.getBounds())) {
					block.setDestroyed(true);
					it.remove();
				}
			}
		}
	}
	public void resolveCollision(Block block, Ball ball) {
		Rectangle blockRight = new Rectangle(block.getX()+block.getWidth(),block.getY(),1,block.getHeight());
		Rectangle blockLeft = new Rectangle(block.getX(),block.getY(),1,block.getHeight());
		Rectangle blockTop = new Rectangle(block.getX(),block.getY(),block.getWidth(),1);
		Rectangle blockBottom = new Rectangle(block.getX(),block.getY() + block.getHeight(),block.getWidth(),1);
		boolean top = ball.getCircleBounds().intersects(blockTop);
		boolean bottom = ball.getCircleBounds().intersects(blockBottom);
		boolean left = ball.getCircleBounds().intersects(blockLeft);
		boolean right = ball.getCircleBounds().intersects(blockRight);
		if((top) && (left || right)) {
			if(ball.getVy() > 0) {
				ball.reverseY();
			}
			else ball.reverseX();
			return;
		}
		if((bottom) && (left || right)) {	
			ball.reverseY();
			return;
		}
		if(top || bottom) {
			ball.reverseY();
		}
		if(left || right) {
			ball.reverseX();
		}
	}
}
