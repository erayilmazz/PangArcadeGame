import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;




public class GameManager {
	public Image border, playerImage, arrow0Image, shootPlayerImage;
	public List<Image> leftImages = new ArrayList<>();
	public List<Image> rightImages = new ArrayList<>();
	public List<Image> smallBallImages = new ArrayList<>();
	public List<Image> mediumBallImages = new ArrayList<>();
	public List<Image> largeBallImages = new ArrayList<>();
	public List<Image> extraLargeImages = new ArrayList<>();
	public List<Image> arrowImages = new ArrayList<>();
	private int currentImageIndex = 0;
	private ImageLoader id = new ImageLoader();
	Player player;
	private LinkedList<Ball> balls;
	private LinkedList<Arrow> arrows;
	public String diff;
	private GamePanel gamePanel;
	public GameManager(String diff){
		player = new Player();
		balls = new LinkedList <>();
		arrows = new LinkedList <>();
		setDiff(diff);
		//loadResources();
		//loadLevel(1);
		//startGameLoop();
		
	}
	public void startGame() {
		loadResources();
		setGamePanel(gamePanel);
		loadLevel(1,diff);
		gamePanel.repaint();
		startGameLoop();
	}
	public void startGameLoop() {
		new Thread(() -> {
			while(true) {
				synchronized (balls) {
					handleExplotion();
					for(Ball ball :balls) {
						if(ball.isExploded()) {
							ball.expodeImageIndex++;
						}
						else ball.move();
					}
				}
				for(Arrow arrow : arrows) {
					if(arrow.getY() <= 16) {
						arrows.remove(arrow);
						continue;
					}
					arrow.move();
				}
				checkPlayerBallCollision();
				checkArrowBallCollision();
				updateAnimation();
				gamePanel.repaint();
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	private void loadResources() {
		border = id.loadImage("/resources/border.png");
		playerImage = id.loadImage("/resources/player.png",Color.GREEN);
		arrow0Image = id.loadImage("/resources/arrow0.png",Color.WHITE);
		shootPlayerImage = id.loadImage("/resources/playerShoot.png",Color.green);
		for(int i = 0; i <= 4; i++) {
			leftImages.add(id.loadImage("/resources/playerLeft" + i + ".png", Color.GREEN));
			rightImages.add(id.loadImage("/resources/playerRight" + i + ".png", Color.GREEN));
			smallBallImages.add(id.loadImage("/resources/small" + i + ".png",Color.GREEN));
			mediumBallImages.add(id.loadImage("/resources/Medium" + i + ".png",Color.GREEN));
			largeBallImages.add(id.loadImage("/resources/Large" + i + ".png",Color.GREEN));
			extraLargeImages.add(id.loadImage("/resources/ExtraLarge" + i + ".png",Color.GREEN));
		}
	}
	public void updateAnimation() {
		currentImageIndex++;
		if(currentImageIndex == 50) currentImageIndex = 0;
	}

	private void loadLevel(int level, String diff) {
		balls = new LinkedList<>();
		if(level == 1) {
			balls.add(new SmallBall(100,350,diff));//350
			balls.add(new MediumBall(100,300,diff));//300
			balls.add(new LargeBall(100,250,diff));//250
			balls.add(new ExtraLargeBall(100,200,diff));//200
		}
	}
	
	public void createArrow() {
		if(arrows.isEmpty()) {
			player.setDirection("shoot");
			arrows.add(new Arrow(player.getX() + 18, player.getY()));
		}
	}
	
	private void checkPlayerBallCollision() {
		for(Ball ball : balls) {
			//System.out.println(ball.getBounds().getX());
			if(ball.getBounds().intersects(player.getBounds())) {
				//System.out.println("Game over");
			}
		}
	}
	private void checkArrowBallCollision() {
		for(Arrow arrow : arrows) {
			synchronized (balls) {
				for(Ball ball : balls) {
					//System.out.println(ball.getBounds().getX());
					if(ball.getBounds().intersects(arrow.getBounds())) {
						System.out.println("Game over");
						ball.setExploded(true);
						arrows.remove(arrow);
						
					}
				}
			}
		}
	}
	public Image getPlayerImage() {
		if(player.getDirection().equals("left")) {
			return leftImages.get(currentImageIndex/10);
		}
		else if (player.getDirection().equals("right")) {
			return rightImages.get(currentImageIndex/10);
		}else if(player.getDirection().equals("shoot")) {
			return shootPlayerImage;
		}
		else {
			return playerImage;
		}
		
	}
	public Image getBallImage(Ball ball) {
		int indexNum = ball.expodeImageIndex;
		if(ball instanceof SmallBall && !ball.isExploded()) return smallBallImages.get(0);
		else if(ball instanceof SmallBall && ball.isExploded()){
			return smallBallImages.get(indexNum/2);
		}
		if(ball instanceof MediumBall && !ball.isExploded()) return mediumBallImages.get(0);
		else if(ball instanceof MediumBall && ball.isExploded()){
			return mediumBallImages.get(indexNum/2);
		}
		if(ball instanceof LargeBall && !ball.isExploded()) return largeBallImages.get(0);
		else if(ball instanceof LargeBall && ball.isExploded()){
			return largeBallImages.get(indexNum/2);
		}
		if(ball instanceof ExtraLargeBall && !ball.isExploded()) return extraLargeImages.get(0);
		else if(ball instanceof ExtraLargeBall && ball.isExploded()){
			return extraLargeImages.get(indexNum/2);
		}
		return null;
	}
	private void handleExplotion() {
		List<Ball> toAdd = new LinkedList<>();
		Iterator<Ball> iterator = balls.iterator();
		while(iterator.hasNext()) {
			Ball ball = iterator.next();
			if(ball.isExploded() && ball.expodeImageIndex >= 9) {
				iterator.remove();
				if(ball instanceof MediumBall) {
					SmallBall s1 = new SmallBall(ball.getX(),ball.getY(),diff);
					SmallBall s2 = new SmallBall(ball.getX(),ball.getY(),diff);
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
				else if(ball instanceof LargeBall) {
					MediumBall s1 = new MediumBall(ball.getX(),ball.getY(),diff);
					MediumBall s2 = new MediumBall(ball.getX(),ball.getY(),diff);
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
				else if(ball instanceof ExtraLargeBall) {
					LargeBall s1 = new LargeBall(ball.getX(),ball.getY(),diff);
					LargeBall s2 = new LargeBall(ball.getX(),ball.getY(),diff);
					s2.reverseX();
					toAdd.add(s1);
					toAdd.add(s2);
					continue;
				}
			}
		}
		synchronized (balls) {
			balls.addAll(toAdd);  
		} 
	}
	public Image getArrowImage(Arrow arrow) {
		return arrow0Image;
	}
	public LinkedList<Ball> getBalls() {
		return balls;
	}
	public LinkedList<Arrow> getArrows(){
		return arrows;
	}
	public void setGamePanel(GamePanel gamePanel) {
	    this.gamePanel = gamePanel;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	
}
