
public class SmallBall extends Ball{
	private double vx = 2;
	private double vy = 10;
	public SmallBall(int x, int y){
		super(x,y);
		this.x = x;
		this.y = y;
		width = 32;
		height = 28;
	}
	@Override
	public void move() {
        x += vx;
        vy += gravity; 
        y += vy;
        //eğer sınır değerlerinin dışındaysa vy değeri daha az olsun
        if (x < 16 || x > 750 - width) {
            vx = -vx;
            x = Math.max(16, Math.min(x, 750 - width)); 
        }
        if (y < 15 || y > 374) {
            vy = -vy;
            y = Math.max(15, Math.min(y, 374)); 
            vy= -10;
        }
    }
}
