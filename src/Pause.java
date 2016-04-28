import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import acm.graphics.*;

public class Pause extends GraphicsPane {
private Main program;
private GImage background;
private GImage backtogamepic;
private GImage instructionspic;
private GImage mainmenupic;
private GImage titleIMG;

public static final GImage TITLE_IMAGE = new GImage("SpriteSheets/pausepic.png");
public static final GImage BACK_IMAGE = new GImage("SpriteSheets/backtogamepic.png");
public static final GImage INSTRUCTION_IMAGE = new GImage("SpriteSheets/instructionspic.png");
public static final GImage MENU_IMAGE = new GImage("SpriteSheets/mainmenupic.png");
public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/background2.jpg");

public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;
public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;

private Game game;

private InstructionsPause instructionspause;


	public Pause(Main app, Game game) {
		program = app;
		this.game = game;

		instructionspause  = new InstructionsPause(program, this);
		

		
		titleIMG = TITLE_IMAGE;
        titleIMG.setLocation(100,50);
	
		int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
        backtogamepic = BACK_IMAGE;
        backtogamepic.setSize(WIDTH, HEIGHT);
        backtogamepic.setLocation(xPos, y);
        y += OFFSET;


        instructionspic = INSTRUCTION_IMAGE;
        instructionspic.setSize(WIDTH, HEIGHT);
        instructionspic.setLocation(xPos, y);
        y += OFFSET;

        mainmenupic = MENU_IMAGE;
        mainmenupic.setSize(WIDTH, HEIGHT);
        mainmenupic.setLocation(xPos, y);
		
	}
	  public void initBackground() {
	        //initialize the main menu background and set size
	        background = BACKGROUND_IMAGE;
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
        	program.switchToScreen(instructionspause);
        	
        	}
        else if(program.getElementAt(e.getX(), e.getY()) == mainmenupic){
        	Sound.clicking.play();
        	Sound.fighting.stop();
            program.switchToScreen(new MainMenu(program));
            Sound.menuMusic.play();
        }
        
    }
    @Override
    public void keyReleased(KeyEvent ke) 
    {
 

        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            //code to execute if escape is pressed
        	program.switchToScreen(game);
        }
    }

}
