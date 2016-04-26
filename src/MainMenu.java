import java.awt.event.MouseEvent;

import acm.graphics.*;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;


public class MainMenu extends GraphicsPane {


    public static final GImage TITLE_IMAGE = new GImage("SpriteSheets/pumpingpowertitle.png");
    public static final GImage PLAY_IMAGE = new GImage("SpriteSheets/playpic.png");
    public static final GImage INSTRUCTION_IMAGE = new GImage("SpriteSheets/instructionspic.png");
    public static final GImage HIGHSCORE_IMAGE = new GImage("SpriteSheets/highscorespic.png");
    public static final GImage QUIT_IMAGE = new GImage("SpriteSheets/quitpic.png");

    private Main program;
    //private GLabel pumpingpower;
    private GImage pumpingpower;
    private GImage background;
    private GImage playpic;
    private GImage instructionspic;
    private GImage highscorespic;
    private GImage quitpic;

    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    public static final int OFFSET = 75;
    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;

    private Game game;
    private ScoreScreen scores;
    private Instructions instructions;


    public  MainMenu(Main app) {
        program = app;
        game = new Game(app);
        scores = new ScoreScreen(program);
        instructions = new Instructions(program);
        
    	
    	Sound.menuMusic.loop();

        /*pumpingpower = new GLabel("PUMPING POWER", xPos, 100);
        pumpingpower.setColor(Color.WHITE);
        pumpingpower.setFont("Arial-30");*/
        
        pumpingpower = TITLE_IMAGE;
        pumpingpower.setLocation(25, 50);
        
        int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
        playpic = PLAY_IMAGE;
        playpic.setSize(WIDTH, HEIGHT);
        playpic.setLocation(xPos, y);
        y += OFFSET;


        instructionspic = INSTRUCTION_IMAGE;
        instructionspic.setSize(WIDTH, HEIGHT);
        instructionspic.setLocation(xPos, y);
        y += OFFSET;

        highscorespic = HIGHSCORE_IMAGE;
        highscorespic.setSize(WIDTH, HEIGHT);
        highscorespic.setLocation(xPos, y);
        
        y += OFFSET;

        quitpic = QUIT_IMAGE;
        quitpic.setSize(WIDTH, HEIGHT);
        quitpic.setLocation(xPos, y);
    }
    public void initBackground() {
        //initialize the main menu background and set size
        background = new GImage("SpriteSheets/background2.jpg");
        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
        program.add(background);
    }

    public void showContents() {
        initBackground();
        program.add(pumpingpower);
        program.add(playpic);
        program.add(instructionspic);
        program.add(highscorespic);
        program.add(quitpic);
    }

    public void hideContents() {
        program.remove(pumpingpower);
        program.remove(playpic);
        program.remove(instructionspic);
        program.remove(highscorespic);
        program.remove(quitpic);
        program.remove(background);
    }

    public void mousePressed(MouseEvent e) {
    	if(program.getElementAt(e.getX(), e.getY()) == playpic){
    		Sound.clicking.play();
        	Sound.menuMusic.stop();
            program.switchToScreen(game);
        	Sound.fighting.loop();
        }
        	else if(program.getElementAt(e.getX(), e.getY()) == instructionspic){
        	  	Sound.clicking.play();
        	program.switchToScreen(instructions);
      
        	}
        else if(program.getElementAt(e.getX(), e.getY()) == highscorespic){
            Sound.clicking.play();
            program.switchToScreen(scores);
        
        }
        else if(program.getElementAt(e.getX(), e.getY()) == quitpic){
        	Sound.clicking.play();
            System.exit(0);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // TODO implement
    	 Toolkit.getDefaultToolkit().beep();
        
    }

}
