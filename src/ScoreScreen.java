import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;

import java.awt.Color;
import java.awt.Font;


public class ScoreScreen extends GraphicsPane {
private Main program;
private GImage returnpic;
private GLabel scoreLabel[] = new GLabel[11];
private GLabel nameLabel[] = new GLabel[11];
private GImage scorepic;
private GImage background;

public static final GImage RETURN_IMAGE = new GImage("SpriteSheets/returnpic.png");
public static final GImage SCORE_IMAGE = new GImage("SpriteSheets/highscorelistpic.png");
public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/background2.jpg");

public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;

HighscoreList show = new HighscoreList();
	
	public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 10);
	
	GLabel titleLabel = new GLabel("");
	Score score;
	
	public ScoreScreen(Main app) {
		program = app;
	
		returnpic = RETURN_IMAGE;
        returnpic.setSize(WIDTH, HEIGHT);
        returnpic.setLocation(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6);
        
		scorepic = SCORE_IMAGE;
		scorepic.setLocation(100, 50);

	}
	
public void initBackground() {
        
        background = BACKGROUND_IMAGE;
        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
        program.add(background);
    }


     public void showContents() {
    	 initBackground();
    	 program.add(returnpic);
    	 program.add(scorepic);
    	 
    	 printScoreLabels(145);
     }
    
 		
 	
     public void hideContents() {
    	 program.remove(background);
    	 program.remove(scorepic);
    	 program.remove(returnpic);
    	 
    	 removeScoreLabels();
     }
     
     public void printScoreLabels(double y) {
  		String displayName;
  		String displayScores;
  		//String offsetLength;
  		//String spaces;
  	
  		int displayTop10 = 10;
  		
  		ArrayList<Score> highScoreList;
  		highScoreList = show.getScores();
  		
  		int j = highScoreList.size();
 		
 		if (j > displayTop10)
 		{
 			j = displayTop10;
 		}
 
 		
  	
 		for (int i = 0; i < j; i++)
  		{
  		
  			displayName = (i + 1) + ".\t" + highScoreList.get(i).getName(); 
  			displayScores = Integer.toString(highScoreList.get(i).getScore());
  			nameLabel[i] = new GLabel(displayName, Main.WINDOW_WIDTH/2-WIDTH/1.75, y);
 			nameLabel[i].setFont("Arial-20");
 			nameLabel[i].setColor(Color.WHITE);
 			
 			scoreLabel[i] = new GLabel(displayScores, Main.WINDOW_WIDTH/1-WIDTH/1.75, y);
 			scoreLabel[i].setFont("Arial-20");
 			scoreLabel[i].setColor(Color.WHITE);
 			
 			program.add(nameLabel[i]);
 			program.add(scoreLabel[i]);
 			
 			y+= 30 ; 
 			
  			}
  		}
  		
  		
      public void removeScoreLabels(){
    	  int displayTop10 = 10;
    		
    	ArrayList<Score> highScoreList;
    	highScoreList = show.getScores();
    		
    	int j = highScoreList.size();
   		
   		if (j > displayTop10)
   		{
   			j = displayTop10;
   		}
   		
     	 for (int i = 0; i < j; i++)
     	 {
     		 program.remove(scoreLabel[i]);
     		 program.remove(nameLabel[i]);
     		 
     	 }
      }
    
     public void mousePressed(MouseEvent e) {
    	 if(program.getElementAt(e.getX(), e.getY()) == returnpic ){
     		program.switchToScreen(new MainMenu(program));
     		Sound.clicking.play();
     	}
     }
}

