
public class FallingObject extends GameObject{
	private String object;
	public FallingObject(int x, int y, int width, int height,String object){
		super(x,y,width,height);
		this.object=object;
	}
	public void move() {
		if(y < 380) {
			y+=5;
		}
	}
	public String getObject() {
		return object;
	}
}
