import java.util.ArrayList;
import java.util.TimerTask;

import acm.graphics.GObject;

import java.util.Timer;
public class Main extends GraphicsApplication{

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;
    public static final int REFRESH_RATE = 20; // In milliseconds

    ArrayList<Display> displays = new ArrayList<Display>();

    public void run(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsPane p = new Game(this);
        switchToScreen(p);
		addKeyListeners();
		addMouseListeners();
        update();
    }

    public void addDisplay(Display d, int x, int y) {
        displays.add(d);
        d.setLocation(x,y);
    }

    public void removeDisplay(Display d) {
        for(GObject o: d.getObjects()) remove(o);
        displays.remove(d);
    }

    public void update() {
        new Timer().schedule(new TimerTask(){
            public void run(){
                update();
        }} ,REFRESH_RATE);
        for(Display d: displays) {
            d.update();
            d.showContents();
        }
    }
}
//Hello
