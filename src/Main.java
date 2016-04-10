public class Main extends GraphicsApplication{

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;
    public static final int REFRESH_RATE = 20; // In milliseconds

    public void run(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsPane p = new Game(this);
        switchToScreen(p);
		addKeyListeners();
		addMouseListeners();
    }
}
//Hello
