
public class ExtraLargeBall extends Ball{
	private double vx = 3;
	private double vy = 16;
	public ExtraLargeBall(int x, int y){
		super(x,y);
		this.x = x;
		this.y = y;
		width = 128;
		height = 106;
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

        if (y < 15 || y > 296) {
            vy = -vy;
            y = Math.max(15, Math.min(y, 296)); 
            vy = -16;
        }
    }
	
}
