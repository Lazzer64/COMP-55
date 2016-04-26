import java.awt.event.MouseEvent;

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Pause extends GraphicsPane {
private Main program;
private GImage pausepic;
private GImage background;
private GImage backtogamepic;
private GImage instructionspic;
private GImage mainmenupic;

public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;
public static final int xPos = Main.WINDOW_WIDTH/2-WIDTH/2;

private Game game;
private Instructions instructions;
private MainMenu mainmenu;
	public Pause(Main app) {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub

	}

}
