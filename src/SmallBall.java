import java.awt.Rectangle;

public class SmallBall extends Ball{
	private double vx = 2;
	private double vy = 10;
	public SmallBall(int x, int y){
		super(x,y,32,28);
		this.x = x;
		this.y = y;
	}
	@Override
	public void move() {
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
