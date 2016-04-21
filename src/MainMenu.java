import java.awt.event.MouseEvent;

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent; 

public class MainMenu extends GraphicsPane {
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

        /*pumpingpower = new GLabel("PUMPING POWER", xPos, 100);
        pumpingpower.setColor(Color.WHITE);
        pumpingpower.setFont("Arial-30");*/
        
        pumpingpower = new GImage("SpriteSheets/pumpingpowertitle.png");
        pumpingpower.setLocation(25, 50);
        
        int y = Main.WINDOW_HEIGHT/4-HEIGHT/2;
        playpic = new GImage("SpriteSheets/playpic.png");
        playpic.setSize(WIDTH, HEIGHT);
        playpic.setLocation(xPos, y);
        y += OFFSET;


        instructionspic = new GImage("SpriteSheets/instructionspic.png");
        instructionspic.setSize(WIDTH, HEIGHT);
        instructionspic.setLocation(xPos, y);
        y += OFFSET;

        highscorespic = new GImage("SpriteSheets/highscorespic.png");
        highscorespic.setSize(WIDTH, HEIGHT);
        highscorespic.setLocation(xPos, y);
        
        y += OFFSET;


        quitpic = new GImage("SpriteSheets/quitpic.png");
        quitpic.setSize(WIDTH, HEIGHT);
        quitpic.setLocation(xPos, y);
    }
    public void initBackground() {
        //initialize the main menu background and set size
        background = new GImage("SpriteSheets/mainmenuBackground.jpg");
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
        // TODO implement
        program.remove(pumpingpower);
        program.remove(playpic);
        program.remove(instructionspic);
        program.remove(highscorespic);
        program.remove(quitpic);
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
        if(program.getElementAt(e.getX(), e.getY()) == playpic){
            program.switchToScreen(game);
        }
        	else if(program.getElementAt(e.getX(), e.getY()) == instructionspic){
        	program.switchToScreen(instructions);
        	}
        else if(program.getElementAt(e.getX(), e.getY()) == highscorespic){
            program.switchToScreen(scores);
        }
        else if(program.getElementAt(e.getX(), e.getY()) == quitpic){
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
