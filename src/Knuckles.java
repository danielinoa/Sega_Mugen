import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;


public class Knuckles implements Runnable {

	private Image knuckles;
	private Image knucklesStandingRight =  Toolkit.getDefaultToolkit().getImage("k_standing_right.gif");
	private Image knucklesStandingLeft =  Toolkit.getDefaultToolkit().getImage("k_standing_left.gif");
	private Image knucklesRunningRight = Toolkit.getDefaultToolkit().getImage("k_run_right.gif");
	private Image knucklesRunningLeft = Toolkit.getDefaultToolkit().getImage("k_run_left.gif");
	private Image knucklesJumpRight = Toolkit.getDefaultToolkit().getImage("k_jumpup_right.gif");
	private Image knucklesJumpLeft = Toolkit.getDefaultToolkit().getImage("k_jumpup_left.gif");
	private Image knucklesPowerPunchR = Toolkit.getDefaultToolkit().getImage("k_POWERpunch_right.gif");
	private Image knucklesPowerPunchL = Toolkit.getDefaultToolkit().getImage("k_POWERpunch_left.gif");
	private Image knucklesPunchR = Toolkit.getDefaultToolkit().getImage("k_punch_right.gif");
	private Image knucklesPunchL = Toolkit.getDefaultToolkit().getImage("k_punch_left.gif");
	private Image knucklesHurtLeft = Toolkit.getDefaultToolkit().getImage("k_hurt_left.gif");
	private Image knucklesHurtRight = Toolkit.getDefaultToolkit().getImage("k_hurt_right.gif");
	private Image knucklesIcon = Toolkit.getDefaultToolkit().getImage("knucklesIcon.png");
	private final int X, Y;
	private int x, y;
	private int dx, dy;
	private final int lifeGaugeOriginalX = 840;
	private int lifeGaugeX=lifeGaugeOriginalX;
	private boolean facingRight=false;
	private static final int width=80, height=80;
	private static final int frameWidth = 1210;
	private int lifeGauge = 275;
	private boolean dead;
	private boolean hitting = false;
	private final int groundLevel;
	private final int peak;
	private boolean reachedPeak = false;

	/**
	 * Constructor sets character initial position and living state.
	 */
	public Knuckles(){
		x=1000;
		y=580;
		X=x;
		Y=y;
		groundLevel = y;
		peak = y-120;
		dead=false;
		knuckles = knucklesStandingLeft;
		x+=dx;
	}
	/**
	 * Position of the character in the x-axis
	 * @return x
	 */
	public int getX(){
		return this.x;
	}
	/**
	 * Position of the character in the y-axis
	 * @return y
	 */
	public int getY(){
		return this.y;
	}
	/**
	 * Sets character's x-coordinate
	 * @param x
	 */
	public void setX(int x){
		this.x = x;
	}
	/**
	 * Sets character's y-coordinate
	 * @param y
	 */
	public void setY(int y){
		this.y= y;
	}

	/**
	 * Paints character, its icon and its life gauge.
	 * @param g
	 * @param frame
	 */
	public void draw(Graphics g, ImageObserver frame){
		g.drawImage(knuckles, x, y, width, height, frame);
		g.drawImage(knucklesIcon, 1210-80, 75, width, height, frame);
		g.setColor(Color.WHITE);
		g.drawRoundRect(840, 100, 275, 25, 20, 20);
		if(lifeGauge>=140){
			g.setColor(Color.GREEN);
		} else if(lifeGauge>=60){
			g.setColor(Color.ORANGE);
		} else if(lifeGauge>=0){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(lifeGaugeX, 100, lifeGauge, 25, 20, 20);
	}

	/**
	 * This method makes the character jump by
	 * decreasing-and-incrementing the character's
	 * y-axis withing a determined short period of time.
	 */
	public void jump(){
		if(y>peak&&reachedPeak==false){
			y=y+(dy*2);
			if(y==peak||y<peak){
				reachedPeak=true;
			}
			if(facingRight&&y!=groundLevel){
				knuckles=knucklesJumpRight;
			}
			if(!facingRight&&y!=groundLevel){
				knuckles=knucklesJumpLeft;
			}
		}
		if(reachedPeak&&y<groundLevel){
			// When falling
			y=y-(dy*4);
			if(y==groundLevel){
				reachedPeak=false;
				dy=0;
				if(facingRight){
					knuckles=knucklesStandingRight;
				} else {
					knuckles=knucklesStandingLeft;
				}
			}
		}
	}

	// Thread applied to characters movements
	public void run(){
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			x+=dx;
			boundaryCheck(dx);
			jump();
		}
	}

	/**
	 * Detects keyboard's keys pressed by user and
	 * determines the action of the character based on
	 * these.
	 * @param e Pressed Key 
	 *  */
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT){
			if(y==groundLevel){
				knuckles = knucklesRunningLeft;
			}
			if(y!=groundLevel){
				knuckles = knucklesJumpLeft;
			}
			dx=-10;
			facingRight = false;
		} else if(key==KeyEvent.VK_RIGHT){
			if(y==groundLevel){
				knuckles = knucklesRunningRight;
			}
			if(y!=groundLevel){
				knuckles = knucklesJumpRight;
			}
			dx=10;
			facingRight = true;
		} else if(key==KeyEvent.VK_UP){
			// System.out.println("y: "+y+" groundLevel: "+groundLevel+" peak:"+peak);
			if(reachedPeak==false){
				dy=-5;
				jump();
			}
		} else if(key==KeyEvent.VK_COMMA){
			hitting = true;
			if(facingRight)
				knuckles = knucklesPowerPunchR;
			else 
				knuckles = knucklesPowerPunchL;
		} else if(key==KeyEvent.VK_PERIOD){
			hitting = true;
			if(facingRight)
				knuckles = knucklesPunchR;
			else 
				knuckles = knucklesPunchL;
		}
	}

	/**
	 * Detects keyboard's keys released by user and
	 * determines the action of the character based on
	 * these.
	 * @param e Released Key 
	 *  */
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT){
			knuckles = knucklesStandingLeft;
			dx=0;
		} else 	if(key==KeyEvent.VK_RIGHT){
			knuckles = knucklesStandingRight;
			dx=0;
		} else if(key==KeyEvent.VK_COMMA){
			hitting = false;
			if(facingRight){
				knuckles = knucklesStandingRight;
			} else {
				knuckles = knucklesStandingLeft;
			}
		} else if(key==KeyEvent.VK_PERIOD){
			hitting = false;
			if(facingRight){
				knuckles = knucklesStandingRight;
			} else {
				knuckles = knucklesStandingLeft;
			}
		}
	}

	/**
	 * Checks if the character is attacking or not
	 * @return hitting
	 */
	public boolean hitting(){
		return hitting;
	}

	/**
	 * Checks if the character is facing right or not
	 * @return boolean
	 */
	public boolean facingRight(){
		if(facingRight==true){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Decreases the characters life gauge if hit from the right, and puts the 
	 * character into a dead state if the gauge goes below zero.
	 */
	public void hitFromRight(){
		lifeGauge-=3;
		dead = lifeGauge<=0 ? true:false;
		lifeGaugeX+=3;
		knuckles = knucklesHurtRight;
		int conditioner = -60;
		x+=conditioner;
		boundaryCheck(conditioner);
	}

	/**
	 * Decreases the characters life gauge if hit from the left, and puts the 
	 * character into a dead state if the gauge goes below zero.
	 */
	public void hitFromLeft(){
		lifeGauge-=3;
		dead = lifeGauge<=0 ? true:false;
		lifeGaugeX+=3;
		knuckles = knucklesHurtLeft;
		int conditioner = 60;
		x+=conditioner;
		boundaryCheck(conditioner);
	}

	/**
	 * Limits the character to go past the walls of the frame.
	 * @param conditioner
	 */
	public void boundaryCheck(int conditioner){
		if(x<0||x>frameWidth){
			x-=conditioner;
		}
	}

	/**
	 * Checks if the character is dead.
	 * @return dead
	 */
	public boolean isDead(){
		return dead;
	}

	/**
	 * Sets life gauge to its original status
	 * and sets character alive.
	 */
	public void replenishLife(){
		lifeGauge = 275;
		dead=false;
		setOriginalPosition();
	}

	/**
	 * Sets character to its original position.
	 */
	public void setOriginalPosition(){
		x=X;
		y=Y;
		lifeGaugeX=lifeGaugeOriginalX;
		facingRight=false;
		knuckles=knucklesStandingLeft;

	}
	
	/**
	 * lifeGauge's health level
	 * @return lifeGauge
	 */
	public int health(){
		return lifeGauge;
	}
}