import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import acm.graphics.*; 
import java.awt.Color;

public class Instructions extends GraphicsPane {

    private Main program;

    public static final GImage RETURN_IMAGE = new GImage("SpriteSheets/returnpic.png");
    //public static final GImage TITLE_IMAGE = new GImage("SpriteSheets/button (1).png");
    public static final GImage INSTRUCTION_IMAGE = new GImage("SpriteSheets/Pumping Power.png");
    public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/instructionsBack.png");


   // private GLabel instructions;
    private GImage background;
    private GImage returnpic;
    private GImage titleIMG;
    private GImage instructions;
    private Board board = new Board(4,4);
    private BoardDisplay boardDisplay = new BoardDisplay(board); 
    
    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;
    public static final int OFFSET = 75;

    Tile start;
    boolean canMove = true;
    
    
    public Instructions(Main app){
    	program = app;

        GLabel tryMe = new GLabel("Try Me!");
        tryMe.setColor(Color.WHITE);
        boardDisplay.add(tryMe);
        boardDisplay.setLocation(300, 10);
        boardDisplay.resize(.3);
        boardDisplay.update();

		returnpic = RETURN_IMAGE;
        returnpic.setSize(WIDTH, HEIGHT);
        returnpic.setLocation(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6.9);
        
        /*titleIMG = TITLE_IMAGE;
        titleIMG.setSize(400, 75);
        titleIMG.setLocation(Main.WINDOW_WIDTH/2-WIDTH,70);*/
        
        instructions = INSTRUCTION_IMAGE;
        instructions.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT-80);
        instructions.setLocation(Main.WINDOW_WIDTH/2-WIDTH,0);
    }
    public void initBackground() {
        
        background = BACKGROUND_IMAGE;
        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
        program.add(background);
    }
    public void showContents() {
    	initBackground();
    	program.add(returnpic);
    	program.add(instructions);
        program.add(boardDisplay);

    }
    
    public void hideContents() {
    	program.remove(instructions);
    	program.remove(returnpic);
    	program.remove(background);
    	program.remove(titleIMG);
    	program.remove(boardDisplay);

    }

    public void mousePressed(MouseEvent e) {
        start = boardDisplay.getTileAt(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        boardStep();
        start = null;
    }

    private void boardStep(){
        new Timer().schedule( new TimerTask(){
            public void run(){
                if(board.step()){
                    boardStep();
                    boardDisplay.update();
                    canMove = false;
                } else canMove = true;
            }}, Game.TILE_MOVE_DELAY);
    }

    public void mouseDragged(MouseEvent e) {
        if(start != null && canMove && boardDisplay.isInBoard(e.getX(), e.getY())){
            Tile end = boardDisplay.getTileAt(e.getX(), e.getY());
            board.moveTile(start.getPosition(), end.getPosition());
            boardDisplay.update();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if(program.getElementAt(e.getX(), e.getY()) == returnpic){
            program.switchToScreen(new MainMenu(program));
            Sound.clicking.play();
        }

    }

}
