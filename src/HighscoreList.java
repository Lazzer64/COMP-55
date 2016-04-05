//Tom's territory 
//Beginning 4/1/16
import java.io.File;
import java.util.ArrayList;
/**
 	* Creates list, keeps track of top 10 scores in games
 	* 
 	*/


public class HighscoreList {

    File scoreList;
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
    
    /** Adds score object onto the highScoreList
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
    
    
    public void writeScore(Score score) {
        
    }
    
    public Score[] readScores() {
        // TODO implement
        return null;
    }
    
}
