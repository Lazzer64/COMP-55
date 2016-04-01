//Alex and Tom's Territory
import java.util.List;
import java.util.Queue;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.event.MouseEvent;
import java.awt.Color;
import acm.graphics.*;
import java.util.ArrayList;

public class Game extends GraphicsPane{

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;
    public static final int TILE_MOVE_DELAY = 250; // In milliseconds
    public static final int TILE_SIZE = 40; 
    public static final int BOARD_X = 0;
    public static final int BOARD_Y = 0;
    public static final Color LINE_COLOR = Color.WHITE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;

    Main program;
    int score;
    Unit player, enemy;
    Board board;
    ArrayList<GObject> boardObjects;   
    RowCol start, end;

    public void mousePressed(MouseEvent e) {
        start = getTileAt(e.getX(),e.getY()).getPosition();
    }
    public void mouseDragged(MouseEvent e) {
        end = getTileAt(e.getX(),e.getY()).getPosition();
        board.moveTile(start,end);
        start = getTileAt(e.getX(),e.getY()).getPosition();
        updateTiles();
    }
    public void mouseReleased(MouseEvent e) {
        makeMatches();
    }

    public Game(Main program){
        this.program = program;
        this.score = 0;
        this.boardObjects = new ArrayList<GObject>();
        board = new Board(NUM_ROWS,NUM_COLS);
    }

    public void showContents(){
        displayBoard();
        for(GObject o: boardObjects){
            program.add(o);
        }
    }

    public void hideContents(){
        for(GObject o: boardObjects){
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
        displayBoardBack();
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

    private void matchEffect(List<Match> matches) {
        // TODO implement
        for(Match m: matches) System.out.println(m.getType()+" size: "+m.size());
    }

    private List<Match> makeMatches() {
        
        Queue<Match> matches = board.getMatches();
        List<Match> totalMatches = new ArrayList<Match>(matches);
        updateTiles();

        new Timer().schedule(new TimerTask(){ // after TILE_MOVE_DELAY milliseconds drops tiles and repaints 

            public void run(){

                board.dropTiles();
                updateTiles();

                new Timer().schedule(new TimerTask(){ // after TILE_MOVE_DELAY milliseconds refills tiles and repaints 
                    public void run(){

                        board.refillBoard();
                        updateTiles();

                        if(!matches.isEmpty()) { // If there are still matches on the board, repeat
                            totalMatches.addAll(makeMatches());
                        }

                    }}, TILE_MOVE_DELAY);

            }}, TILE_MOVE_DELAY);

        matchEffect(totalMatches);
        return totalMatches;
    }

    private void displayBoardBack(){
        GRect background = new GRect(BOARD_X,BOARD_Y,TILE_SIZE*NUM_COLS,TILE_SIZE*NUM_ROWS);
        background.setColor(EMPTY_TILE_COLOR);
        background.setFilled(true);
        boardObjects.add(background);
    }

    private void updateTiles() {
        for(GObject o: boardObjects) program.remove(o);
        boardObjects = new ArrayList<GObject>();
        displayBoard();
        for(GObject o: boardObjects) program.add(o);
    }

    private Tile getTileAt(int x, int y){
        int row = (int)((y-BOARD_Y)/TILE_SIZE);
        int col = (int)((x-BOARD_X)/TILE_SIZE);
        return board.getTiles()[row][col];
    }

    private void displayGrid(){
        for (int y = 0; y < NUM_ROWS+1; y++) {
            int yPos = BOARD_Y + (TILE_SIZE)*y;
            int xStart = BOARD_X;
            int xEnd = BOARD_X + TILE_SIZE*NUM_COLS;
            GLine line = new GLine(xStart, yPos, xEnd, yPos);
            line.setColor(LINE_COLOR);
            boardObjects.add(line);
        }
        for (int x = 0; x < NUM_COLS+1; x++) {
            int xPos = BOARD_X + (TILE_SIZE)*x;
            int yStart = BOARD_Y;
            int yEnd = BOARD_Y + TILE_SIZE*NUM_ROWS;
            GLine line = new GLine(xPos, yStart, xPos, yEnd);
            line.setColor(LINE_COLOR);
            boardObjects.add(line);
        }
    }

    private void displayTiles(){
        Tile[][] tiles = board.getTiles();
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                displayTile(tiles[y][x]);
            }
        }
    }

    private void displayTile(Tile tile){
        if(tile == null) return;
        int x = BOARD_X + tile.getPosition().getX()*TILE_SIZE;
        int y = BOARD_Y + tile.getPosition().getY()*TILE_SIZE;
        GRect t = new GRect(x, y, TILE_SIZE, TILE_SIZE);
        t.setColor(TileType.getColor(tile.getType()));
        t.setFilled(true);
		boardObjects.add(t);
    }
    
}
