import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.KeyEvent;
import acm.graphics.*;
public class Credits extends GraphicsPane {
	private Main program;
	public static final GImage RETURN_IMAGE = new GImage("SpriteSheets/returnpic.png");
	public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/creditBack.jpg");
	public static final GRect rectangle = new GRect(160, 180, 100, 100);
	public static final GImage CREDITS_IMAGE = new GImage("SpriteSheets/credits.png");




	protected GImage returnpic;
	protected GImage background;
	protected GLabel dontdoit;
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
        
        rectangle.setColor(Color.GRAY);
        rectangle.setFilled(false);
        rectangle.setVisible(false);
        
        dontdoit = new GLabel("(Don't click the head)", 150, 580);
        dontdoit.setColor(Color.WHITE);
        dontdoit.setFont("Arial-11");

        creditsImage = CREDITS_IMAGE;
        creditsImage.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT-80);
        creditsImage.setLocation(Main.WINDOW_WIDTH/2-WIDTH,22.5);



        
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
    	program.add(rectangle);
    	program.add(dontdoit);

	}

	@Override
	public void hideContents() {
		program.remove(returnpic);
    	program.remove(background);
     	program.remove(creditsImage);
    	program.remove(rectangle);
    	program.remove(dontdoit);

	}
	 public void mousePressed(MouseEvent e) {
	        if(returnpic.contains(e.getX(), e.getY())){
	            program.switchToScreen(new MainMenu(program));
	            Sound.clicking.play();
	            Sound.cena.stop();

	        }
	        else if(program.getElementAt(e.getX(), e.getY()) == rectangle){
	        	//rectangle.setVisible(true);
	        	Sound.clicking.play();
	         	Sound.creditMusic.stop();
	        	Sound.cena.loop();
	       

	        	
	        }

	    }

}
