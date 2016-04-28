import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import acm.graphics.*;
public class Credits extends GraphicsPane {
	private Main program;
	public static final GImage RETURN_IMAGE = new GImage("SpriteSheets/returnpic.png");
	public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/creditBack.jpg");
	public static final GImage CREDITS_IMAGE = new GImage("SpriteSheets/Credits.png");

	protected GImage returnpic;
	protected GImage background;
	protected GImage creditsImage;
	
	  public static final int WIDTH = 200;
	    public static final int HEIGHT = 50;
	    public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;
	    public static final int OFFSET = 75;
	    
	    
	public Credits(Main app) {
		program = app;
     
		
		
		returnpic = RETURN_IMAGE;
        returnpic.setSize(WIDTH, HEIGHT);
        returnpic.setLocation(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6.9);
        
        creditsImage = CREDITS_IMAGE;
        creditsImage.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT-80);
        creditsImage.setLocation(Main.WINDOW_WIDTH/2-WIDTH,0);

	}
	 public void initBackground() {
	        
	        background = BACKGROUND_IMAGE;
	        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
	        program.add(background);
	    }
	@Override
	public void showContents() {
		initBackground();
    	program.add(returnpic);
    	program.add(creditsImage);

	}

	@Override
	public void hideContents() {
		program.remove(returnpic);
    	program.remove(background);
    	program.remove(creditsImage);

	}
	 public void mouseClicked(MouseEvent e) {
	        if(program.getElementAt(e.getX(), e.getY()) == returnpic){
	            program.switchToScreen(new MainMenu(program));
	            Sound.clicking.play();
	            Sound.creditMusic.stop();
	        }

	    }

}
