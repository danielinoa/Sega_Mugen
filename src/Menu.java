import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Menu implements MouseListener, MouseMotionListener{


	private boolean stageChosen=false;
	private boolean visible=true;
	private boolean versusHoverOff = true, aboutHoverOff=true, quitHoverOff=true, menuHoverOff=false;
	private boolean versusButtonClicked = false;
	private Image background = Toolkit.getDefaultToolkit().getImage("resources/menuBackground.jpg");
	private int width, height;
	private Image versusButton = Toolkit.getDefaultToolkit().getImage("resources/versus.png");
	private Image versusHoverButton = Toolkit.getDefaultToolkit().getImage("resources/versus_hover.png");
	private int versusX = 587, versusY = 220;
	private int versusWidth = 170, versusHeight = 130;
	private Image aboutButton = Toolkit.getDefaultToolkit().getImage("resources/about.png");
	private Image aboutHoverButton = Toolkit.getDefaultToolkit().getImage("resources/about_hover.png");
	private int aboutY = versusY+150;
	private Image quitButton = Toolkit.getDefaultToolkit().getImage("resources/quit.png");
	private Image quitHoverButton = Toolkit.getDefaultToolkit().getImage("resources/quit_hover.png");
	private int quitY = aboutY+150;
	private Image scArena = Toolkit.getDefaultToolkit().getImage("resources/sc_arena.jpg");
	private Image mkArena = Toolkit.getDefaultToolkit().getImage("resources/mk_arena.jpg");
	private Image arena1 = Toolkit.getDefaultToolkit().getImage("resources/Arena.gif");
	private Image beachArena = Toolkit.getDefaultToolkit().getImage("resources/b_arena.jpg"); 
	private Image mainMenu = Toolkit.getDefaultToolkit().getImage("resources/mainMenu.png");
	private Image menuButton = Toolkit.getDefaultToolkit().getImage("resources/menu_Button.png");
	private Image menuHoverButton = Toolkit.getDefaultToolkit().getImage("resources/menu_HoverButton.png");
	private Image stageSelection = Toolkit.getDefaultToolkit().getImage("resources/stageSelection.png"); 
	private int stageNumber = 0;

	/**
	 * Constructor sets dimension of the menu
	 * @param width Width of the menu background image
	 * @param height Height of the menu background image
	 */
	public Menu(int width, int height){
		this.width=width;
		this.height=height;
	}
	/**
	 * Paints the menu background image, the menu buttons, and the different arena panels.
	 * @param g
	 * @param frame
	 */
	public void draw(Graphics g, Main frame){
		if(visible){
			g.drawImage(background, 0, 0, width, height, frame);
			if(versusButtonClicked==false){
				g.drawImage(mainMenu, versusX-140, versusY-180, versusWidth+300, versusHeight, frame);
				if(versusHoverOff){
					g.drawImage(versusButton, versusX, versusY, versusWidth, versusHeight, frame);
				} else {
					g.drawImage(versusHoverButton, versusX, versusY, versusWidth, versusHeight, frame);
				}
				if(aboutHoverOff){
					g.drawImage(aboutButton, versusX, aboutY, versusWidth, versusHeight, frame);
				} else {
					g.drawImage(aboutHoverButton, versusX, aboutY, versusWidth, versusHeight, frame);
				}
				if(quitHoverOff){
					g.drawImage(quitButton, versusX, quitY, versusWidth, versusHeight, frame);
				} else {
					g.drawImage(quitHoverButton, versusX, quitY, versusWidth, versusHeight, frame);
				}
			} else if(versusButtonClicked){
				g.drawImage(stageSelection, versusX-140, versusY-180, versusWidth+300, versusHeight, frame);
				g.drawImage(scArena, versusX+200, versusY-50, versusWidth, versusHeight, frame);
				g.drawImage(mkArena, versusX-200, versusY-50, versusWidth, versusHeight, frame);
				g.drawImage(arena1, versusX+200, versusY+150, versusWidth, versusHeight, frame);
				g.drawImage(beachArena, versusX-200, versusY+150, versusWidth, versusHeight, frame);
				if(menuHoverOff){
					g.drawImage(menuButton, versusX, versusY+300, versusWidth, versusHeight, frame);
				} else {
					g.drawImage(menuHoverButton, versusX, versusY+300, versusWidth, versusHeight, frame);
				}
			}
		}
	}
	/**
	 * Set Menu to be visible
	 */
	public void setVisible(){
		visible=true;
	}
	/**
	 * Set Menu to be NOT visible
	 */
	public void setInvisible(){
		visible=false;
	}
	/**
	 * Checks if the menu is visible or not.
	 * @return visible 
	 */
	public boolean isVisible(){
		return visible;
	}
	/**
	 * Checks if the versusButton was clicked
	 * @return versusButtonClicked
	 */
	public boolean versusButtonClicked(){
		return versusButtonClicked;
	}
	/**
	 * Allows the multi-action events based on the mouse click
	 * i.e. Let's menu sections be browsed based on mouse click to 
	 * the custom-made buttons game.
	 * @param e Mouse event selection.
	 *  */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		// In main menu
		if(x>=610&&x<=735&&y>=255&&y<=320&&!versusButtonClicked){
			versusButtonClicked=true;
		}
		// In main menu
		if(x>=610&&x<=735&&y>=255+300&&y<=320+300&&!versusButtonClicked){
			System.exit(0);
		}
		// already in the stage selection menu
		if(versusButtonClicked&&!stageChosen){
			if(x>=389&&y>=173&&x<=556&&y<=300){
				stageChosen=true;
				stageNumber=0; 
			}
			if(x>=789&&y>=172&&x<=957&&y<=300){
				stageChosen=true;
				stageNumber=1;
			}
			if(x>=389&&x<=556&&y>=372&&y<=501){
				stageChosen=true;
				stageNumber=2;
			}
			if(x>=789&&x<=957&&y>=372&&y<=501){
				stageChosen=true;
				stageNumber=3;
			}
			// Going to main menu
			if(x>=versusX&&x<=versusX+versusWidth
					&&y>=versusY+300&&y<=versusY+300+versusHeight){
				versusButtonClicked=false;
			}
		}
	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent arg0) {

	}
	public void mouseDragged(MouseEvent e) {

	}
	/**
	 * Detects where the mouse is on the window frame.
	 * @param e Position  
	 *  */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		//	System.out.println("X entered: "+x+" Y entered: "+y);
		if(x>=610&&x<=735&&y>=255&&y<=320){
			versusHoverOff=false;
		} else {
			versusHoverOff=true;
		}
		if(x>=610&&x<=735&&y>=255+150&&y<=320+150){
			aboutHoverOff=false;
		} else {
			aboutHoverOff=true;
		}
		if(x>=610&&x<=735&&y>=255+300&&y<=320+300){
			quitHoverOff=false;
		} else {
			quitHoverOff=true;
		}
		if(versusButtonClicked&&x>=versusX&&x<=versusX+versusWidth
				&&y>=versusY+300&&y<=versusY+300+versusHeight){
			menuHoverOff=false;
		} else {
			menuHoverOff=true;
		}
	}
	/**
	 * Checks for the chosen stage id number 
	 * @return Stage number
	 */
	public int stageNumber(){
		return stageNumber;
	}
	/**
	 * Check if any stage has so far been chosen.
	 * @return stageChosen
	 */
	public boolean stageChosen(){
		return stageChosen;
	}
	/**
	 * Sets the boolean status of the variable 
	 * that controls whether if a stage has been
	 * chosen or not.
	 * @param state
	 */
	public void	setStageState(boolean state){
		stageChosen=state;
	}
}