
public class MediumBall extends Ball{
	private double vx = 2.25;
	private double vy = 13;
	public MediumBall(int x, int y){
		super(x,y,64,53);
		this.x = x;
		this.y = y;
	}
	@Override
	public void move() {
        x += vx;
        vy += gravity; 
        y += vy;
        if (x < 16 || x > 750 -  getWidth()) {
            vx = -vx;
            x = Math.max(16, Math.min(x, 750 -  getWidth())); 
        }

        if (y < 15 || y > 349) {
            vy = -vy;
            y = Math.max(15, Math.min(y, 349));
            vy = -13;
        }
    }
}
