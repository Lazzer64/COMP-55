public class Main extends GraphicsApplication{

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;

    public void run(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        switchToScreen(new Game(this));
		addKeyListeners();
		addMouseListeners();
    }
        
}
//Hello
