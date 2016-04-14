import java.awt.event.MouseEvent;

import acm.graphics.GImage;

import java.awt.event.KeyEvent; 

public class MainMenu extends GraphicsPane {

	 public void initBackground() {
		 	//initialize the main menu background and set size
	    	GImage background = new GImage("SpriteSheets/mainmenuBackground.jpg");
	    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
	    }
	 
    public void showContents() {
    	 initBackground();
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
