//Tom's territory 
//Beginning 4/1/16
import java.io.File;
import java.util.ArrayList;
/**
 	* Creates list, keeps track of top 10 scores in games
 	* Writes/reads into file, descending order
 	*/

public class HighscoreList {

    File scoreList;
    //array list
    private ArrayList<Score> highScoreList;
    Score[] scores;
    private int numScores;
    private int numScores2;

    public HighscoreList()
    {
    	highScoreList = new ArrayList<Score>();
    }
    
    public int getnumScores()
    {
    	return numScores;
    }
    
    /** Writes score object onto the highScoreList
     * @param scoreObj
     */
    public void addScore(Score scoreObj)
    {
    	numScores = 0;
    	numScores2 = 0;
    	
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
    
      
    
    /** Sorts scores
     * @return
     */
    public Score[] sortScores() {
        Score temp;
        int min;
        
        for(int i = 0; i < highScoreList.size(); i++)
        {
        	min = i;
        	
        	for (int j = i+1; j < highScoreList.size(); j++)
        	{
        		if (highScoreList.get(j).compare(highScoreList.get(min)) > 0)
        		{
        			min = j;
        		}
        		
        	}
        	temp = highScoreList.get(min);
        	highScoreList.set(min, highScoreList.get(i));
        	highScoreList.set(i, temp);
        	
        	scores = new Score[highScoreList.size()];
        }
        
        for(int i = 0; i < highScoreList.size(); i++)
        {
        	scores[i] = highScoreList.get(i);
        }
        	
        return scores;
        }
    	
    

	public void readScores(){
		
		
		
	}
	
	/**
	 * 
	 */
	public void writeScores()
	{
		
		
		
		
	}
	


