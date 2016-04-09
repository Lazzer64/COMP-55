import java.awt.Font;
import java.awt.Color;
import java.util.Stack;
import acm.graphics.*;

public class TilePathDisplay extends Display{

    public static final Color PATH_COLOR = Color.BLACK;
    public static final Font PATH_FONT = new Font("Consolas",Font.BOLD, 20);
    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);

    GLabel scoreLabel;
    Stack<RowCol> moveList;

    TilePathDisplay(GraphicsApplication program, Stack<RowCol> moveList){
        super(program);
        this.moveList = moveList;
    }

    public void repaint() {
        displayTilePath(moveList);
    }

    public void displayTilePath(Stack<RowCol> moveList) {
        int i = 0;
        clean();
        for(RowCol x: moveList) {

            String text = i+"";
            if(i==0) text = "X";

            GLabel l = new GLabel(text);
            l.setFont(PATH_FONT);
            l.setColor(PATH_COLOR);
            l.setLocation(x.getX() * TILE_SIZE + TILE_SIZE/2, x.getY() * TILE_SIZE + TILE_SIZE/2);
            addObject(l);
            i++;
        }
    }

}
