import java.awt.Font;
import java.awt.Color;
import java.util.Stack;
import acm.graphics.*;

public class TilePathDisplay extends Display{

    public static final Color PATH_COLOR = Color.BLACK;
    public static final Color EDGE_COLOR = Color.BLACK;
    public static final Font PATH_FONT = new Font("Consolas",Font.BOLD, 20);
    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);

    Stack<RowCol> moveList;

    int TILE_SIZE = Game.TILE_SIZE;
    int PATH_SIZE = TILE_SIZE/4;
    int maxMoves;
    GRect[] arrow;

    TilePathDisplay(Stack<RowCol> moveList, int maxMoves){
        super();
        this.moveList = moveList;
        arrow = new GRect[maxMoves+1];
        initMarkers();
    }

    public void resize(double x){
        super.resize(x);
        this.TILE_SIZE = (int) (TILE_SIZE * x);
        this.PATH_SIZE = (int) (PATH_SIZE * x);
    }

    public void update() {
        updatePath();
    }

    public void initMarkers(){
        for (int i = 0; i < arrow.length; i++) {
            GRect l = new GRect(PATH_SIZE,PATH_SIZE);
            l.setFillColor(PATH_COLOR);
            l.setFilled(true);
            arrow[i] = l;
            addObject(l);
        }
    }

    public void updatePath() {
        for (int e = 0; e < arrow.length; e++) arrow[e].setVisible(false);
        int i = 0;
        RowCol lastRowCol = null;
        Stack<RowCol> moves = (Stack<RowCol>) moveList.clone();
        for(RowCol x: moves) {
            arrow[i].setLocation(x.getX() * TILE_SIZE + (TILE_SIZE-PATH_SIZE)/2, x.getY() * TILE_SIZE + (TILE_SIZE-PATH_SIZE)/2);

            if(i > 0) {
            	int direction = getLastDirectionMoved(x,lastRowCol);
            	if(direction == 1) { //Up
            		arrow[i].setSize(PATH_SIZE,TILE_SIZE);
            	} else if(direction == 2) { //Down
            		arrow[i].setSize(PATH_SIZE,TILE_SIZE);
            		arrow[i].move(0,-(TILE_SIZE-PATH_SIZE));
            	} else if(direction == 3) { //Left
            		arrow[i].setSize(TILE_SIZE, PATH_SIZE);
            	} else if(direction == 4) { //Right
            		arrow[i].setSize(TILE_SIZE, PATH_SIZE);
            		arrow[i].move(-(TILE_SIZE-PATH_SIZE),0);
            	}
            }
            arrow[i].setVisible(true);
            i++;
            lastRowCol = x;
        }
    }
    // 1=Up   2=Down   3=Left   4=Right
    public int getLastDirectionMoved(double currX, double currY, GRectangle bounds) {
    	if(bounds.contains(currX,currY + TILE_SIZE)) return 1;
    	if(bounds.contains(currX,currY - TILE_SIZE)) return 2;
    	if(bounds.contains(currX + TILE_SIZE,currY)) return 3;
    	if(bounds.contains(currX - TILE_SIZE,currY)) return 4;
    	return -1;
    }
    public int getLastDirectionMoved(RowCol currPos, RowCol lastPos) {
    	if(currPos.getX() == lastPos.getX() && currPos.getY() > lastPos.getY()) return 2;
    	if(currPos.getX() == lastPos.getX() && currPos.getY() < lastPos.getY()) return 1;
    	if(currPos.getX() > lastPos.getX() && currPos.getY() == lastPos.getY()) return 4;
    	if(currPos.getX() < lastPos.getX() && currPos.getY() == lastPos.getY()) return 3;
    	return -1;
    }
}
