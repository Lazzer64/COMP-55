import java.awt.event.MouseEvent;

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Pause extends GraphicsPane {
private Main program;
private GImage pausepic;
private GImage background;
private GImage backtogamepic;
private GImage instructionspic;
private GImage mainmenupic;
private GImage titleIMG;

public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;
public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;

private Game game;
private Instructions instructions;
private MainMenu mainmenu;
	public Pause(Main app, Game game) {
		// TODO Auto-generated constructor stub
		program = app;
		this.game = game;
		instructions  = new Instructions(program);
		mainmenu = new MainMenu(program);
		
		titleIMG = new GImage("SpriteSheets/button (1).png");
        titleIMG.setSize(400, 75);
        titleIMG.setLocation(Main.WINDOW_WIDTH/2-WIDTH,20);
		//pausepic = new GImage("SpriteSheets/pausepic.png");
		//pausepic.setLocation(25,50);
		int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
        backtogamepic = new GImage("SpriteSheets/playpic.png");
        backtogamepic.setSize(WIDTH, HEIGHT);
        backtogamepic.setLocation(xPos, y);
        y += OFFSET;


        instructionspic = new GImage("SpriteSheets/instructionspic.png");
        instructionspic.setSize(WIDTH, HEIGHT);
        instructionspic.setLocation(xPos, y);
        y += OFFSET;

        mainmenupic = new GImage("SpriteSheets/quitpic.png");
        mainmenupic.setSize(WIDTH, HEIGHT);
        mainmenupic.setLocation(xPos, y);
		
	}
	  public void initBackground() {
	        //initialize the main menu background and set size
	        background = new GImage("SpriteSheets/background2.jpg");
	        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
	        program.add(background);
	    }

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		initBackground();
		//program.add(pausepic);
		program.add(backtogamepic);
		program.add(instructionspic);
		program.add(mainmenupic);
		program.add(titleIMG);

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		//program.remove(pausepic);
		program.remove(backtogamepic);
		program.remove(instructionspic);
		program.remove(mainmenupic);
		program.remove(titleIMG);
		program.remove(background);
	}
    public void mousePressed(MouseEvent e) {
        // TODO implement
    	if(program.getElementAt(e.getX(), e.getY()) == backtogamepic){
            program.switchToScreen(game);
        }
        	else if(program.getElementAt(e.getX(), e.getY()) == instructionspic){
        	program.switchToScreen(instructions);
        	}
        else if(program.getElementAt(e.getX(), e.getY()) == mainmenupic){
            program.switchToScreen(mainmenu);
        }
        
    }

}
