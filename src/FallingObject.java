
public class FallingObject extends GameObject{
	private String object;
	private boolean isFalling = true;
	public FallingObject(int x, int y, int width, int height,String object){
		super(x,y,width,height);
		this.object=object;
	}
	public void move() {
		if(getY() > 376) isFalling = false;
		if(isFalling == true) {
			y+=5;
		}
	}
	public String getObject() {
		return object;
	}
	public boolean isFalling() {
		return isFalling;
	}
	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}
	
}
