import java.awt.event.MouseEvent;

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent; 

public class MainMenu extends GraphicsPane {
private Main program;
private GRect playbtn;
private GRect instructionsbtn;
private GRect highscoresbtn;
private GRect quitbtn;
private GLabel play;
private GLabel instructions;
private GLabel highscores;
private GLabel quit;
public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;

	public  MainMenu(Main app) {
		program = app;
		int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
		playbtn = new GRect(Main.WINDOW_WIDTH/2-WIDTH/2, y, WIDTH, HEIGHT);
		playbtn.setFilled(false);
		play = new GLabel("PLAY", Main.WINDOW_WIDTH/2-WIDTH/4, y);
		play.setColor(Color.red);
		play.setFont("Arial-20");
		play.move(0,play.getHeight());
		y += OFFSET;
		
		instructionsbtn = new GRect(Main.WINDOW_WIDTH/2-WIDTH/2, y, WIDTH, HEIGHT);
		instructionsbtn.setFilled(false);
		instructions = new GLabel("INSTRUCTIONS", Main.WINDOW_WIDTH/2-WIDTH/4, y);
		instructions.setColor(Color.red);
		instructions.setFont("Arial-20");
		instructions.move(0,play.getHeight());
		y += OFFSET;
		
		highscoresbtn = new GRect(Main.WINDOW_WIDTH/2-WIDTH/2, y, WIDTH, HEIGHT);
		highscoresbtn.setFilled(false);
		highscores = new GLabel("HIGH SCORES", Main.WINDOW_WIDTH/2-WIDTH/4, y);
		highscores.setColor(Color.red);
		highscores.setFont("Arial-20");
		highscores.move(0,play.getHeight());
		y += OFFSET;
		
		quitbtn = new GRect(Main.WINDOW_WIDTH/2-WIDTH/2, y, WIDTH, HEIGHT);
		quitbtn.setFilled(false);
		quit = new GLabel("QUIT", Main.WINDOW_WIDTH/2-WIDTH/4, y);
		quit.setColor(Color.red);
		quit.setFont("Arial-20");
		quit.move(0,play.getHeight());
	}
	 public void initBackground() {
		 	//initialize the main menu background and set size
	    	//GImage background = new GImage("SpriteSheets/mainmenuBackground.jpg");
	    	//background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
	    }
	 
    public void showContents() {
    	 initBackground();
    program.add(playbtn);
    program.add(instructionsbtn);
    program.add(highscoresbtn);
    program.add(quitbtn);
    program.add(play);
    program.add(instructions);
    program.add(highscores);
    program.add(quit);
    }
    
    public void hideContents() {
        // TODO implement
    	    program.remove(playbtn);
    	    program.remove(instructionsbtn);
    	    program.remove(highscoresbtn);
    	    program.remove(quitbtn);
    	    program.remove(play);
    	    program.remove(instructions);
    	    program.remove(highscores);
    	    program.remove(quit);
    }
    
    public void mousePressed(MouseEvent e) {
        // TODO implement
    }

    public void mouseReleased(MouseEvent e) {
        // TODO implement
    }
    
    public void mouseClicked(MouseEvent e) {
        // TODO implement
    	if(program.getElementAt(e.getX(), e.getY()) == playbtn){
    		program.switchToScreen(new Game(program));
    	}
    //	else if(program.getElementAt(e.getX(), e.getY()) == instructionsbtn){
    //		program.switchToScreen(new Instructions(program));
    //	}
    //	else if(program.getElementAt(e.getX(), e.getY()) == highscoresbtn){
    //		program.switchToScreen(new ScoreScreen(program));
//}
    	else if(program.getElementAt(e.getX(), e.getY()) == quitbtn){
	System.exit(0);
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
