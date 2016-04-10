//Tom's Territory

public class Score {
	private int score;
	private String name;

    public Score(int score, String name) { 
		this.score = score;
		this.name = name;
    }
    
    static Score parseScore(String input) {
        // TODO implement
    	return null;
    }
    
    
    /**Compares the scores in the list for sorting
     * @param score1
     * @param score2
     * @return
     */
    public int compare(Score scoreIn){
    	int s1 = this.getScore();
    	int s2 = scoreIn.getScore();
    	
    	if(s1 > s2){
    		return -1;
    	}
    	
    	else if (s1 < s2){
    		return +1;
    		}
    	else {
    		return 0;
    	}
     	
    }
    
    // Getters and Setters 
	public void setScore(int score) {
    	this.score = score;
	}

	public int getScore() {    
		return score;
	}

	public void setName(String name) {
    	this.name = name;
	}

	public String getName() {    
		return name;
	}

}
