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
    public static final int SCORE_X = 5;
    public static final int SCORE_Y = 20; 
    public static final int NUM_ALLOWED_MOVES = 5;
    public static final int ENEMY_DAMAGE = 10;
    public static final int PLAYER_DAMAGE_MULT = 5;
    public static final int COMBAT_Y = 100;
    public static final int COMBAT_X = 100;

    Main program;
    Player player;
    Enemy enemy;
    RowCol start, end;
    BoardDisplay boardDisplay;
    TilePathDisplay moveListDisplay;
    CombatDisplay combatDisplay;
    ScoreDisplay scoreDisplay;

    Score score;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    boolean canMove = true;
    Stack<RowCol> moveList = new Stack<RowCol>();

    public Game(Main program){
        this.program = program;
        this.score = new Score("", 0);
        this.player = new Player(null);
        this.enemy = new Enemy(null);

        this.boardDisplay = new BoardDisplay(program, board); 
        this.moveListDisplay = new TilePathDisplay(program, moveList); 
        this.combatDisplay = new CombatDisplay(program, player, enemy); 
        this.scoreDisplay = new ScoreDisplay(program, score);


    }

    public void mousePressed(MouseEvent e) {
        if(isInBoard(e.getX(), e.getY()) && canMove){ 
            start = getTileAt(e.getX(),e.getY()).getPosition();
            moveList.push(start);
        }
    }
    public void mouseDragged(MouseEvent e) {

        if(isInBoard(e.getX(), e.getY()) && start != null) {
            end = getTileAt(e.getX(),e.getY()).getPosition(); 
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
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(canMove && moveList.size() > 1) boardStep();
        moveList.clear();
        start = null;
    }

    private void boardStep() {
        new Timer().schedule(
                new TimerTask(){
                    public void run(){
                        if(board.step()){

                            if(!board.getMatches().isEmpty()) matchEffect(board.getMatches().peek());

                            boardStep(); 
                            canMove = false;

                        } else {

                            if(!checkWinFight()) {
                                enemy.attack(player, ENEMY_DAMAGE);
                                if(checkLoseGame()) {
                                    // TODO change to actual game over
                                    System.out.println("GAME OVER");
                                }
                            } else nextFight();

                            canMove = true;
                        }
                    }
                }
        ,TILE_MOVE_DELAY);
    }

    public void showContents(){
        program.addDisplay(boardDisplay, BOARD_X, BOARD_Y);
        program.addDisplay(moveListDisplay, BOARD_X, BOARD_Y);
        program.addDisplay(combatDisplay, COMBAT_X, COMBAT_Y);
        program.addDisplay(scoreDisplay, SCORE_X, SCORE_Y);
    }

    public void hideContents(){
        program.removeDisplay(boardDisplay);
        program.removeDisplay(moveListDisplay);
        program.removeDisplay(combatDisplay);
        program.removeDisplay(scoreDisplay);
    }

    public boolean checkWinFight() {
        return enemy.getHp() <= 0;
    }

    public boolean checkLoseGame() {
        return player.getHp() <= 0;
    }

    public void nextFight() {
        // TODO change to actual generation
        System.out.println("NEXT FIGHT!");
        enemy = new Enemy(null);
        combatDisplay.updateEnemy(enemy);
    }

    public void playerTurn() {
        // TODO implement
    }
    
    public void saveScore(int Score) {
        // TODO implement
    }

    // Helpers 

    private boolean isInBoard(int x, int y){
        return (x > BOARD_X && x < BOARD_X+TILE_SIZE*NUM_COLS && y > BOARD_Y && y < BOARD_Y+TILE_SIZE*NUM_ROWS);
    }

    private void matchEffect(Match m) {
        player.attack(enemy, m.size()*PLAYER_DAMAGE_MULT);
        System.out.println(m.getType()+" size: "+m.size());
        score.setScore(score.getScore() + m.size());
    }

    private Tile getTileAt(int x, int y){
        if(!isInBoard(x,y)) return null;
        int row = (int)((y-BOARD_Y)/TILE_SIZE);
        int col = (int)((x-BOARD_X)/TILE_SIZE);
        return board.getTiles()[row][col];
    }

}
