
public class MediumBall extends Ball{
	private double vx = 2.25;
	private double vy = 13;
	private  int move = 0;
	private int maxMove;
	private boolean firstMove = true;
	public MediumBall(int x, int y, String diff){
		super(x,y,64,53);
		this.x = x;
		this.y = y;
		if(diff == "novice") maxMove = 3;
		if(diff == "intermediate") maxMove = 2;
		if(diff == "advanced") maxMove = 1;
	}
	@Override
	public void move() {
		if(isFirstCollisionBlock) {
			reverseX();
			reverseY();
			isFirstCollisionBlock = false;
		}
		move++;
		if(firstMove) {
			vy = 0;
			firstMove = false;
		}
		if(move == maxMove) {
			move = 0;
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
	public int getX() {return  x;}
	public int getY() {return  y;}
	public void reverseX() {vx = -vx;}
	public void reverseY() {vy = -vy;}
	public void setVy(double vy) {
		this.vy = vy;
	}
	public double getVy() {
		return vy;
	}
}
