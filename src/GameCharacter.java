
public abstract class GameCharacter {

	public static final int width=80, height=80;
	public static final int frameWidth = 1210;

	private int x, y;
	private int dx, dy;
	private boolean facingRight=false;
	private boolean dead;
	private boolean spaceBeingPressed = false;
	private int groundLevel;
	private int peak;
	private boolean reachedPeak = false;

	public GameCharacter() {
		
	}

	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y= y;
	}
	
	public void run(){
		
	}

}
