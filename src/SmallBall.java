

public class SmallBall extends Ball{
	private double vx = 2;
	private double vy = 10;
	private  int move = 0;
	private int maxMove;
	boolean firstMove = true;
	public SmallBall(int x, int y,String diff){
		super(x,y,32,28);
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
		//System.out.print(move);
		if(move == maxMove) {
			move = 0;
			x += vx;
	        vy += gravity; 
	        y += vy;
	        //eğer sınır değerlerinin dışındaysa vy değeri daha az olsun
	        if (x < 16 || x > 750 - getWidth()) {
	            vx = -vx;
	            x = Math.max(16, Math.min(x, 750 - getWidth())); 
	        }
	        if (y < 15 || y > 374) {
	            vy = -vy;
	            y = Math.max(15, Math.min(y, 374)); 
	            vy= -10;
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
