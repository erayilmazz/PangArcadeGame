
public class Ball extends GameObject{
	private double x,y;
	private double vx = 2;
	private double vy = -13;
	protected double gravity = 0.5; 
	private int c = 1;
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		this.x = x;
		this.y = y;
	}
	public void move() {
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
	public int getX() {return (int) x;}
	public int getY() {return (int) y;}
}
