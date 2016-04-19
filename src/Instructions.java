import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent; 

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent
;
public class Instructions extends GraphicsPane {

    private Main program;

    private GLabel instructions;
    private GImage background;
    private GImage returnpic;
    
    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;
    public static final int OFFSET = 75;
    
    public Instructions(Main app){
    	program =app;
    	instructions = new GLabel("INSTRUCTIONS", xPos, 100);
    	instructions.setColor(Color.WHITE);
    	instructions.setFont("Arial-30");

		returnpic = new GImage("SpriteSheets/returnpic.png");
        returnpic.setSize(WIDTH, HEIGHT);
        returnpic.setLocation(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6);
    }
    public void initBackground() {
        
        background = new GImage("SpriteSheets/mainmenuBackground.jpg");
        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
        program.add(background);
    }
    public void showContents() {
        // TODO implement
    	initBackground();
    	program.add(instructions);
    	program.add(returnpic);
    }
    
    public void hideContents() {
        // TODO implement
    	program.remove(instructions);
    	program.remove(returnpic);
    	program.remove(background);
    }
    
    public void mousePressed(MouseEvent e) {
        // TODO implement
    }

    public void mouseReleased(MouseEvent e) {
        // TODO implement
    }
    
    public void mouseClicked(MouseEvent e) {
        // TODO implement
   	 if(program.getElementAt(e.getX(), e.getY()) == returnpic){
  		program.switchToScreen(new MainMenu(program));
  	}
  
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
