
public class Player extends GameObject{
	//private int playerX = 350;
	//private int playerY = 338;
	
	public Player() {
		super(350,338,1,1);
	}
	
	public void goLeft(int num) {
		if(x > 10) x -= num;
	}
	public void goRight(int num) {
		if(x < 690) x += num;
	}
	
}
