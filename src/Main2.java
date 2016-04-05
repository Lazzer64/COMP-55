import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main2 extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	public static final int BREAK_MS = 300;
	
	// Images for each animation
	private BufferedImage[] walkingLeft = {Sprite.getSprite(0, 1), Sprite.getSprite(2, 1)}; // Gets the upper left images of my sprite sheet
	private BufferedImage[] walkingRight = {Sprite.getSprite(0, 2), Sprite.getSprite(2, 2)};
	private BufferedImage[] standing = {Sprite.getSprite(1, 0)};

	// These are animation states
	private Animation walkLeft = new Animation(walkingLeft, 0);
	private Animation walkRight = new Animation(walkingRight, 0);
	private Animation stand = new Animation(standing, 0);

	// This is the actual animation
	private Animation animation = walkRight;
	
	public void run() {
		add(animation);
		addMouseListeners();
		while(true) {
			animation.update();
			repaint();
			pause(BREAK_MS);
		}
	}
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		remove(animation);
	    animation = walkLeft;
	    add(animation);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		remove(animation);
	    animation = walkRight;
	    add(animation);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		remove(animation);
	    animation = stand;
	    add(animation);
	}
}