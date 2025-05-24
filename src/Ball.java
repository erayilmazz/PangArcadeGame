
public class Ball extends GameObject{
	protected int x,y;
	private double vx = 2;
	private double vy = -13;
	protected double gravity = 0.5; 
	protected int width, height;
	protected boolean isExploded;
	public int expodeImageIndex;
	private boolean firstMove = true;
	public Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		isExploded = false;
	}
	public void move() {
		if(firstMove) {
			vy = 0;
			firstMove = false;
		}
        x += vx;
        vy += gravity; 
        y += vy;
        if (x < 16 || x > 750 - width) {
            vx = -vx;
            x = Math.max(16, Math.min(x, 750 - width)); 
        }

        if (y < 15 || y > 350) {
            vy = -vy;
            y = Math.max(15, Math.min(y, 350)); 
        }
    }
	public boolean isExploded() {
		return isExploded;
	}
	public void setExploded(boolean isExploded) {
		this.isExploded = isExploded;
	}
	public int getX() {return  x;}
	public int getY() {return  y;}
	public void reverseX() {vx = -vx;}
}
