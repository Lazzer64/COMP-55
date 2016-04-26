import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent; 


//Program WIDTH = 400, HEIGHT = 600;

public class ScoreScreen extends GraphicsPane {
private Main program;
private GRect rect;
private GImage returnpic;
private GLabel scoreLabel[] = new GLabel[11];
private GLabel nameLabel[] = new GLabel[11];
private GImage titleIMG;
private GImage background;


public static final int WIDTH = 200;
public static final int HEIGHT = 50;
public static final int OFFSET = 75;

HighscoreList show = new HighscoreList();
	
	public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 10);
	
	GLabel titleLabel = new GLabel("");
	Score score;
	
	public ScoreScreen(Main app) {
		program = app;
		
		

		returnpic = new GImage("SpriteSheets/returnpic.png");
        returnpic.setSize(WIDTH, HEIGHT);
        returnpic.setLocation(Main.WINDOW_WIDTH/2-WIDTH/2, OFFSET*6);
		//backLabel.move(0,back.getHeight());
		
		titleIMG = new GImage("SpriteSheets/button (1).png");
        titleIMG.setSize(400, 75);
        titleIMG.setLocation(Main.WINDOW_WIDTH/2-WIDTH,70);
        
        
		
	}
	
public void initBackground() {
        
        background = new GImage("SpriteSheets/background2.jpg");
        background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT);
        program.add(background);
    }


	public void update() {

	}
	
     public void showContents() {
         // TODO implement
    	 initBackground();
    	// program.add(titleLabel);
    	 program.add(returnpic);
    	 program.add(titleIMG);
    	 
    	 printScoreLabels(175);
     }
    
 		
 	
     public void hideContents() {
    	 program.remove(background);
    	 program.remove(titleIMG);
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
  		
  			//offsetLength = Integer.toString(highScoreList.get(i).getName().length());
  			//spaces = "......";
  			displayName = (i + 1) + ".\t" + highScoreList.get(i).getName(); //+ spaces-(offsetLength) + highScoreList.get(i).getScore() + "\n";
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
         // TODO implement
     }

     public void mouseReleased(MouseEvent e) {
         // TODO implement
     }
    
     public void mouseClicked(MouseEvent e) {
    	 if(program.getElementAt(e.getX(), e.getY()) == returnpic ){
     		program.switchToScreen(new MainMenu(program));
     		Sound.clicking.play();
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

