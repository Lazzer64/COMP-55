//Alex and Tom's Territory
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.*;
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
    public static final int ENEMY_DAMAGE = 30;
    public static final int PLAYER_DAMAGE_MULT = 5;
    public static final int COMBAT_Y = 0;
    public static final int COMBAT_X = 0;
    public static final int HEAL_MOD = 3;
    public static final double GAME_SPEED = 0.82; // Value from 0.0 to 1.0

    Main program;
    Player player;
    Enemy enemy;
    RowCol start, end;
    BoardDisplay boardDisplay;
    TilePathDisplay moveListDisplay;
    CombatDisplay combatDisplay;
    ScoreDisplay scoreDisplay;

    Animator animator = new Animator(){
            public void run(){
                while(true){
                    combatDisplay.update();
                    boardDisplay.update();
                    scoreDisplay.update();
                    scoreDisplay.update();
                    moveListDisplay.update();
                    delay();
                }
            }
        };
    

    Score score;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    boolean canMove = true;
    Stack<RowCol> moveList = new Stack<RowCol>();

    public Game(Main program){
        this.program = program;
        this.score = new Score("",0);
        this.player = new Player();
        this.enemy = new Enemy(UnitType.randomEnemy());

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
        System.out.println(animator.getAnimatorState());
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



    public void update() {
        // new Timer().scheduleAtFixedRate(new TimerTask(){
        //     public void run(){
        //         combatDisplay.update();
        //         boardDisplay.update();
        //         scoreDisplay.update();
        //         scoreDisplay.update();
        //         moveListDisplay.update();
        //     }
        // } ,0, 20);
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
                                program.add(combatDisplay.addProjectile(enemy,-10, Color.WHITE));
                                enemy.playAnimationFor(500, AnimationState.ATTACK, AnimationState.IDLE);
                                if(checkLoseGame()) {
                                    // TODO change to actual game over
                                    System.out.println("GAME OVER");
                                    // FIXME replace when done
                                    program.switchToScreen(new GraphicsPane(){
                                        public void showContents(){
                                            GLabel label = new GLabel("GAME OVER!");
                                            GLabel label2 = new GLabel("(TEMP) ENTER NAME IN CONSOLE");
                                            label.setLocation(100,100);
                                            label2.setLocation(100,200);
                                            program.add(label2);
                                            program.add(label);
                                        }
                                        public void hideContents(){
                                        }
                                    });
                                    saveScore(score.getScore());
                                    
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
        for(GObject o : boardDisplay.getObjects()) program.add(o);
        for(GObject o : combatDisplay.getObjects()) program.add(o); 
        for(GObject o : scoreDisplay.getObjects()) program.add(o); 
        for(GObject o : moveListDisplay.getObjects()) program.add(o);
    }

    public void hideContents(){
        for(GObject o : boardDisplay.getObjects()) program.remove(o);
        for(GObject o : combatDisplay.getObjects()) program.remove(o); 
        for(GObject o : scoreDisplay.getObjects()) program.remove(o); 
        for(GObject o : moveListDisplay.getObjects()) program.remove(o);
    }

    public boolean checkWinFight() {
        return enemy.getHp() <= 0;
    }

    public boolean checkLoseGame() {
        return player.getHp() <= 0;
    }

    public void nextFight() {
        // TODO change to actual generation
        enemy.setCurrentAnimation(AnimationState.DEATH);
        program.pause(800);
        System.out.println("NEXT FIGHT!");
        enemy = new Enemy(UnitType.randomEnemy());
        combatDisplay.updateEnemy(enemy);
    }

    public void playerScore(String name, int Score) {
    	//Creates a new high score object, then adds it
    	HighscoreList hm = new HighscoreList();
    	hm.addScore(name, Score);
    }
    
    public void saveScore(int score){
        System.out.println("Enter name for high-score list");
        Scanner userNameScanner = new Scanner(System.in);
        String userName = userNameScanner.nextLine();

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
                player.attack(enemy, m.size()*PLAYER_DAMAGE_MULT);

                program.add(combatDisplay.addProjectile(player,10, TileType.getColor(m.getType())));

                System.out.println(m.getType()+" size: "+m.size());
                score.setScore(score.getScore() + m.size());
        }

    }

    private Tile getTileAt(int x, int y){
        if(!isInBoard(x,y)) return null;
        int row = (int)((y-BOARD_Y)/TILE_SIZE);
        int col = (int)((x-BOARD_X)/TILE_SIZE);
        return board.getTiles()[row][col];
    }

}
