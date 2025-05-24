import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class GameManager {
	public Image border, playerImage, smallBallImage, mediumBallImage, largeBallImage, extraLargeImage;
	public List<Image> leftImages = new ArrayList<>();
	public List<Image> rightImages = new ArrayList<>();
	private int currentImageIndex = 0;
	private ImageLoader id = new ImageLoader();
	Player player;
	private LinkedList<Ball> balls;
	private GamePanel gamePanel;
	public GameManager(){
		player = new Player();
		loadResources();
		loadLevel(1);
		//startGameLoop();
		
	}
	public void startGameLoop() {
		new Thread(() -> {
			while(true) {
				for(Ball ball :balls) {
					ball.move();
				}
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
		smallBallImage = id.loadImage("/resources/small0.png",Color.GREEN);
		mediumBallImage = id.loadImage("/resources/Medium0.png",Color.GREEN);
		largeBallImage = id.loadImage("/resources/Large0.png",Color.GREEN);
		extraLargeImage = id.loadImage("/resources/ExtraLarge0.png",Color.GREEN);
		for(int i = 0; i <= 4; i++) {
			leftImages.add(id.loadImage("/resources/playerLeft" + i + ".png", Color.GREEN));
			rightImages.add(id.loadImage("/resources/playerRight" + i + ".png", Color.GREEN));
		}
	}
	public Image getPlayerImage() {
		if(player.getDirection().equals("left")) {
			return leftImages.get(currentImageIndex/10);
		}
		else if (player.getDirection().equals("right")) {
			return rightImages.get(currentImageIndex/10);
		}else {
			return playerImage;
		}
		
	}
	public void updateAnimation() {
		currentImageIndex++;
		if(currentImageIndex == 50) currentImageIndex = 0;
	}
	private void loadLevel(int level) {
		balls = new LinkedList<>();
		if(level == 1) {
			balls.add(new SmallBall(100,350));//350
			balls.add(new MediumBall(100,300));//300
			balls.add(new LargeBall(100,250));//250
			balls.add(new ExtraLargeBall(100,200));//200
		}
	}
	public Image getBallImage(Ball ball) {
		if(ball instanceof SmallBall) return smallBallImage;
		if(ball instanceof MediumBall) return mediumBallImage;
		if(ball instanceof LargeBall) return largeBallImage;
		if(ball instanceof ExtraLargeBall) return extraLargeImage;
		return mediumBallImage;
	}
	public LinkedList<Ball> getBalls() {
		return balls;
	}
	public void setGamePanel(GamePanel gamePanel) {
	    this.gamePanel = gamePanel;
	}
	
}
