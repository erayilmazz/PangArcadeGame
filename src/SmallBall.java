
public class SmallBall extends Ball{
	private double vx = 2;
	private double vy = -13;
	public SmallBall(int x, int y){
		super(x,y);
		this.x = x;
		this.y = y;
		width = 10;
		height = 10;
	}
	@Override
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
}
