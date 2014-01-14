import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class Sonic implements Runnable{

	private Image sonic;
	private Image sonicStandingRight =  Toolkit.getDefaultToolkit().getImage("standing_right.gif");
	private Image sonicStandingLeft =  Toolkit.getDefaultToolkit().getImage("standing_left.gif");
	private Image sonicRunningRight = Toolkit.getDefaultToolkit().getImage("run_right.gif");
	private Image sonicRunningLeft = Toolkit.getDefaultToolkit().getImage("run_left.gif");
	private Image sonicJumpRight = Toolkit.getDefaultToolkit().getImage("jumpUp_right.gif");
	private Image sonicJumpLeft = Toolkit.getDefaultToolkit().getImage("jumpUp_left.png");
	private Image sonicPowerKickR = Toolkit.getDefaultToolkit().getImage("powerKick_right.gif");
	private Image sonicPowerKickL = Toolkit.getDefaultToolkit().getImage("powerKick_left.gif");
	private Image sonicKickR = Toolkit.getDefaultToolkit().getImage("s_kick_right.gif");
	private Image sonicKickL = Toolkit.getDefaultToolkit().getImage("05.png");
	private Image sonicHurtLeft = Toolkit.getDefaultToolkit().getImage("sonicHurtLeft.gif");
	private Image sonicHurtRight = Toolkit.getDefaultToolkit().getImage("sonicHurtRight.gif");
	private Image sonicIcon = Toolkit.getDefaultToolkit().getImage("sonicIcon.png");
	private final int X, Y;
	private int x, y;
	private int dx, dy;
	private boolean facingRight=true;
	private int width=80, height=80;
	private int frameWidth = 1210;
	private int lifeGauge = 275;
	private boolean dead;
	private boolean hitting = false;
	private final int groundLevel;
	private final int peak;
	private boolean reachedPeak = false;

	/**
	 * Constructor sets character initial position and living state.
	 */
	public Sonic(){
		x=200;
		y=580;
		X=x;
		Y=y;
		groundLevel = y;
		peak = y-120;
		dead=false;
		sonic = sonicStandingRight;
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
		g.drawImage(sonic, x, y, width, height, frame);
		g.drawImage(sonicIcon, 80, 80, width, height, frame);
		g.setColor(Color.WHITE);
		g.drawRoundRect(170, 100, 275, 25, 20, 20);
		if(lifeGauge>=140){
			g.setColor(Color.GREEN);
		} else if(lifeGauge>=60){
			g.setColor(Color.ORANGE);
		} else if(lifeGauge>=0){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(170, 100, lifeGauge, 25, 20, 20);
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
				sonic=sonicJumpRight;
			}
			if(!facingRight&&y!=groundLevel){
				sonic=sonicJumpLeft;
			}
		}
		if(reachedPeak&&y<groundLevel){
			// When falling goes faster because of "gravity"
			y=y-(dy*4);
			if(y==groundLevel){
				reachedPeak=false;
				dy=0;
				if(facingRight){
					sonic=sonicStandingRight;
				} else {
					sonic=sonicStandingLeft;
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
		if(key==KeyEvent.VK_A){
			if(y==groundLevel){
				sonic = sonicRunningLeft;
			}
			if(y!=groundLevel){
				sonic = sonicJumpLeft;
			}
			dx=-10;
			facingRight = false;
		} else if(key==KeyEvent.VK_D){
			if(y==groundLevel){
				sonic = sonicRunningRight;
			}
			if(y!=groundLevel){
				sonic = sonicJumpRight;
			}
			dx=10;
			facingRight = true;
		} else if(key==KeyEvent.VK_W){
			//	System.out.println("y: "+y+" groundLevel: "+groundLevel+" peak:"+peak);
			if(reachedPeak==false){
				dy=-5;
				jump();
			}
		} else if(key==KeyEvent.VK_1){
			hitting = true;
			if(facingRight)
				sonic = sonicPowerKickR;
			else
				sonic = sonicPowerKickL;	
		} else if(key==KeyEvent.VK_2){
			hitting = true;
			if(facingRight)
				sonic = sonicKickR;
			else
				sonic = sonicKickL;
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
		if(key==KeyEvent.VK_A){
			sonic = sonicStandingLeft;
			dx=0;
		} else if(key==KeyEvent.VK_D){
			sonic = sonicStandingRight;
			dx=0;
		} else if(key==KeyEvent.VK_1){
			hitting = false;
			if(facingRight){
				sonic = sonicStandingRight;
			} else {
				sonic = sonicStandingLeft;
			}
		} else if(key==KeyEvent.VK_2){
			hitting = false;
			if(facingRight){
				sonic = sonicStandingRight;
			} else {
				sonic = sonicStandingLeft;
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
		// ternary if statement
		dead = lifeGauge<=0 ? true:false;
		sonic = sonicHurtRight;
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
		// ternary if statement
		dead = lifeGauge<=0 ? true:false;
		sonic = sonicHurtLeft;
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
			x-=conditioner; // x = x - conditioner
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
		facingRight=true;
		sonic=sonicStandingRight;
	}

	/**
	 * lifeGauge's health level
	 * @return lifeGauge
	 */
	public int health(){
		return lifeGauge;
	}
}