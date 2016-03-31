public class Main extends GraphicsApplication{

    public void run(){
        switchToScreen(new Game(this));
		addKeyListeners();
		addMouseListeners();
    }
    
}
//Hello - Michael