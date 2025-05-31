
public class Block extends GameObject{
	private boolean isDestroyed;
	public int destroyImageIndex;
	private char type;
	public Block(int x, int y, int width, int height, char type) {
		super(x, y, width, height);
		this.type = type;
		isDestroyed = false;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public char getType() {
		return type;
	}
	
	
}
