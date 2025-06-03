import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectManager {
	GameManager gm;
	CollisionManager collisionManager;
	SoundManager sm;
	public ObjectManager(GameManager gm, CollisionManager collisionManager) {
		this.gm = gm;
		this.collisionManager=collisionManager;
		sm = new SoundManager();
	}
	public void updateBalls() {
		for(Ball ball : gm.getBalls()) {
			if(ball.isExploded()) {
				ball.explodeImageIndex++;
			}
			else {
				if (!gm.isFreeze()) ball.move();
			}
			 boolean isColliding = false;
			for(Block block : gm.getBlocks()) {
				if(ball.getCircleBounds().intersects(block.getBounds())) {
					isColliding = true;
					if(!ball.isCollisionBlock()) {
						collisionManager.resolveCollision(block,ball);
					}
				break;
				}
			}
			ball.setCollisionBlock(isColliding);
		}
	}
	public void updateArrows() {
		for(Arrow arrow : gm.getArrows()) {	
			if(arrow.getY() <= 16) {
				if(arrow.getType().equals("normal") || arrow.getType().equals("double"))gm.getArrows().remove(arrow);
				else if(arrow.getType().equals("fixed") && arrow.getFixedTime() == gm.getCountdown()) {
					gm.getArrows().remove(arrow);
				}
				continue;
			}
			arrow.move();
		}
	}
	public void updateFallingObjects() {
		for(FallingObject object : gm.getFallingObjects()) {
			if(object.isFalling()) object.move();
		}
	}
	public void updateBlocks() {
		Iterator<Block> it = gm.getBlocks().iterator();
		while(it.hasNext()) {
			Block block = it.next();
			if(block.isDestroyed()) block.destroyImageIndex ++;
			if(block.destroyImageIndex >= 24) {
				it.remove();
			}
		}
	}
	public void handleExplotion() {
		List<Ball> toAdd = new LinkedList<>();
		Iterator<Ball> iterator = gm.getBalls().iterator();
		while(iterator.hasNext()) {
			Ball ball = iterator.next();
			if(ball.isExploded() && ball.explodeImageIndex >= 9) {
				iterator.remove();
				if(ball instanceof MediumBall) {
					SmallBall s1 = new SmallBall(ball.getX(),ball.getY(),gm.getDiff());
					SmallBall s2 = new SmallBall(ball.getX(),ball.getY(),gm.getDiff());
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
				else if(ball instanceof LargeBall) {
					MediumBall s1 = new MediumBall(ball.getX(),ball.getY(),gm.getDiff());
					MediumBall s2 = new MediumBall(ball.getX(),ball.getY(),gm.getDiff());
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
				else if(ball instanceof ExtraLargeBall) {
					LargeBall s1 = new LargeBall(ball.getX(),ball.getY(),gm.getDiff());
					LargeBall s2 = new LargeBall(ball.getX(),ball.getY(),gm.getDiff());
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
			}
		}
		synchronized (gm.getBalls()) {
			gm.getBalls().addAll(toAdd);  
		} 
	}
	public void createArrow() {
		switch (gm.getArrowType()){
		case("normal"):
			if(gm.getArrows().isEmpty()) {
				sm.playSound(sm.arrowThrow);
				gm.player.setDirection("shoot");
				Arrow arrow = new Arrow(gm.player.getX() + 18, gm.player.getY());
				arrow.setType("normal");
				gm.getArrows().add(arrow);
			}
			break;
		case("fixed"):
			if(gm.getArrows().isEmpty()) {
				sm.playSound(sm.arrowThrow);
				gm.player.setDirection("shoot");
				Arrow arrow = new Arrow(gm.player.getX() + 18, gm.player.getY());
				arrow.setFixedTime(gm.getCountdown() - 5);
				arrow.setType("fixed");
				gm.getArrows().add(arrow);	
			}
			break;
		
		case("double"):
			if(gm.getArrows().size() <= 1) {
				sm.playSound(sm.arrowThrow);
				gm.player.setDirection("shoot");
				Arrow arrow = new Arrow(gm.player.getX() + 18, gm.player.getY());
				arrow.setType("double");
				gm.getArrows().add(arrow);
			}
			break;

		}
	}
}
