import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JApplet;

public class Arena {

	private int width, height;
	private AudioClip arenaMusic, am1, am2, am3;
	private URL url1;
	private Image scArena = Toolkit.getDefaultToolkit().getImage("sc_arena.jpg");
	private Image mkArena = Toolkit.getDefaultToolkit().getImage("mk_arena.jpg");
	private Image beachArena = Toolkit.getDefaultToolkit().getImage("b_arena.jpg"); 
	private Image arena = Toolkit.getDefaultToolkit().getImage("Arena.gif");

	/**
	 * Constructor sets dimension of the arena
	 * @param width Width of the arena
	 * @param height Height of the arena
	 */
	public Arena(int width, int height){
		this.width=width;
		this.height=height;
	}
	/**
	 * Paints selected arena
	 * @param g
	 * @param selection
	 * @param frame
	 */
	public void draw(Graphics g, int selection, Main frame){
		if(selection==0){
			g.drawImage(mkArena, 0, 0, width, height, frame);
		} else if(selection==1){
			g.drawImage(scArena, 0, 0, width, height, frame);
		} else if(selection==2){
			g.drawImage(beachArena, 0, 0, width, height, frame);
		} else if(selection==3){
			g.drawImage(arena, 0, 0, width, height, frame);
		}
	}
	/**
	 * Loads and play arena music #1
	 */
	public void loadArenaMusic0(){
		try {
			url1 = new URL ("file", "localhost", "mkArenaMusic.wav");
			arenaMusic = JApplet.newAudioClip(url1);
			arenaMusic.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Loads and play arena music #2
	 */
	public void loadArenaMusic1(){
		try {
			url1 = new URL ("file", "localhost", "scArenaMusic.wav");
			arenaMusic = JApplet.newAudioClip(url1);
			arenaMusic.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Loads and play arena music #3
	 */
	public void loadArenaMusic2(){
		try {
			url1 = new URL ("file", "localhost", "beachArenaMusic.wav");
			arenaMusic = JApplet.newAudioClip(url1);
			arenaMusic.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Loads and play arena music #4
	 */
	public void loadArenaMusic3(){
		try {
			url1 = new URL ("file", "localhost", "arenaMusic1.wav");
			arenaMusic = JApplet.newAudioClip(url1);
			arenaMusic.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Halts any currently playing.
	 */
	public void stopArenaMusic(){
		arenaMusic.stop();
	}
}