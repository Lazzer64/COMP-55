import java.awt.event.MouseEvent;
import acm.graphics.*;
import java.awt.Color;

public class MainMenu extends GraphicsPane {


    public static final GImage TITLE_IMAGE = new GImage("SpriteSheets/pumpingpowertitle.png");
    public static final GImage PLAY_IMAGE = new GImage("SpriteSheets/playpic.png");
    public static final GImage INSTRUCTION_IMAGE = new GImage("SpriteSheets/instructionspic.png");
    public static final GImage HIGHSCORE_IMAGE = new GImage("SpriteSheets/highscorespic.png");
    public static final GImage QUIT_IMAGE = new GImage("SpriteSheets/quitpic.png");
    public static final GImage CREDITS_IMAGE = new GImage("SpriteSheets/quitpic.png");
    private Main program;
    private GImage pumpingpower;
    private GImage background;
    
    private GImage playpic;
    private GImage instructionspic;
    private GImage highscorespic;
    private GImage quitpic;
    private GLabel copyright;
    private GImage creditpic;

    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    public static final int OFFSET = 75;
    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;

    private Game game;
    private ScoreScreen scores;
    private Instructions instructions;
    private Credits credits;

    public  MainMenu(Main app) {
        program = app;
        game = new Game(app);
        scores = new ScoreScreen(program);
        instructions = new Instructions(program);
        credits = new Credits(program);
        
        //Sounds
        Sound.menuMusic.loop();
        Sound.ending.stop(); //if return to main menu from high scores list at end game
        
        copyright = new GLabel("© 2k16. Some rights reserved. Created by: Alex P, Tom V, Mark B, Michael M.", 7, 580);
        copyright.setColor(Color.WHITE);
        copyright.setFont("Arial-11");
        
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
        
        y += OFFSET;

        creditpic = CREDITS_IMAGE;
        creditpic.setSize(WIDTH, HEIGHT);
        creditpic.setLocation(xPos, y);
        
        
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
        program.add(copyright);
        program.add(playpic);
        program.add(instructionspic);
        program.add(highscorespic);
        program.add(quitpic);
        program.add(creditpic);
    }

    public void hideContents() {
        program.remove(pumpingpower);
        program.remove(playpic);
        program.remove(instructionspic);
        program.remove(highscorespic);
        program.remove(quitpic);
        program.remove(background);
        program.remove(copyright);
        program.remove(creditpic);
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
            else if(program.getElementAt(e.getX(), e.getY()) == creditpic){
                Sound.clicking.play();
                program.switchToScreen(credits);
        }
    }

}
