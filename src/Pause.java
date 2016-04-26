import java.awt.event.MouseEvent;
import acm.graphics.*;

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
	public Pause(Main app, Game game) {
		program = app;
		this.game = game;
		instructions  = new Instructions(program);
		
		titleIMG = new GImage("SpriteSheets/button (1).png");
        titleIMG.setSize(400, 75);
        titleIMG.setLocation(Main.WINDOW_WIDTH/2-WIDTH,20);
		//pausepic = new GImage("SpriteSheets/pausepic.png");
		//pausepic.setLocation(25,50);
		int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
        backtogamepic = new GImage("SpriteSheets/backtogamepic.png");
        backtogamepic.setSize(WIDTH, HEIGHT);
        backtogamepic.setLocation(xPos, y);
        y += OFFSET;


        instructionspic = new GImage("SpriteSheets/instructionspic.png");
        instructionspic.setSize(WIDTH, HEIGHT);
        instructionspic.setLocation(xPos, y);
        y += OFFSET;

        mainmenupic = new GImage("SpriteSheets/mainmenupic.png");
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
		initBackground();
		program.add(backtogamepic);
		program.add(instructionspic);
		program.add(mainmenupic);
		program.add(titleIMG);

	}

	@Override
	public void hideContents() {
		program.remove(backtogamepic);
		program.remove(instructionspic);
		program.remove(mainmenupic);
		program.remove(titleIMG);
		program.remove(background);
	}
    public void mousePressed(MouseEvent e) {
    	if(program.getElementAt(e.getX(), e.getY()) == backtogamepic){
    		Sound.clicking.play();
            program.switchToScreen(game);
        }
        	else if(program.getElementAt(e.getX(), e.getY()) == instructionspic){
        	Sound.clicking.play();
        	program.switchToScreen(instructions);
        	
        	}
        else if(program.getElementAt(e.getX(), e.getY()) == mainmenupic){
        	Sound.clicking.play();
        	Sound.fighting.stop();
            program.switchToScreen(new MainMenu(program));
            Sound.menuMusic.play();
        }
        
    }

}
