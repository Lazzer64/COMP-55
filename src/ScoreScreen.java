import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent; 


//public static final int WINDOW_WIDTH = 400;
//public static final int WINDOW_HEIGHT = 600;

public class ScoreScreen extends GraphicsPane {
private Main program;
private GRect rect;
private GRect backbtn;
private GLabel back;

public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;

HighscoreList show = new HighscoreList();
	
	public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 10);
	
	GLabel titleLabel = new GLabel("");
	Score score;
	
	public ScoreScreen(Main app) {
		program = app;
		rect = new GRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT-150);
		rect.setFilled(true);
		rect.setColor(Color.PINK);
		
		titleLabel.setLabel("---High Score List---");
		titleLabel.setLocation(140,70);
		titleLabel.setColor(Color.BLACK);
        titleLabel.setFont(SCORE_FONT);
        
    	backbtn = new GRect(0,450,Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT-450);
		backbtn.setFilled(false);
		back = new GLabel("QUIT", Main.WINDOW_WIDTH/2-WIDTH/4, 50);
		back.setColor(Color.RED);
		back.setFont("Arial-20");
		//quit.move(0,play.getHeight());
		
	    
	}
	


	public void update() {
	
	}
	
     public void showContents() {
         // TODO implement
    	 program.add(rect);
    	 program.add(titleLabel);
    	 program.add(backbtn);
    	 //program.add(scoreLabel);
     }
    
     
     public String printScoreLabels(double x, double y) {
 		String displayScore = "";
 		//displays Top 10 best scores
 		int displayTop10 = 10;
 		
 		ArrayList<Score> highScoreList;
 		highScoreList = show.getScores();
 		
 		//i starts at beginning
 		//j is the size of the list
 		int i = 0;
 		int j = highScoreList.size();
 		
 		if (j > displayTop10)
 		{
 			j = displayTop10;
 		}
 		
 		
 		while (i < j)
 		{
 			
 			displayScore += (i + 1) + ".\t" + highScoreList.get(i).getName() + "\t\t" + highScoreList.get(i).getScore() + "\n";
 		    i++;
 		}
 		
 		return displayScore;
 		
 		
 	}
 	
     public void hideContents() {
         // TODO implement
     }
    
     public void mousePressed(MouseEvent e) {
         // TODO implement
     }

     public void mouseReleased(MouseEvent e) {
         // TODO implement
     }
    
     public void mouseClicked(MouseEvent e) {
    	 if(program.getElementAt(e.getX(), e.getY()) == backbtn ||program.getElementAt(e.getX(), e.getY()) == back ){
     		program.switchToScreen(new MainMenu(program));
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

