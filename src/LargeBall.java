
public class LargeBall extends Ball{
	private double vx = 2.5;
	private double vy = 14.5;
	public LargeBall(int x, int y){
		super(x,y,96,80);
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

        if (y < 15 || y > 322) {
            vy = -vy;
            y = Math.max(15, Math.min(y, 322));
            vy = -14.5;
        }
    }
	
}
