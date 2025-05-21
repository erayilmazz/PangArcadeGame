import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class GameManager {
	public Image border, playerImage, ballImage;
	public List<Image> leftImages = new ArrayList<>();
	public List<Image> rightImages = new ArrayList<>();
	private int currentImageIndex = 0;
	private ImageLoader id = new ImageLoader();
	Player player;
	private LinkedList<Ball> balls;
	public GameManager(){
		player = new Player();
		loadResources();
		loadLevel(1);
		startGameLoop();
		
	}
	private void startGameLoop() {
		new Thread(() -> {
			while(true) {
				for(Ball ball : balls) {
					ball.move();
				}
				
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
		ballImage = id.loadImage("/resources/ball.png",Color.GREEN);
		for(int i = 0; i <= 4; i++) {
			leftImages.add(id.loadImage("/resources/playerLeft" + i + ".png", Color.GREEN));
			rightImages.add(id.loadImage("/resources/playerRight" + i + ".png", Color.GREEN));
		}
	}
	public Image getPlayerImage() {
		if(player.getDirection().equals("left")) {
			return leftImages.get(currentImageIndex);
		}
		else if (player.getDirection().equals("right")) {
			return rightImages.get(currentImageIndex);
		}else {
			return playerImage;
		}
		
	}
	public void updateAnimation() {
		currentImageIndex++;
		if(currentImageIndex == 5) currentImageIndex = 0;
	}
	private void loadLevel(int level) {
		balls = new LinkedList<>();
		if(level == 1) {
			balls.add(new Ball(350,338,64,53));//for medium
		}
	}
	public LinkedList<Ball> getBalls() {
		return balls;
	}
	
}
