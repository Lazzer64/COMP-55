import java.awt.Color;
import acm.graphics.*;

public class BoardDisplay extends Display{

    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;
    public static final Color LINE_COLOR = Color.WHITE;

    Board board;

    BoardDisplay(GraphicsApplication program, Board board){
        super(program);
        this.board = board;
        displayBoard();
    }

    public void repaint(){
        displayBoard();
    }

    public void displayBoard(){
        int rows = board.getTiles().length;
        int cols = board.getTiles()[0].length;
        init(board, rows, cols);
    }

    private void init(Board board, int rows, int cols){
        displayBoardBack(rows, cols);
        displayTiles(board.getTiles());
        displayGrid(rows, cols);
    }


    private void displayBoardBack(int rows, int cols){
        GRect background = new GRect(0, 0, TILE_SIZE*cols, TILE_SIZE*rows);
        background.setColor(EMPTY_TILE_COLOR);
        background.setFilled(true);
        addObject(background);
    }

    private void displayGrid(int rows, int cols){
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

    private void displayTiles(Tile[][] tiles){
        int num_rows = tiles.length;
        int num_cols = tiles[0].length;
        for (int y = 0; y < num_rows; y++) {
            for (int x = 0; x < num_cols; x++) {
                displayTile(tiles[y][x]);
            }
        }
    }

    private void displayTile(Tile tile){
        if(tile == null) return;
        int x = tile.getPosition().getX()*TILE_SIZE;
        int y = tile.getPosition().getY()*TILE_SIZE;
        GRect t = new GRect(x, y, TILE_SIZE, TILE_SIZE);
        t.setColor(TileType.getColor(tile.getType()));
        t.setFilled(true);
		addObject(t);
    }

}
