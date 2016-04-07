import java.awt.Font;
import java.awt.Color;
import java.util.Stack;
import acm.graphics.*;

public class UiDisplay extends Display{

    public static final Color PATH_COLOR = Color.BLACK;
    public static final Font PATH_FONT = new Font("Consolas",Font.BOLD, 20);
    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);

    GLabel scoreLabel;

    UiDisplay(GraphicsApplication program){
        super(program);
    }

    public void displayScore(int score, int scoreX, int scoreY) {

        objects.remove(scoreLabel);
        GLabel scoreLabel = new GLabel("");

        scoreLabel.setColor(Color.BLACK);
        scoreLabel.setLabel("Score: "+score);
        scoreLabel.setLocation(scoreX, scoreY);
        scoreLabel.setFont(SCORE_FONT);

        objects.add(scoreLabel);
        showContents();
    }

    public void displayTilePath(Stack<RowCol> moveList, int board_x, int board_y) {
        int i = 0;
        clean();
        for(RowCol x: moveList) {

            String text = i+"";
            if(i==0) text = "X";

            GLabel l = new GLabel(text);
            l.setFont(PATH_FONT);
            l.setColor(PATH_COLOR);
            l.setLocation(board_x + x.getX() * TILE_SIZE + TILE_SIZE/2, board_y + x.getY() * TILE_SIZE + TILE_SIZE/2);
            objects.add(l);
            i++;
        }
        showContents();
    }

}
