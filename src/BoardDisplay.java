import java.awt.Color;
import acm.graphics.*;

public class BoardDisplay extends Display{

    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;
    public static final Color LINE_COLOR = Color.WHITE;

    Board board;
    GRect[][] tiles;
    int rows, cols;

    BoardDisplay(Board board){
        super();
        this.board = board;
        rows = board.getTiles().length;
        cols = board.getTiles()[0].length;
        tiles = new GRect[rows][cols];

        initBack();
        initTiles();
        initGrid();
    }

    public void update(){
        updateTiles();
    }

    public void updateTiles() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                Color color;
                if(board.getTiles()[y][x] == null) color = EMPTY_TILE_COLOR;
                else color = TileType.getColor(board.getTiles()[y][x].getType());

                tiles[y][x].setColor(color);
            }
        }
    }

    public void displayBoard(){
        int rows = board.getTiles().length;
        int cols = board.getTiles()[0].length;
        init(board, rows, cols);
    }

    private void init(Board board, int rows, int cols){
        initTiles();
    }

    private void initBack(){
        GRect background = new GRect(0, 0, TILE_SIZE*cols, TILE_SIZE*rows);
        background.setColor(EMPTY_TILE_COLOR);
        background.setFilled(true);
        addObject(background);
    }

    private void initGrid(){
        for (int y = 0; y < rows+1; y++) {
            int yPos =TILE_SIZE * y;
            int xStart = 0;
            int xEnd = TILE_SIZE*cols;
            GLine line = new GLine(xStart, yPos, xEnd, yPos);
            line.setColor(LINE_COLOR);
            addObject(line);
        }

        for (int x = 0; x < cols+1; x++) {
            int xPos = TILE_SIZE*x;
            int yEnd = TILE_SIZE * rows;
            GLine line = new GLine(xPos, 0, xPos, yEnd);
            line.setColor(LINE_COLOR);
            addObject(line);
        }
    }

    private void initTiles(){
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                GRect t = new GRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                t.setColor(EMPTY_TILE_COLOR);
                t.setFilled(true);
                tiles[y][x] = t;
                addObject(tiles[y][x]);
            }
        }
    }

}
