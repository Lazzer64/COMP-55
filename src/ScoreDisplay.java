import java.awt.Font;
import java.awt.Color;
import acm.graphics.*;

public class ScoreDisplay extends Display {

    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);

    GLabel scoreLabel = new GLabel("");
    Score score;

    ScoreDisplay(Score score){
        super();
        this.score = score;
        scoreLabel.setColor(Color.BLACK);
        scoreLabel.setFont(SCORE_FONT);
        addObject(scoreLabel);
    }

    public void setLocation(double x, double y){
        scoreLabel.setLocation(x,y);
    }

    public void update() {
        scoreLabel.setLabel("Score: "+score.getScore());
    }

}
