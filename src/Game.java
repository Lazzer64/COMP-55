import java.awt.Color;
import acm.graphics.*;
import java.util.ArrayList;

public class Game extends GraphicsPane{

    public static final int TILE_SIZE = 40; 
    public static final int BOARD_X = 0;
    public static final int BOARD_Y = 0;
    public static final Color LINE_COLOR = Color.WHITE;

    Main program;
    int score;
    Unit player, enemy;
    Board board;
    ArrayList<GObject> display;   

    public Game(Main program){
        this.program = program;
        this.score = 0;
        this.display = new ArrayList<GObject>();
        board = new Board();
    }

    public void showContents(){
        displayBoard();
        for(GObject o: display){
            program.add(o);
        }
    }

    public void hideContents(){
        for(GObject o: display){
            program.remove(o);
        }
    }

    public boolean checkWinFight() {
        // TODO implement
        return false;
    }

    public boolean checkLoseGame() {
        // TODO implement
        return false;
    }

    public void nextFight() {
        // TODO implement
    }

    public void playerTurn() {
        // TODO implement
    }
    
    public void saveScore(int Score) {
        // TODO implement
    }
    
    public void update() {
        // TODO implement
    }
    
    public void displayCombatField() {
        // TODO implement
    }
    
    public void displayBoard() {
        displayTiles();
        displayGrid();
    }

    public void displayUnit(int x, int y, Unit unit) {
        // TODO implement
    }
    
    public void displayScore() {
        // TODO implement
    }
    
    // Helpers
    private void displayGrid(){
        for (int y = 0; y < Board.NUM_ROWS+1; y++) {
            int yPos = BOARD_Y + (TILE_SIZE)*y;
            int xStart = BOARD_X;
            int xEnd = BOARD_X + TILE_SIZE*Board.NUM_COLS;
            GLine line = new GLine(xStart, yPos, xEnd, yPos);
            line.setColor(LINE_COLOR);
            display.add(line);
        }
        for (int x = 0; x < Board.NUM_COLS+1; x++) {
            int xPos = BOARD_X + (TILE_SIZE)*x;
            int yStart = BOARD_Y;
            int yEnd = BOARD_Y + TILE_SIZE*Board.NUM_ROWS;
            GLine line = new GLine(xPos, yStart, xPos, yEnd);
            line.setColor(LINE_COLOR);
            display.add(line);
        }
    }

    private void displayTiles(){
        Tile[][] tiles = board.getTiles();
        for (int y = 0; y < Board.NUM_ROWS; y++) {
            for (int x = 0; x < Board.NUM_COLS; x++) {
                displayTile(tiles[y][x]);
            }
        }
    }

    private void displayTile(Tile tile){
        int x = BOARD_X + tile.getPosition().getX()*TILE_SIZE;
        int y = BOARD_Y + tile.getPosition().getY()*TILE_SIZE;
        GRect t = new GRect(x, y, TILE_SIZE, TILE_SIZE);
        t.setColor(getColor(tile));
        t.setFilled(true);
		display.add(t);
    }

    private Color getColor(Tile t){
        switch(t.getType()){
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }
    
}
