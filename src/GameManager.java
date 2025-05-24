import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class GameManager {
	public Image border, playerImage, arrow0Image;
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
	private GamePanel gamePanel;
	public GameManager(){
		player = new Player();
		balls = new LinkedList <>();
		arrows = new LinkedList <>();
		loadResources();
		loadLevel(1);
		//startGameLoop();
		
	}
	public void startGameLoop() {
		new Thread(() -> {
			while(true) {
				for(Ball ball :balls) {
					if(ball.isExploded()) ball.expodeImageIndex++;
					else ball.move();
				}
				for(Arrow arrow : arrows) {
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

	private void loadLevel(int level) {
		balls = new LinkedList<>();
		if(level == 1) {
			balls.add(new SmallBall(100,350));//350
			balls.add(new MediumBall(100,300));//300
			balls.add(new LargeBall(100,250));//250
			balls.add(new ExtraLargeBall(100,200));//200
		}
	}
	
	public void createArrow() {
		arrows.add(new Arrow(player.getX() + 18, player.getY()));
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
	public Image getBallImage(Ball ball) {
		if(ball instanceof SmallBall && !ball.isExploded()) return smallBallImages.get(0);
		else if(ball instanceof SmallBall && ball.isExploded()){
			int indexNum = ball.expodeImageIndex;
			if(indexNum == 10) balls.remove(ball);
			else return smallBallImages.get(indexNum/2);
		}
		if(ball instanceof MediumBall && !ball.isExploded()) return mediumBallImages.get(0);
		else if(ball instanceof MediumBall && ball.isExploded()){
			int indexNum = ball.expodeImageIndex;
			if(indexNum == 10) balls.remove(ball);
			else return mediumBallImages.get(indexNum/2);
		}
		if(ball instanceof LargeBall && !ball.isExploded()) return largeBallImages.get(0);
		else if(ball instanceof LargeBall && ball.isExploded()){
			int indexNum = ball.expodeImageIndex;
			if(indexNum == 10) balls.remove(ball);
			else return largeBallImages.get(indexNum/2);
		}
		if(ball instanceof ExtraLargeBall && !ball.isExploded()) return extraLargeImages.get(0);
		else if(ball instanceof ExtraLargeBall && ball.isExploded()){
			int indexNum = ball.expodeImageIndex;
			if(indexNum == 10) balls.remove(ball);
			else return extraLargeImages.get(indexNum/2);
		}
		return null;
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
	
}
