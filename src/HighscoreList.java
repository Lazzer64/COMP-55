//Tom's territory 
//Beginning 4/1/16
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.Scanner;
import java.io.File;

/**
 	* Creates list, keeps track of top 10 scores in games
 	* Writes/reads into file, descending order
 	*/

public class HighscoreList {

    //Score Objects in Array List
    private ArrayList<Score> highScoreList;
    //Name of file for highscores
    private static final String highScoreFile = "resource/scores.txt";
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
   
    //Score Objects in Array
    private Score[] scores;
    //private int numScores;
    //private int numScores2;

    
    
    /**Generates the default constructor for an empty array list
     * 
     */
    public HighscoreList()
    {
    	highScoreList = new ArrayList<Score>();
    }
    
   /* public int getnumScores()
    {
    	return numScores;
    }*/
    
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
    
    /**Sorts the scores by calling the sort function
     * 
     */
    public Score[] sortScores()
    {
    	Score temp;
    	int min;
    	
    	for(int i = 0; i < highScoreList.size(); i++)
    	{
    		min = i;
    		
    		for(int j = i+1; j < highScoreList.size(); j++)
    		{
    			if (highScoreList.get(j).compare(highScoreList.get(min)) > 0)
    			{
    				min = j;
    			}
    				
    		}
    			temp = highScoreList.get(min);
    			
    			//sets the value of the min to index i
    			highScoreList.set(min, highScoreList.get(i));
    			//value of index i becomes temp
    			highScoreList.set(i, temp);
    			highScoreList.set(i, temp);
    		
    		}
    		
    	//Scores array uses the highScoreList file size
    	scores = new Score[highScoreList.size()];
    	for (int i = 0; i < highScoreList.size(); i++)
    	{
    		//sets the score array to the highScoreList
    		scores[i] = highScoreList.get(i);
    	}
    	
    	return scores;
    
    }
    
    /** Writes score object onto the highScoreList
     * @param scoreObj
     */
    public void addScore(Score scoreObj)
    {
    	//numScores = 0;
    	//numScores2 = 0;
    	
    	 Score temp;
    	 
    	 if (highScoreList.size() < 10)
    	 {
    	 	highScoreList.add(scoreObj);
    	 }
    	 
    	 else
    	 {
    	 	for (int i = 0; i < 10; i++)
    	 	{
    	 		if(highScoreList.get(i).compare(scoreObj) == -1)
    	 		{
    	 			temp = highScoreList.get(i);
    	 			highScoreList.set(highScoreList.indexOf(temp), scoreObj);
    	 			scoreObj = temp;
    	 		}
    	 	}
    	 }
    	 		    	
    }
      
    
    
    public void writeScores()
	{
    	
    
	}

	/**Load scores form file, check if can be read
	 * @param file
	 * @returns a string parameter file
	 */
	public void readScoreFile()
	{
		  try 
		  {
		        input = new ObjectInputStream(new FileInputStream(highScoreFile));
		        highScoreList = (ArrayList<Score>) input.readObject();
		   } 
		  catch (FileNotFoundException e) 
		  {
		        System.out.println("FNF Error: " + e.getMessage());
		  } 
		  catch (IOException e) 
		  {
		        System.out.println("IO Error: " + e.getMessage());
		   } 
		  catch (ClassNotFoundException e) 
		  {
		        System.out.println("Class Not Found Error:" + e.getMessage());
		    } 
		  finally 
		  {
		       try {
		            if (output != null) {
		                output.flush();
		                output.close();
		            }
		        } catch (IOException e) {
		            System.out.println("Error IO: " + e.getMessage());
		        }
		    }
		}
			
		
		
}
	

	


