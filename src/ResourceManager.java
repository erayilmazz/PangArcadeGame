import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
	public Image border,antalya,ankara,izmir,istanbul,playerImage,shootPlayerImage,emptyImage,healthImage,dynamiteImage,clockImage,fixedArrowImage,doubleArrowImage,black;
	public BufferedImage arrow0Image, arrowfImage;
	public List<Image> leftImages = new ArrayList<>();
	public List<Image> rightImages = new ArrayList<>();
	public List<Image> smallBallImages = new ArrayList<>();
	public List<Image> mediumBallImages = new ArrayList<>();
	public List<Image> largeBallImages = new ArrayList<>();
	public List<Image> extraLargeImages = new ArrayList<>();
	public List<Image> arrowImages = new ArrayList<>();
	public List<Image> blockx = new ArrayList<>();
	public List<Image> blocky = new ArrayList<>();
	public void loadResources() {
		ImageLoader id = new ImageLoader();
		border = id.loadImage("/resources/border.png",Color.WHITE);
		antalya = id.loadImage("/resources/antalya.png");
		ankara = id.loadImage("/resources/ankara.png");
		izmir = id.loadImage("/resources/izmir.png");
		istanbul = id.loadImage("/resources/istanbul.png");
		playerImage = id.loadImage("/resources/player.png",Color.GREEN);
		arrow0Image = id.loadBufferedImage("/resources/arrow.png",Color.RED);
		shootPlayerImage = id.loadImage("/resources/playerShoot.png",Color.green);
		emptyImage = id.loadImage("/resources/empty.png",Color.white);
		healthImage = id.loadImage("/resources/health.png",Color.GREEN);
		dynamiteImage = id.loadImage("/resources/dynamite.png",Color.GREEN);
		clockImage = id.loadImage("/resources/clock.png",Color.GREEN);
		fixedArrowImage = id.loadImage("/resources/fixedArrow.png",Color.GREEN);
		doubleArrowImage = id.loadImage("/resources/doubleArrow.png",Color.GREEN);
		black = id.loadImage("/resources/black.png");
		arrowfImage = id.loadBufferedImage("/resources/arrowf.png",Color.GREEN);
		for(int i = 0; i <= 4; i++) {
			leftImages.add(id.loadImage("/resources/playerLeft" + i + ".png", Color.green));
			rightImages.add(id.loadImage("/resources/playerRight" + i + ".png", Color.GREEN));
			smallBallImages.add(id.loadImage("/resources/small" + i + ".png",Color.GREEN));
			mediumBallImages.add(id.loadImage("/resources/Medium" + i + ".png",Color.GREEN));
			largeBallImages.add(id.loadImage("/resources/Large" + i + ".png",Color.GREEN));
			extraLargeImages.add(id.loadImage("/resources/ExtraLarge" + i + ".png",Color.GREEN));
			blockx.add(id.loadImage("/resources/blockx" + i + ".png"));
			blocky.add(id.loadImage("/resources/blocky" + i + ".png"));
		}
	}
	public Image getPlayerImage(Player player, int index) {
		if(player.isInvisible() && (index%5) == 0) {
			return emptyImage;
		}
		if(player.getDirection().equals("left")) {
			return leftImages.get(index/10);
		}
		else if (player.getDirection().equals("right")) {
			return rightImages.get(index/10);
		}else if(player.getDirection().equals("shoot")) {
			return shootPlayerImage;
		}
		else {
			return playerImage;
		}
	}
	public Image getBallImage(Ball ball) {
		int indexNum = ball.explodeImageIndex;
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
	public BufferedImage getArrowImage(Arrow arrow) {
		if(arrow.getWidth() <= 0 || arrow.getHeight() <= 0) return null;
		BufferedImage croppedImage = null;
		if(arrow.getType() == "normal" || arrow.getType() == "double")
			croppedImage = arrow0Image.getSubimage(0,0,arrow.getWidth(),arrow.getHeight());
		else if(arrow.getType() == "fixed")
			croppedImage = arrowfImage.getSubimage(0,0,arrow.getWidth(),arrow.getHeight());
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
	public Image getBlockImage(Block block) {
		if(block.getType() == 'x') return blockx.get(block.destroyImageIndex/5);
		else return blocky.get(block.destroyImageIndex/5);
	}
	public Image getArrowImage(String arrowType) {
		switch (arrowType) {
		case "fixed":
			return fixedArrowImage;
		case "double":
			return doubleArrowImage;
		default:
			return black;
		}
	}
	public Image getLevelImage(int currentLevel) {
		switch(currentLevel) {
		case 1:
			return antalya;
		case 2:
			return ankara;
		case 3:
			return izmir;
		case 4:
			return istanbul;
		default:
			return emptyImage;
		}
	}
}
