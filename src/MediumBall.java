
public class MediumBall extends Ball{
	private double vx = 2;
	private double vy = -13;
	public MediumBall(int x, int y){
		super(x,y);
		this.x = x;
		this.y = y;
		width = 64;
		height = 53;
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
