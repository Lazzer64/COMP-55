//Alex and Tom's Territory
import java.util.Stack;
import java.util.Collection;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import acm.graphics.*;
import java.util.ArrayList;

public class Game extends GraphicsPane{

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 6;
    public static final int TILE_MOVE_DELAY = 150; // In milliseconds
    public static final int TILE_SIZE = Main.WINDOW_WIDTH/NUM_COLS; 
    public static final int BOARD_X = 0;
    public static final int BOARD_Y = Main.WINDOW_HEIGHT - NUM_ROWS * TILE_SIZE;
    public static final int SCORE_X = 0;
    public static final int SCORE_Y = 20; 
    public static final int HP_BAR_HEIGHT = 25;
    public static final int NUM_ALLOWED_MOVES = 5;
    public static final Color LINE_COLOR = Color.WHITE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;
    public static final Font SCORE_FONT = new Font("Consolas",Font.BOLD, 20);
    public static final Font TILE_PATH_FONT = new Font("Consolas",Font.BOLD, 20);

    Main program;
    Unit player, enemy;
    RowCol start, end;

    int score = 0;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    ArrayList<GObject> uiObjects = new ArrayList<GObject>();
    ArrayList<GObject> combatObjects = new ArrayList<GObject>();
    ArrayList<GObject> boardObjects = new ArrayList<GObject>();
    boolean canMove = true;

    Stack<RowCol> moveList = new Stack<RowCol>();

    public void mousePressed(MouseEvent e) {
        if(isInBoard(e.getX(), e.getY()) && canMove){ 
            start = getTileAt(e.getX(),e.getY()).getPosition();
            moveList.push(start);
        }
        updateTiles();
    }
    public void mouseDragged(MouseEvent e) {

        if(isInBoard(e.getX(), e.getY())) end = getTileAt(e.getX(),e.getY()).getPosition(); 

        if(!start.equals(end)){
            if(moveList.size() > 1 && end.equals(moveList.elementAt(moveList.size()-2))){
                moveList.pop();
                board.moveTile(start,end);
            }
            else if(moveList.size() <= NUM_ALLOWED_MOVES && canMove){ 
                board.moveTile(start,end);
                moveList.push(end);
            }
        }

        start = end;
        updateTiles();
    }
    public void mouseReleased(MouseEvent e) {
        if(canMove) boardStep();
        moveList = new Stack<RowCol>();
    }

    private void boardStep() {
        new Timer().schedule(
                new TimerTask(){
                    public void run(){
                        if(board.step()){
                            if(!board.getMatches().isEmpty()) matchEffect(board.getMatches().peek());
                            boardStep();
                            updateTiles();
                            canMove = false;
                        } else canMove = true;
                    }
                }
        ,TILE_MOVE_DELAY);
    }


    public Game(Main program){
        this.program = program;
        displayBoard();
        displayCombatField();
        displayScore();
    }

    public void showContents(){
        displayObjects(boardObjects);
        displayObjects(combatObjects);
        displayObjects(uiObjects);
    }

    public void hideContents(){
        hideObjects(boardObjects);
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
        int x = 50, y = 50;
        displayUnit(x,y,player);
        displayUnit(Main.WINDOW_WIDTH - x - 75,y,enemy);
    }
    
    public void displayBoard() {
        displayBoardBack();
        displayTiles();
        displayGrid();
        displayTilePath();
    }

    public void displayUnit(int x, int y, Unit unit) {

        int width = 75, height = 150; // TODO change rectangle to display unit's image
        int unitHp = 7;// TODO replace with unit.getHp() when implemented;
        int unitMaxHp = 10;// TODO replace with unit.getMaxHp() when implemented;

        GRect u = new GRect(x,y,width,height);

        GRect hpMax = new GRect(x, y+height, width, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(Color.RED);

        GRect hp = new GRect(x, y+height, width*(1.0*unitHp/unitMaxHp), HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(Color.GREEN);

        combatObjects.add(u);
        combatObjects.add(hpMax);
        combatObjects.add(hp);
    }
    
    public void displayScore() {
        GLabel scoreLabel = new GLabel("");
        scoreLabel.setColor(Color.BLACK);
        scoreLabel.setLabel("Score: "+score);
        scoreLabel.setLocation(SCORE_X, SCORE_Y);
        scoreLabel.setFont(SCORE_FONT);
        uiObjects.add(scoreLabel);
    }
    
    // Helpers 

    private void displayObjects(Collection<GObject> objects) {
        for(GObject o: objects) program.add(o);
    }

    private void hideObjects(Collection<GObject> objects) {
        for(GObject o: objects) program.remove(o);
    }

    private boolean isInBoard(int x, int y){
        return (x >= BOARD_X && x >= 0 && y >= BOARD_Y && y >= 0);
    }

    private void matchEffect(Match m) {
        // TODO implement
        System.out.println(m.getType()+" size: "+m.size());
    }

    private void displayTilePath() {
        int i = 0;
        for(RowCol x: moveList) {
            String text = i+"";
            if(i==0) text = "X";
            GLabel l = new GLabel(text);
            l.setFont(TILE_PATH_FONT);
            l.setColor(Color.WHITE);
            l.setLocation(BOARD_X + x.getX() * TILE_SIZE + TILE_SIZE/2, BOARD_Y + x.getY() * TILE_SIZE + TILE_SIZE/2);
            boardObjects.add(l);
            i++;
        }
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
        if(!isInBoard(x,y)) return null;
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
