
public class Arrow extends GameObject{
	public Arrow(int x, int y){
		super(x,y,18,15);
		this.x = x;
		this.y = y;
	}
	public void move() {
		y-=3;
	}
	public int getX() {return (int) x;}
	public int getY() {return (int) y;}
}
