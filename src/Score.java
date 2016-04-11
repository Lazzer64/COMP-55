//Tom's Territory
import java.io.Serializable;

public class Score implements Serializable {
	private int score;
	private String name;

    public Score(String name, int score) { 
		this.score = score;
		this.name = name;
    }
    
    static Score parseScore(String input) {
        // TODO implement
    	return null;
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
