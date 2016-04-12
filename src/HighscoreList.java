//Tom's territory 
//Beginning 4/1/16
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.Serializable;

/**
 	* Creates list, keeps track of top 10 scores in games
 	* Writes/reads into file, descending order
 	*/

public class HighscoreList {

    //Score Objects in Array List
    private ArrayList<Score> highScoreList;
    //Name of file for highscores
    private static final String highScoreFile = "scores.dat";
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    
    /**Generates the default constructor for an empty array list
     * 
     */
    public HighscoreList()
    {
    	highScoreList = new ArrayList<Score>();
    }
    
    /**Get the scores of the highScoreList by loading file, sorting them
     * then returning the sorted list
     * @return
     */
    public ArrayList<Score> getScores(){
    	readScoreFile();
    	sort();
    	return highScoreList;
    }
    
    private void sort() 
    {
    	ScoreComparator comparator = new ScoreComparator();
    	Collections.sort(highScoreList, comparator);
    }
    
    public void addScore(String name, int score) {
        readScoreFile();
        highScoreList.add(new Score(name, score));
        writeScoreFile();
    }
    
   
    
    public void writeScoreFile()
	{
    	try 
    	{
    		output = new ObjectOutputStream(new FileOutputStream(highScoreFile));
    	    output.writeObject(highScoreList);
    	} 
    	catch (FileNotFoundException e) 
    	{
    	        System.out.println("[Write] File Not Found: " + e.getMessage() + ",the program will try and make a new file");
    	} 
    	catch (IOException e) 
    	{
    	 System.out.println("[Write] IO ERROR" + e.getMessage());
    	} 
    	finally 
    	{
    		try 
    		{
    			if (output != null) {
    	                output.flush();
    	                output.close();
    			}
    		} 
    		catch (IOException e) 
    		{
    	            System.out.println("[Write] ERROR: " + e.getMessage());
    	    }
    	    }
	}

	/**Load scores form file, check if can be read
	 * @param file
	 * @returns a string parameter file
	 */
	@SuppressWarnings("unchecked")
	public void readScoreFile()
	{
		  try 
		  {
		        input = new ObjectInputStream(new FileInputStream(highScoreFile));
		        highScoreList = (ArrayList<Score>) input.readObject();
		   } 
		  catch (FileNotFoundException e) 
		  {
		        System.out.println("[Read] File not found: " + e.getMessage());
		  } 
		  catch (IOException e) 
		  {
		        System.out.println("[Read] IO Error: " + e.getMessage());
		   } 
		  catch (ClassNotFoundException e) 
		  {
		        System.out.println("[Read] Class Not Found:" + e.getMessage());
		    } 
		  finally 
		  {
		       try {
		            if (output != null) {
		                output.flush();
		                output.close();
		            }
		        } catch (IOException e) {
		            System.out.println("[Read] IO Error: " + e.getMessage());
		        }
		    }
		}
	
	public String printScores() {
		String displayScore = "";
		//displays Top 10 best scores
		int displayTop10 = 10;
		
		ArrayList<Score> highScoreList;
		highScoreList = getScores();
		
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
			
}



	


