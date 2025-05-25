
public class Player extends GameObject{
	//private int playerX = 350;
	//private int playerY = 338;
	private String direction = "none";
	protected int width, height;
	private String diff;
	private boolean isInvisible = false;
	private int healthBar;
	public Player(String diff) {
		super(350,338,10,10);
		this.diff = diff;
		setHealthBar(diff);
	}
	
	public void goLeft(int num) {
		if(x > 10) x -= (num * getMultiplier());
		direction = "left";
	}
	public void goRight(int num) {
		if(x < 690) x += (num * getMultiplier());
		direction = "right";
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	private double getMultiplier() {
		if(diff == "novice") return 2;
		else if(diff == "intermediate") return 1.5;
		else if(diff == "advanced") return 1;
		else return 1;
		
	}
	public boolean isInvisible() {
		return isInvisible;
	}
	public void setInvisible(boolean isInvisible) {
		this.isInvisible = isInvisible;
	}
	public int getHealthBar() {
		return healthBar;
	}
	public void setHealthBar(int healthBar) {
		this.healthBar = healthBar;
	}
	public void decreaseHealthBar() {
		healthBar--;
	}
	public void setHealthBar(String diff) {
		if(diff.equals("novice")) setHealthBar(3);
		else if(diff.equals("intermediate")) setHealthBar(2);
		else if(diff.equals("advanced")) setHealthBar(1);
	}
	public void resetCor() {
		this.x = 350;
		this.y = 338;
	}
	
}
