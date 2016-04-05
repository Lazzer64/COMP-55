//Alex and Tom's Territory
import java.util.Stack;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.event.MouseEvent;

public class Game extends GraphicsPane{

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 6;
    public static final int TILE_MOVE_DELAY = 150; // In milliseconds
    public static final int TILE_SIZE = Main.WINDOW_WIDTH/NUM_COLS; 
    public static final int BOARD_X = 0;
    public static final int BOARD_Y = Main.WINDOW_HEIGHT - NUM_ROWS * TILE_SIZE;
    public static final int SCORE_X = 0;
    public static final int SCORE_Y = 20; 
    public static final int NUM_ALLOWED_MOVES = 5;

    Main program;
    Unit player, enemy;
    RowCol start, end;
    BoardDisplay boardDisplay;
    UiDisplay uiDisplay;
    CombatDisplay combatDisplay;

    int score = 0;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    boolean canMove = true;
    Stack<RowCol> moveList = new Stack<RowCol>();

    public Game(Main program){
        this.program = program;
        this.boardDisplay = new BoardDisplay(program);
        this.uiDisplay = new UiDisplay(program);
        this.combatDisplay = new CombatDisplay(program);
        update();
    }

    public void mousePressed(MouseEvent e) {
        if(isInBoard(e.getX(), e.getY()) && canMove){ 
            start = getTileAt(e.getX(),e.getY()).getPosition();
            moveList.push(start);
        }
        update();
    }
    public void mouseDragged(MouseEvent e) {

        if(isInBoard(e.getX(), e.getY())) end = getTileAt(e.getX(),e.getY()).getPosition(); 

        int xAdj = Math.abs(end.getX() - moveList.peek().getX());
        int yAdj = Math.abs(end.getY() - moveList.peek().getY());
        boolean adjacent = xAdj <= 1 && yAdj <= 1 && xAdj != yAdj;

        if(adjacent){
            if(moveList.size() > 1 && end.equals(moveList.elementAt(moveList.size()-2))){
                board.moveTile(moveList.pop(),moveList.peek());
            }
            else if(moveList.size() <= NUM_ALLOWED_MOVES && canMove){ 
                board.moveTile(start,end);
                moveList.push(end);
            }
        }

        start = end;
        update();
    }
    public void mouseReleased(MouseEvent e) {
        if(canMove && moveList.size() > 1) boardStep();
        moveList = new Stack<RowCol>();
        update();
    }

    private void boardStep() {
        new Timer().schedule(
                new TimerTask(){
                    public void run(){
                        if(board.step()){
                            if(!board.getMatches().isEmpty()) matchEffect(board.getMatches().peek());
                            boardStep();
                            update();
                            canMove = false;
                        } else canMove = true;
                    }
                }
        ,TILE_MOVE_DELAY);
    }

    public void showContents(){
        boardDisplay.showContents();
        uiDisplay.showContents();
        combatDisplay.showContents();
    }

    public void hideContents(){
        boardDisplay.hideContents();
        uiDisplay.hideContents();
        combatDisplay.hideContents();
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
        boardDisplay.displayBoard(board, BOARD_X, BOARD_Y);
        uiDisplay.displayTilePath(moveList, BOARD_X, BOARD_Y);
        uiDisplay.displayScore(score, SCORE_X, SCORE_Y);
        combatDisplay.displayCombatField(player, enemy);
    }
    
    // Helpers 

    private boolean isInBoard(int x, int y){
        return (x >= BOARD_X && x >= 0 && y >= BOARD_Y && y >= 0);
    }

    private void matchEffect(Match m) {
        // TODO implement
        System.out.println(m.getType()+" size: "+m.size());
        score += m.size();
    }

    private Tile getTileAt(int x, int y){
        if(!isInBoard(x,y)) return null;
        int row = (int)((y-BOARD_Y)/TILE_SIZE);
        int col = (int)((x-BOARD_X)/TILE_SIZE);
        return board.getTiles()[row][col];
    }

}
