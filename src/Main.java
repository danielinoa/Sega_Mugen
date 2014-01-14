/**
 * @author Daniel Inoa
 */

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
	private JFrame frame;
	private Menu menu;
	private Sonic sonic;
	private Knuckles knuckles;
	private Arena arena;
	private URL url;
	private Thread th = new Thread();
	private AudioClip backgroundMusic;
	private boolean fightingMusicPlaying = false;
	private Image knucklesWins = Toolkit.getDefaultToolkit().getImage("resources/knucklesWins.png"); 
	private Image sonicWins = Toolkit.getDefaultToolkit().getImage("resources/sonicWins.png"); 
	private Image SEGA = Toolkit.getDefaultToolkit().getImage("resources/segaMugen.png");
	private boolean intro=true;

	// Runner
	public static void main(String[] args){
		String title= "SEGA m.u.g.e.n.";
		Main runner= new Main(title, 1279, 700);
	}

	/**
	 * Instantiate menu, characters, assign threads to characters,
	 * loads menu music, and other visual objects.
	 * Also assigns the dimensions of the window frame and its name.
	 * @param title
	 * @param width
	 * @param height
	 */
	public Main(String title, int width, int height){
		super();
		layoutSetup(title, width, height);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		menu = new Menu(width, height);
		arena = new Arena(width, height);
		sonic = new Sonic();
		knuckles = new Knuckles();
		Thread[] t = {new Thread(sonic), new Thread(knuckles)};
		t[1].start();
		t[0].start();
		loadMenuMusic();
		frame.setVisible(true);
	}

	/**
	 * pre-loads components to be drawn by the paint component method.
	 * @param g
	 */
	public void paintComponent(Graphics g){
		Image offImage = createImage(getWidth(), getHeight());
		Graphics offGC = offImage.getGraphics();
		paintOneFrame(offGC);
		g.drawImage(offImage, 0, 0, null);
	}

	/**
	 * Calls the draw (painting) method of the Game's classes. In
	 * order to draw the menus, characters, and arenas. 
	 * @param g
	 */
	private void paintOneFrame(Graphics g){
		if(intro==true){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			g.setColor(Color.GRAY);
			g.drawLine(0, 0, 0, getHeight());

			g.drawImage(SEGA, 370, 280, 600, 100, frame);
			g.drawString("[PRESS ANY KEY]",600, 500);
		} else {
			if(menu.isVisible()){
				menu.draw(g, this);
			} else {
				if(arena!=null){
					arena.draw(g,menu.stageNumber(), this);
				}
				if(sonic!=null){
					sonic.draw(g, this);
					if(sonic.health()<5){
						g.drawImage(knucklesWins, 370, 280, 600, 100, frame);
					}
				}
				if(knuckles!=null){
					knuckles.draw(g,this);
					if(knuckles.health()<5){
						g.drawImage(sonicWins, 370, 280, 600, 100, frame);
					}
				}
			}
		}
	}

	/**
	 * This method is the layout setup that determines the size (based on width and height) of the window.
	 * @param theTitle The name of the window
	 * @param theWidth the width of the window
	 * @param theHeight the height of the window
	 */
	public void layoutSetup(String theTitle, int theWidth, int theHeight){
		frame = new JFrame(theTitle){
			private static final long serialVersionUID = 1L;
			public void paint(Graphics g) {
				paintComponents(g);
			}
		};
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // not resizesable.
		frame.setSize(theWidth, theHeight);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
	}

	/**
	 * Allows the multi-action events based on the mouse click
	 * i.e. Let's menu be browsed based on mouse click to 
	 * the custom-made buttons and stages of the game.
	 * @param e Mouse event selection.
	 *  */
	public void mouseClicked(MouseEvent e) {
//		System.out.println("x: "+e.getX()+" y: "+e.getY());
		menu.mouseClicked(e);
		if(menu.stageChosen()){
			stopMenuMusic();
			if(menu.stageNumber()==0&&!fightingMusicPlaying){
				arena.loadArenaMusic0();
				fightingMusicPlaying=true;
			} else if(menu.stageNumber()==1&&!fightingMusicPlaying){
				arena.loadArenaMusic1();
				fightingMusicPlaying=true;
			} else if(menu.stageNumber()==2&&!fightingMusicPlaying){
				arena.loadArenaMusic2();
				fightingMusicPlaying=true;
			} else if(menu.stageNumber()==3&&!fightingMusicPlaying){
				arena.loadArenaMusic3();
				fightingMusicPlaying=true;
			}
			menu.setInvisible();
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * Detects where the mouse is on the window frame
	 * @param e Position  
	 *  */
	public void mouseMoved(MouseEvent e) {
		menu.mouseMoved(e);		
		repaint();
	}

	/**
	 * Detects keyboard's keys pressed by user
	 * @param e Pressed Key 
	 *  */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key==KeyEvent.VK_BACK_SPACE){
			reset();
		}

		//		System.out.println(e.getKeyCode());
		intro=false;
		if(!menu.isVisible()){
			knuckles.keyPressed(e);
			sonic.keyPressed(e);
			// Checking for proximity #
			if(knuckles.hitting()&&proximityCheck()){
				// Checking for facing direction 
				if(knuckles.facingRight()){
					if(knuckles.getX()-sonic.getX()<=0){
						sonic.hitFromLeft();
					}
				} else if(!knuckles.facingRight()) {
					if(knuckles.getX()-sonic.getX()>=0){
						sonic.hitFromRight();
					}
				}
			}// #
			if(sonic.hitting()&&proximityCheck()){
				if(sonic.facingRight()){
					if(sonic.getX()-knuckles.getX()<=0){
						knuckles.hitFromLeft();
					}
				} else if(!sonic.facingRight()) {
					if(sonic.getX()-knuckles.getX()>=0){
						knuckles.hitFromRight();
					}
				}
			}
			if(sonic.isDead()){
				repaint();				
				try {
					th.sleep(3000);
					reset();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (knuckles.isDead()){
				repaint();
				try {
					th.sleep(3000);
					reset();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		repaint();
	}

	/**
	 * Detects keyboard's keys released by user
	 * @param e Released Key 
	 *  */
	public void keyReleased(KeyEvent e) {
		sonic.keyReleased(e);
		knuckles.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Loads and play menu music
	 */
	public void loadMenuMusic(){
		if(menu.isVisible()){
			try{
				url = new URL ("file", "localhost", "resources/Menu.wav");
				backgroundMusic = JApplet.newAudioClip(url);
				backgroundMusic.loop();
			}catch(Exception e){
				//	do something in case of problem
			}
		}
	}
	/**
	 * Stops menu music
	 */
	public void stopMenuMusic(){
		backgroundMusic.stop();
	}
	/**
	 * Determines when the game characters are near each other.
	 * @return boolean
	 */
	public boolean proximityCheck(){
		if(Math.abs(sonic.getX()-knuckles.getX())<=60
				&&sonic.getY()==knuckles.getY()){
			return true;
		} else 
			return false;
	}
	/**
	 * Set the original settings of the game.
	 */
	public void reset(){
		sonic.replenishLife();
		knuckles.replenishLife();
		menu.setVisible();
		menu.setStageState(false);
		arena.stopArenaMusic();
		fightingMusicPlaying=false;
		loadMenuMusic();
	}
} // End Of Class