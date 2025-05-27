import java.awt.Rectangle;

public class Player extends GameObject{
	//private int playerX = 350;
	//private int playerY = 338;
	private String direction = "none";
	protected int width, height;
	private String diff;
	private boolean isInvisible = false;
	private int healthBar;
	public Player(String diff) {
		super(350,338,64,64);//64 64 
		this.diff = diff;
		setHealthBar(diff);
	}
	@Override
	public Rectangle getBounds() {
		switch(direction) {
		case "left": return new Rectangle(getX() + 5, getY(), getWidth() - 12, getHeight());
		case "right" : return new Rectangle(getX() + 5, getY(), getWidth() - 10, getHeight());
		default: return new Rectangle(getX() + 10, getY(), getWidth() - 12, getHeight());
		}
	}
	public void move() {
		if(direction == "left") {
			if(x > 10) x -= (2 * getMultiplier());
		}else if(direction == "right") {
			if(x < 690) x += (2 * getMultiplier());
		}
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
	public void increaseHealthBar() {
		healthBar++;
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
