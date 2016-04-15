import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent; 

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent
;
public class Instructions extends GraphicsPane {

    private Main program;
    private GRect returnbtn;
    private GLabel returnlab;
    private GLabel instructions;
    
    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;
    public static final int OFFSET = 75;
    
    public Instructions(Main app){
    	program =app;
    	instructions = new GLabel("INSTRUCTIONS", xPos, 100);
    	instructions.setColor(Color.BLUE);
    	instructions.setFont("Arial-30");
    	returnbtn = new GRect(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6, WIDTH, HEIGHT);
		returnbtn.setFilled(false);
		returnlab = new GLabel("<= RETURN", Main.WINDOW_WIDTH/2-WIDTH/4, OFFSET*6+75/2);
		returnlab.setColor(Color.RED);
		returnlab.setFont("Arial-20");
    }
	public void showContents() {
        // TODO implement
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
    
    public void mouseClicked(MouseEvent e) {
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
