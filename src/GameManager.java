import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

public class GameManager {
	protected int score = 0;
	private int totalScore = 0;
	private int timeBonus = 0;
	public int currentImageIndex = 0;
	Player player;
	private LinkedList<Ball> balls;
	private LinkedList<Arrow> arrows;
	protected String arrowType = "normal";
	protected LinkedList<FallingObject> fallingObjects;
	protected List<String> fallingObjectsList = Arrays.asList("health","dynamite","clock","fixedArrow","doubleArrow");
	private LinkedList<Block> blocks;
	public String diff;
	public boolean isGamePaused = true;
	private Timer gameTimer;
	protected int countdown = 98;
	protected int invisibleTime;
	protected int freezeTime;
	protected boolean isFreeze = false;
	protected int arrowTime;
	private int currentLevel;
	private boolean isScoreScreen = false;
	private boolean isGameOver = false;
	private GamePanel gamePanel;
	private SubPanel subPanel;
	private ResourceManager resourceManager;
	private CollisionManager collisionManager;
	public GameManager(String diff){
		balls = new LinkedList <>();
		arrows = new LinkedList <>();
		fallingObjects = new LinkedList <>();
		blocks = new LinkedList <>();
		setDiff(diff);
		resourceManager = new ResourceManager();
		collisionManager = new CollisionManager(this);
		resourceManager.loadResources();
		//loadResources();
		//loadLevel(1);
		//startGameLoop();
		
	}
	public void startGame() {
		setGamePanel(gamePanel);
		gameTimer = new Timer(1000, e -> {
			countdown--;
            if(countdown == 95) {
            	isGamePaused = false;
            }else if(countdown < 0) {
            	gameTimer.stop();
            	System.out.print("GAMEEEE");
            }
        });
		loadLevel(1,diff);
	}
	boolean running;
	public void startGameLoop() {
		running = true;
		new Thread(() -> {
			while(running) {
				if(!isGamePaused) {
					if(freezeTime == countdown) {
						isFreeze = false;
						freezeTime = 98;
					}
					if(arrowTime == countdown) {
						arrowType = "normal";
						arrowTime = 98;
					}
					if(countdown <= 0) {
						gameOver();
					}
					player.move();
					synchronized (balls) {
						handleExplotion();
						for(Ball ball :balls) {
							if(ball.isExploded()) {
								ball.explodeImageIndex++;
							}
							else {
								if (!isFreeze) ball.move();
							}
							for(Block block : blocks) {
								if(ball.getCircleBounds().intersects(block.getBounds())) {
									ball.setCollisionBlock(true);
									if(ball.isFirstCollisionBlock) {
										//System.out.println("değdi");
										collisionManager.resolveCollision(block,ball);
										ball.isFirstCollisionBlock = false;
									}
									break;
								}else {
									ball.setCollisionBlock(false);
								}
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
						if(object.isFalling()) object.move();
					}
					Iterator<Block> it = blocks.iterator();
					while(it.hasNext()) {
						Block block = it.next();
						if(block.isDestroyed()) block.destroyImageIndex ++;
						if(block.destroyImageIndex >= 24) {
							it.remove();
						}
					}
					collisionManager.checkPlayerBallCollision(player, balls);
					collisionManager.checkArrowBallCollision(arrows, balls);
					collisionManager.checkPlayerItemCollision(player, fallingObjects);
					collisionManager.checkArrowBlockCollision(arrows, blocks);
					updateAnimation();
					if(player.isInvisible() == true) checkInvisible();
					if(balls.isEmpty()) {
						if(currentLevel == 4) {
							gameOver();
							break;
						}
							
						loadNextLevel();
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
				if(isGameOver()) running = false;
			}
		}).start();
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
			blocks.add(new Block(300,160,16,64,'y'));
			blocks.add(new Block(450,150,16,64,'y'));
			blocks.add(new Block(316,214,64,16,'x'));
			balls.add(new LargeBall(317,130,diff));
			//balls.add(new ExtraLargeBall(100,200,diff));
			//balls.add(new LargeBall(100,250,diff));
			//balls.add(new MediumBall(100,50,diff));
			//balls.add(new MediumBall(100,300,diff));//300
			//balls.add(new LargeBall(100,250,diff));//250
			//balls.add(new ExtraLargeBall(100,200,diff));//200
		}else if(level == 2) {
			currentLevel = 2;
			balls.add(new MediumBall(100,300,diff));
			blocks.add(new Block(200,200,64,16,'x'));
		}else if (level == 3) {
			currentLevel = 3;
			balls.add(new LargeBall(100,250,diff));
			blocks.add(new Block(200,200,64,16,'x'));
		}else if (level == 4) {
			currentLevel = 4;
			balls.add(new ExtraLargeBall(100,200,diff));
			blocks.add(new Block(200,200,64,16,'x'));
		}
		gameTimer.restart();
		gamePanel.revalidate();
		gamePanel.repaint();
        startGameLoop();
	}
	private void loadNextLevel() {
		timeBonus = countdown * 10;
		totalScore += (score + timeBonus);
		isScoreScreen = true;
		gameTimer.stop();
		countdown = 97;
		currentLevel ++;
		fallingObjects.clear();
		isFreeze = false;
		player.resetCor();
		player.setInvisible(false);
		arrowType = "normal";
		arrows.clear();
		blocks.clear();
		gamePanel.repaint();
		new Timer(5000, e -> {
			loadLevel(currentLevel,diff);
			((javax.swing.Timer) e.getSource()).stop();
			isScoreScreen = false;
			isGamePaused = true;
			timeBonus = 0;
			score = 0;
		}).start();
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
	
	
	private void checkInvisible() {
		if(countdown == invisibleTime) player.setInvisible(false);
	}
	
	/*
	private void checkBallBlockCollision() {
		for(Ball ball : balls) {
				for(Block block : blocks) {
					if(ball.getBounds().intersects(block.getBounds())) {
						ball.setCollisionBlock(true);
						if(ball.isFirstCollisionBlock) ball.resolveCollision(block);
						break;
					}else {
						ball.setCollisionBlock(false);
					}
				}
		}
	}
	*/
	
	private void handleExplotion() {
		List<Ball> toAdd = new LinkedList<>();
		Iterator<Ball> iterator = balls.iterator();
		while(iterator.hasNext()) {
			Ball ball = iterator.next();
			if(ball.isExploded() && ball.explodeImageIndex >= 9) {
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
	
	
	public void gameOver() {
		//System.out.println(MainFrame.user.getUsername());
		timeBonus = countdown * 10;
		totalScore += (score + timeBonus);
		isGameOver = true;
		gamePanel.repaint();
		MainFrame.user.saveScore(totalScore);
		
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
	public LinkedList<Block> getBlocks() {
		return blocks;
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
	public boolean isScoreScreen() {
		return isScoreScreen;
	}
	public void setScoreScreen(boolean isScoreScreen) {
		this.isScoreScreen = isScoreScreen;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getTimeBonus() {
		return timeBonus;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	public String getArrowType() {
		return arrowType;
	}
	
	
}
