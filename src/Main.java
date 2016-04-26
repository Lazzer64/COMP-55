import acm.graphics.GLabel;

public class Main extends GraphicsApplication{

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;

    public void run(){
    	
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        switchToScreen(new GraphicsPane(){
            GLabel label = new GLabel("Loading...",WINDOW_WIDTH/2,WINDOW_HEIGHT/2);
            public void showContents(){add(label);}
            public void hideContents(){remove(label);}

        });

        GraphicsPane p = new MainMenu(this);
        switchToScreen(p);
		addKeyListeners();
		addMouseListeners();
      
    }
}
