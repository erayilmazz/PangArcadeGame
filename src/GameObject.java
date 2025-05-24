import java.awt.Rectangle;

public abstract class GameObject {
	protected int x,y;
	protected int width, height;
	
	
	//public abstract void update();
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		width = 0;
		height = 0;
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	
}
