
public class Arrow extends GameObject{
	private int initalY;
	private int maxHeight = 300;
	public int totalPlus = 0;
	private int fixedTime = 99;
	private String type;
	public Arrow(int x, int y){
		super(x,y,18,0);
		this.x = x;
		this.y = y;
		this.initalY = y;
	}
	public void move() {
		y-=3;
		totalPlus += 1;
		height += 3;
	}
	public int getDrawY() {
		return initalY - height;
	}
	
	public int getX() {return (int) x;}
	public int getY() {return (int) y;}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public void setFixedTime(int time) {
		fixedTime = time;
	}
	public int getFixedTime() {
		return fixedTime;
	}
	
}
