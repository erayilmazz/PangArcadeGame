import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;




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
	protected int countdown = 98;
	protected int invisibleTime;
	protected int freezeTime;
	protected boolean isFreeze = false;
	protected int arrowTime;
	private int currentLevel;
	private boolean isScoreScreen = false;
	private boolean isGameOver = false;
	private String gameOverReason;
	
	private GamePanel gamePanel;
	private SubPanel subPanel;
	private ResourceManager resourceManager;
	private CollisionManager collisionManager;
	private ObjectManager objectManager;
	private SoundManager soundManager;
	private Thread gameLoopThread;
	private Thread countdownThread;
	
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
		loadLevel(1,diff);
	}
	boolean running;
	public void startGameLoop() {
		running = true;
		gameLoopThread = new Thread(new GameLoop(this));
		countdownThread = new Thread(new Countdown(this));
		gameLoopThread.start();
		countdownThread.start();
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
		gamePanel.revalidate();
		gamePanel.repaint();
        startGameLoop();
	}
	public void loadNextLevel() {
		running = false;
		soundManager.stopMusic();
		soundManager.playMusic(soundManager.bonus);
		timeBonus = countdown * 10;
		totalScore += (score + timeBonus);
		isScoreScreen = true;
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
		subPanel.modifySubPanel();
		new Thread(() -> {
			try {
				Thread.sleep(5000);
				loadLevel(currentLevel,diff);
				isScoreScreen = false;
				isGamePaused = true;
				timeBonus = 0;
				score = 0;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	public void createArrow() {
		objectManager.createArrow();
	}
	
	
	public void gameOver(char reason) {
		setGameOverReason(reason);
		soundManager.stopMusic();
		soundManager.playMusic(soundManager.gameover);
		if(reason == 'f') {
			timeBonus = countdown * 10;
			totalScore += (score + timeBonus);
		}else {
			totalScore += score;
		}
		isGameOver = true;
		gamePanel.repaint();
		subPanel.modifySubPanel();
		MainFrame.user.saveScore(totalScore);
	}
	
	public String getGameOverReason() {
		return gameOverReason;
	}
	
	public void setGameOverReason(char reason) {
		switch(reason) {
		case('f'): 
			gameOverReason = "GAME COMPLETED";
			break;
		case('t'): gameOverReason = "TIME OVER";
			break;
		case('d'): gameOverReason = "YOU DIED";
			break;
		}
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
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public SubPanel getSubPanel() {
		return subPanel;
	}
	public CollisionManager getCollisionManager() {
		return collisionManager;
	}
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
}
