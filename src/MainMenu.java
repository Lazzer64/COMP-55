import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GRect;

import java.awt.event.KeyEvent; 

public class MainMenu extends GraphicsPane {
private Main program;
private GRect rect;
	public  MainMenu(Main app) {
		program = app;
		rect = new GRect(Main.WINDOW_WIDTH/2-25, Main.WINDOW_HEIGHT/2-75/2, 50, 75);
		rect.setFilled(true);
	}
	 public void initBackground() {
		 	//initialize the main menu background and set size
	    	GImage background = new GImage("SpriteSheets/mainmenuBackground.gif");
	    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
	    }
	 
    public void showContents() {
    	 initBackground();
    program.add(rect);
    }
    
    public void hideContents() {
        // TODO implement
    }
    
    public void mousePressed(MouseEvent e) {
        // TODO implement
    }

    public void mouseReleased(MouseEvent e) {
        // TODO implement
    }
    
    public void mouseClicked() {
        // TODO implement
    }
    
    public void mouseMoved() {
        // TODO implement
    }
    
    public void mouseDragged() {
        // TODO implement
    }
    
    public void keyPressed(KeyEvent e) {
        // TODO implement
    }
    
    public void keyReleased(KeyEvent e) {
        // TODO implement
    }
    
    public void keyTyped(KeyEvent e) {
        // TODO implement
    }
    

}
