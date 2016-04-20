//Alex and Tom's Territory
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.io.IODialog;
import acm.util.Animator;
import acm.graphics.*;

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
    public static final int COMBAT_Y = 0;
    public static final int COMBAT_X = 0;
    public static final double GAME_SPEED = 0.82; // Value from 0.0 to 1.0

    public static final int HEALTH_SCALE = 3;
    public static final int ATTACK_SCALE = 1;
    public static final int DEFENSE_SCALE = 0;
    public static final double ENEMY_GROWTH = 1.1;
    public static final int FRAME_TIME = 16;

    public static final int BASE_HP  = 15;
    public static final int BASE_ATK = 5;
    public static final int BASE_DEF = 0;

    public static final int PLAYER_HEALTH = 100;
    public static final int PLAYER_ATTACK = 2;
    public static final int PLAYER_DEFENSE = 3;
    public static final int HEAL_MOD = 1;
 

    Main program;
    Player player;
    Enemy enemy;
    RowCol start, end;
    BoardDisplay boardDisplay;
    TilePathDisplay moveListDisplay;
    CombatDisplay combatDisplay;
    ScoreDisplay scoreDisplay;
    IODialog dialog;

    Animator animator = new Animator(){
        public void run(){
            while(true){
                combatDisplay.update();
                boardDisplay.update();
                scoreDisplay.update();
                scoreDisplay.update();
                moveListDisplay.update();
                program.pause(FRAME_TIME);
            }
        }
    };


    Score score;
    double currentMultiplier = 1;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    boolean canMove = true;
    Stack<RowCol> moveList = new Stack<RowCol>();
    int level = 1;

    public Game(Main program){
        this.program = program;
        this.score = new Score("",0);
        this.player = new Player(PLAYER_HEALTH, PLAYER_ATTACK, PLAYER_DEFENSE);
        this.enemy = generateEnemy(level);
        this.dialog = new IODialog(program);

        this.boardDisplay = new BoardDisplay(board); 
        this.boardDisplay.setLocation(BOARD_X, BOARD_Y);

        this.combatDisplay = new CombatDisplay(player, enemy);
        this.combatDisplay.setLocation(COMBAT_X, COMBAT_Y);

        this.scoreDisplay = new ScoreDisplay(score);
        this.scoreDisplay.setLocation(SCORE_X, SCORE_Y);

        this.moveListDisplay = new TilePathDisplay(moveList, NUM_ALLOWED_MOVES);
        this.moveListDisplay.setLocation(BOARD_X, BOARD_Y);

        animator.start();
        animator.setSpeed(GAME_SPEED);
    }

    private Enemy generateEnemy(int level) {

        double scale = Math.pow(level-1, ENEMY_GROWTH);

        int hp  =  BASE_HP  + (int) (HEALTH_SCALE  * scale);
        int atk =  BASE_ATK + (int) (ATTACK_SCALE  * scale);
        int def =  BASE_DEF + (int) (DEFENSE_SCALE * scale);

        if(level%5 == 0) return new Boss((int)2*hp, (int)2*atk, (int)2*def);
        return new Enemy("Grunt lvl: "+level, hp, atk, def);
    }

    public void mousePressed(MouseEvent e) {
        if(isInBoard(e.getX(), e.getY()) && moveList.isEmpty() && canMove){ 
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
                        	currentMultiplier = 1;
                            if(!checkWinFight()) {

                                combatDisplay.addProjectile(enemy,-3, Color.WHITE);
                                enemy.setCurrentAnimation(AnimationState.ATTACK);

                                new Timer().schedule( new TimerTask(){
                                    public void run(){
                                        enemy.attack(player, enemy.getAttack());
                                        combatDisplay.addEffect(enemy, player, Color.WHITE);
                                        enemy.setCurrentAnimation(AnimationState.IDLE);
                                        combatDisplay.addEffect(enemy,player, Color.WHITE);
                                    }}, combatDisplay.getTimeToDisplayProjectile(-3));

                                if(checkLoseGame()) {
                                    player.setCurrentAnimation(AnimationState.DEATH);
                                    String name = dialog.readLine("\tGame Over!\nEnter your name.");
                                    saveScore(name, score.getScore());
                                    program.switchToScreen(new ScoreScreen(program));
                                }
                            } else nextFight();
                            canMove = true;
                            player.changeAnimationAfter(150,AnimationState.IDLE);
                        }
                    }
                }
        ,TILE_MOVE_DELAY);
    }

    public void showContents(){
        program.add(boardDisplay);
        program.add(combatDisplay);
        program.add(scoreDisplay);
        program.add(moveListDisplay);
    }

    public void hideContents(){
        program.remove(boardDisplay);
        program.remove(combatDisplay);
        program.remove(scoreDisplay);
        program.remove(moveListDisplay);
    }

    public boolean checkWinFight() {
        return enemy.getHp() <= 0;
    }

    public boolean checkLoseGame() {
        return player.getHp() <= 0;
    }

    public void nextFight() {
        // TODO change to actual generation
    	score.setScore(score.getScore() + level*10);
        enemy.setCurrentAnimation(AnimationState.DEATH);
        program.pause(800);
        System.out.println("NEXT FIGHT!");
        level++;
        enemy = generateEnemy(level);
        combatDisplay.updateEnemy(enemy);
    }

    public void playerScore(String name, int Score) {
        //Creates a new high score object, then adds it
        HighscoreList hm = new HighscoreList();
        hm.addScore(name, Score);
    }

    public void saveScore(String userName, int score){
        HighscoreList hm = new HighscoreList();
        hm.addScore(userName, score);
        System.out.println(hm.printScores());
    }

    // Helpers 

    private boolean isInBoard(int x, int y){
        return (x > BOARD_X && x < BOARD_X+TILE_SIZE*NUM_COLS && y > BOARD_Y && y < BOARD_Y+TILE_SIZE*NUM_ROWS);
    }

    private void matchEffect(Match m) {

        switch(m.getType()){
            case PINK: // Heal
                player.heal(m.size()*HEAL_MOD);
                break;
            default: // Generic damage
                player.setCurrentAnimation(AnimationState.ATTACK);
                combatDisplay.addProjectile(player,3, TileType.getColor(m.getType()));

                new Timer().schedule( new TimerTask(){
                    public void run(){
                        player.attack(enemy, m.size() * player.getAttack());
                        combatDisplay.addEffect(player,enemy, TileType.getColor(m.getType()));
                    }}, combatDisplay.getTimeToDisplayProjectile(3));

                System.out.println(m.getType()+" size: "+m.size());
                score.setScore(score.getScore() + (int)(m.size()*currentMultiplier));
                currentMultiplier += 0.5;
        }

    }

    private Tile getTileAt(int x, int y){
        if(!isInBoard(x,y)) return null;
        int row = (int)((y-BOARD_Y)/TILE_SIZE);
        int col = (int)((x-BOARD_X)/TILE_SIZE);
        return board.getTiles()[row][col];
    }

}
