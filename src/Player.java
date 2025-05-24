
public class Player extends GameObject{
	//private int playerX = 350;
	//private int playerY = 338;
	private String direction = "none";
	protected int width, height;
	
	public Player() {
		super(350,338,10,10);
	}
	
	public void goLeft(int num) {
		if(x > 10) x -= num;
		direction = "left";
	}
	public void goRight(int num) {
		if(x < 690) x += num;
		direction = "right";
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
}
