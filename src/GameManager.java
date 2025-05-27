import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;




public class GameManager {
	public Image border, playerImage, shootPlayerImage, emptyImage, healthImage, dynamiteImage, clockImage, fixedArrowImage, doubleArrowImage;
	public BufferedImage arrow0Image;
	public List<Image> leftImages = new ArrayList<>();
	public List<Image> rightImages = new ArrayList<>();
	public List<Image> smallBallImages = new ArrayList<>();
	public List<Image> mediumBallImages = new ArrayList<>();
	public List<Image> largeBallImages = new ArrayList<>();
	public List<Image> extraLargeImages = new ArrayList<>();
	public List<Image> arrowImages = new ArrayList<>();
	private int score = 0;
	private int currentImageIndex = 0;
	private ImageLoader id = new ImageLoader();
	Player player;
	private LinkedList<Ball> balls;
	private LinkedList<Arrow> arrows;
	private String arrowType = "normal";
	private LinkedList<FallingObject> fallingObjects;
	private List<String> fallingObjectsList = Arrays.asList("health","dynamite","clock","fixedArrow","doubleArrow");
	public String diff;
	public boolean isGamePaused = true;
	private Timer gameTimer;
	private int countdown = 98;
	private int invisibleTime;
	private int freezeTime;
	private boolean isFreeze = false;
	private int arrowTime;
	private int currentLevel;
	private GamePanel gamePanel;
	private SubPanel subPanel;
	public GameManager(String diff){
		balls = new LinkedList <>();
		arrows = new LinkedList <>();
		fallingObjects = new LinkedList <>();
		setDiff(diff);
		//loadResources();
		//loadLevel(1);
		//startGameLoop();
		
	}
	public void startGame() {
		loadResources();
		setGamePanel(gamePanel);
		loadLevel(1,diff);
	}
	public void startGameLoop() {
		new Thread(() -> {
			while(true) {
				if(!isGamePaused) {
					if(freezeTime == countdown) {
						isFreeze = false;
						freezeTime = 98;
					}
					if(arrowTime == countdown) {
						arrowType = "normal";
						arrowTime = 98;
					}
					player.move();
					synchronized (balls) {
						handleExplotion();
						for(Ball ball :balls) {
							if(ball.isExploded()) {
								ball.expodeImageIndex++;
							}
							else {
								if (!isFreeze) ball.move();
							}
						}
					}
					for(Arrow arrow : arrows) {	
						if(arrow.getY() <= 16) {
							if(arrow.getType().equals("normal") || arrow.getType().equals("double"))arrows.remove(arrow);
							else if(arrow.getType().equals("fixed") && arrow.getFixedTime() == countdown) {
								arrows.remove(arrow);
							}
							continue;
						}
						arrow.move();
					}
					for(FallingObject object : fallingObjects) {
						object.move();
					}
					checkPlayerBallCollision();
					checkArrowBallCollision();
					checkPlayerItemCollision();
					updateAnimation();
					if(player.isInvisible() == true) checkInvisible();
					if(balls.isEmpty()) {
						gameTimer.stop();
						countdown = 98;
						currentLevel ++;
						fallingObjects.clear();
						isFreeze = false;
						player.resetCor();
						player.setInvisible(false);
						arrowType = "normal";
						loadLevel(currentLevel,diff);
						isGamePaused = true;
						break;
					}
				}
				subPanel.modifySubPanel();
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
		border = id.loadImage("/resources/border.png",Color.WHITE);
		playerImage = id.loadImage("/resources/player.png",Color.GREEN);
		arrow0Image = id.loadBufferedImage("/resources/arrow.png",Color.RED);
		shootPlayerImage = id.loadImage("/resources/playerShoot.png",Color.green);
		emptyImage = id.loadImage("/resources/empty.png",Color.white);
		healthImage = id.loadImage("/resources/health.png",Color.GREEN);
		dynamiteImage = id.loadImage("/resources/dynamite.png",Color.GREEN);
		clockImage = id.loadImage("/resources/clock.png",Color.GREEN);
		fixedArrowImage = id.loadImage("/resources/fixedArrow.png",Color.GREEN);
		doubleArrowImage = id.loadImage("/resources/doubleArrow.png",Color.GREEN);
		for(int i = 0; i <= 4; i++) {
			leftImages.add(id.loadImage("/resources/playerLeft" + i + ".png", Color.green));
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
			currentLevel = 1;
			balls.add(new SmallBall(100,350,diff));//350
			player = new Player(diff);
			//balls.add(new MediumBall(100,300,diff));//300
			//balls.add(new LargeBall(100,250,diff));//250
			//balls.add(new ExtraLargeBall(100,200,diff));//200
		}else if(level == 2) {
			currentLevel = 2;
			balls.add(new MediumBall(100,300,diff));
			player.resetCor();
		}else if (level == 3) {
			currentLevel = 3;
			balls.add(new LargeBall(100,250,diff));
		}else if (level == 4) {
			currentLevel = 4;
			balls.add(new ExtraLargeBall(100,200,diff));
		}
		gameTimer = new Timer(1000, e -> {
			countdown--;
            if(countdown == 95) {
            	isGamePaused = false;
            }else if(countdown < 0) {
            	gameTimer.stop();
            	System.out.print("GAMEEEE");
            }
        });
		gameTimer.start();
		gamePanel.revalidate();
		gamePanel.repaint();
        startGameLoop();
	}
	
	public void createArrow() {
		switch (arrowType){
		case("normal"):
			if(arrows.isEmpty()) {
				player.setDirection("shoot");
				Arrow arrow = new Arrow(player.getX() + 18, player.getY());
				arrow.setType("normal");
				arrows.add(arrow);
			}
			break;
		case("fixed"):
			if(arrows.isEmpty()) {
				player.setDirection("shoot");
				Arrow arrow = new Arrow(player.getX() + 18, player.getY());
				arrow.setFixedTime(countdown - 5);
				arrow.setType("fixed");
				arrows.add(arrow);	
			}
			break;
		
		case("double"):
			if(arrows.size() <= 1) {
				player.setDirection("shoot");
				Arrow arrow = new Arrow(player.getX() + 18, player.getY());
				arrow.setType("double");
				arrows.add(arrow);
			}
			break;
		
		
		}
	}
	
	private void checkPlayerBallCollision() {
		if(!player.isInvisible()) {
			for(Ball ball : balls) {
				//System.out.println(ball.getBounds().getX());
				if(ball.getBounds().intersects(player.getBounds())) {
					//System.out.println("Game over");
					player.decreaseHealthBar();
					player.setInvisible(true);
					invisibleTime = countdown - 3;
				}
			}
		}
	}
	private void checkInvisible() {
		if(countdown == invisibleTime) player.setInvisible(false);
	}
	private void checkArrowBallCollision() {
		for(Arrow arrow : arrows) {
			synchronized (balls) {
				for(Ball ball : balls) {
					//System.out.println(ball.getBounds().getX());
					if(ball.getBounds().intersects(arrow.getBounds())) {
						if(ball instanceof SmallBall) score += 200;
						if(ball instanceof MediumBall) score += 150;
						if(ball instanceof LargeBall) score += 100;
						if(ball instanceof ExtraLargeBall) score += 50;
						ball.setExploded(true);
						arrows.remove(arrow);
						Random rand = new Random();
						String randomItem = fallingObjectsList.get(rand.nextInt(fallingObjectsList.size()));
						fallingObjects.add(new FallingObject(ball.getX(),ball.getY(),16,16,randomItem));
						
					}
				}
			}
		}
	}
	private void checkPlayerItemCollision() {
		
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
					for(Ball ball : balls) {
						if(!(ball instanceof SmallBall)) {
							ball.setExploded(true);
						}
					}
					break;
				case "clock":
					freezeTime = countdown - 5;
					System.out.println(freezeTime);
					isFreeze = true;
					break;
				case "fixedArrow":
					arrowType = "fixed";
					arrowTime = countdown - 20;
					break;
				case "doubleArrow":
					arrowType = "double";
					arrowTime = countdown - 20;
					break;
				}
				it.remove();
			}
		}
		
	}
	public Image getPlayerImage() {
		if(player.isInvisible() && (currentImageIndex%5) == 0) {
			return emptyImage;
		}
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
	public BufferedImage getArrowImage(Arrow arrow) {
		BufferedImage croppedImage = arrow0Image.getSubimage(0,0,arrow.getWidth(),arrow.getHeight());
		return croppedImage;
	}
	public Image getFallingObjectsImage(FallingObject object) {
		if(object.getObject() == "health") return healthImage;
		else if(object.getObject() == "dynamite") return dynamiteImage;
		else if(object.getObject() == "clock") return clockImage;
		else if(object.getObject() == "doubleArrow") return doubleArrowImage;
		else if(object.getObject() == "fixedArrow") return fixedArrowImage;
		return emptyImage;
	}
	public LinkedList<Ball> getBalls() {
		return balls;
	}
	public LinkedList<Arrow> getArrows(){
		return arrows;
	}
	public LinkedList<FallingObject> getFallingObjects() {
		return fallingObjects;
	}
	public void setGamePanel(GamePanel gamePanel) {
	    this.gamePanel = gamePanel;
	}
	public void setSubPanel(SubPanel subPanel) {
		this.subPanel = subPanel;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public int getCountdown() {
		return countdown;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public int getScore() {
		return score;
	}
	
	
}
