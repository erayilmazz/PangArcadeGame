import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;


public class GameManager {
	public Image border, playerImage, ballImage;
	private ImageLoader id = new ImageLoader();
	Player player;
	private LinkedList<Ball> balls;
	public GameManager(){
		player = new Player();
		loadResources();
		loadLevel(1);
		
	}
	private void loadResources() {
		border = id.loadImage("/resources/border.png");
		playerImage = id.loadImage("/resources/player.png",Color.GREEN);
		ballImage = id.loadImage("/resources/ball.png",Color.GREEN);
	}
	private void loadLevel(int level) {
		balls = new LinkedList<>();
		if(level == 1) {
			balls.add(new Ball(350,338,1,1));
		}
	}
	public LinkedList<Ball> getBalls() {
		return balls;
	}
	
}
