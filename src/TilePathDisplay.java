import java.awt.Font;
import java.awt.Color;
import java.util.Stack;
import acm.graphics.*;

public class TilePathDisplay extends Display{

    public static final Color PATH_COLOR = Color.BLACK;
    public static final Font PATH_FONT = new Font("Consolas",Font.BOLD, 20);
    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);

    Stack<RowCol> moveList;

    int maxMoves;
    GLabel[] markers;

    TilePathDisplay(Stack<RowCol> moveList, int maxMoves){
        super();
        this.moveList = moveList;
        markers = new GLabel[maxMoves+1];
        initMarkers();
    }

    public void update() {
        updatePath();
    }

    public void initMarkers(){
        for (int i = 0; i < markers.length; i++) {
            GLabel l = new GLabel("");
            l.setFont(PATH_FONT);
            l.setColor(PATH_COLOR);
            markers[i] = l;
            addObject(l);
        }
    }

    public void updatePath() {
        for (int e = 0; e < markers.length; e++) markers[e].setVisible(false);
        int i = 0;
        Stack<RowCol> moves = (Stack<RowCol>) moveList.clone();
        for(RowCol x: moves) {

            String text = i+"";
            if(i==0) text = "X";

            markers[i].setLabel(text);
            markers[i].setLocation(x.getX() * TILE_SIZE + TILE_SIZE/2, x.getY() * TILE_SIZE + TILE_SIZE/2);
            markers[i].move(x_adj,y_adj);

            markers[i].setVisible(true);
            i++;
        }
    }
}
