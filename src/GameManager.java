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
	private ObjectManager objectManager;
	private SoundManager soundManager;
	public GameManager(String diff){
		balls = new LinkedList <>();
		arrows = new LinkedList <>();
		fallingObjects = new LinkedList <>();
		blocks = new LinkedList <>();
		setDiff(diff);
		resourceManager = new ResourceManager();
		soundManager = new SoundManager();
		collisionManager = new CollisionManager(this,soundManager);
		objectManager = new ObjectManager(this,collisionManager);
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
						objectManager.handleExplotion();
						objectManager.updateBalls();
					}
					objectManager.updateArrows();
					objectManager.updateFallingObjects();
					objectManager.updateBlocks();
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
		//balls.add(new SmallBall(100,350,diff));//350
		//balls.add(new MediumBall(100,300,diff));//300
		//balls.add(new LargeBall(100,250,diff));//250
		//balls.add(new ExtraLargeBall(100,200,diff));//200
		if(level == 1) {
			currentLevel = 1;
			soundManager.playMusic(soundManager.antalya);
			player = new Player(diff);
			balls.add(new ExtraLargeBall(50,50,diff));
			
		}else if(level == 2) {
			currentLevel = 2;
			soundManager.playMusic(soundManager.ankara);
			balls.add(new LargeBall(150,70,diff));
			balls.add(new LargeBall(600,70,diff));
			blocks.add(new Block(100,150,64,16,'x'));
			blocks.add(new Block(325,150,64,16,'x'));
			blocks.add(new Block(550,150,64,16,'x'));
			
		}else if (level == 3) {
			currentLevel = 3;
			soundManager.playMusic(soundManager.izmir);
			balls.add(new LargeBall(50,130,diff));
			balls.add(new MediumBall(317,130,diff));
			balls.add(new LargeBall(600,130,diff));
			blocks.add(new Block(170,150,16,64,'y'));
			blocks.add(new Block(550,150,16,64,'y'));
		}else if (level == 4) {
			currentLevel = 4;
			soundManager.playMusic(soundManager.istanbul);
			blocks.add(new Block(30,200,64,16,'x'));
			blocks.add(new Block(100,200,64,16,'x'));
			blocks.add(new Block(170,200,64,16,'x'));
			blocks.add(new Block(240,200,64,16,'x'));
			blocks.add(new Block(310,200,64,16,'x'));
			blocks.add(new Block(380,200,64,16,'x'));
			blocks.add(new Block(450,200,64,16,'x'));
			blocks.add(new Block(520,200,64,16,'x'));
			blocks.add(new Block(590,200,64,16,'x'));
			blocks.add(new Block(660,200,64,16,'x'));
			balls.add(new ExtraLargeBall(50,50,diff));
			balls.add(new ExtraLargeBall(550,50,diff));
			balls.add(new LargeBall(480,300,diff));
		}
		gameTimer.restart();
		gamePanel.revalidate();
		gamePanel.repaint();
        startGameLoop();
	}
	private void loadNextLevel() {
		soundManager.stopMusic();
		soundManager.playMusic(soundManager.bonus);
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
		objectManager.createArrow();
	}
	
	
	private void checkInvisible() {
		if(countdown == invisibleTime) player.setInvisible(false);
	}
	
	
	
	
	public void gameOver() {
		//System.out.println(MainFrame.user.getUsername());
		soundManager.stopMusic();
		soundManager.playMusic(soundManager.gameover);
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
	public boolean isFreeze() {
		return isFreeze;
	}
	public SoundManager getSoundManager() {
		return soundManager;
	}
	
	
}
