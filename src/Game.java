//Alex and Tom's Territory
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import acm.graphics.GLabel;
import acm.io.IODialog;
import acm.util.Animator;

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
    public static final int PROJECTILE_SPEED = 3;

    public static final int HEALTH_SCALE = 3;
    public static final int ATTACK_SCALE = 1;
    public static final int DEFENSE_SCALE = 0;
    public static final double ENEMY_GROWTH = 1.1;
    public static final int FRAME_TIME = 16;

    public static final int BASE_HP  = 15;
    public static final int BASE_ATK = 5;
    public static final int BASE_DEF = 0;
  
    private GLabel pause;

    Main program;
    Player player;
    Enemy enemy;
    RowCol start, end;
    BoardDisplay boardDisplay;
    TilePathDisplay moveListDisplay;
    CombatDisplay combatDisplay;
    ScoreDisplay scoreDisplay;
    IODialog dialog;
    

    boolean canMove = true;

    Animator animator = new Animator(){
        public void run(){
            while(!game_over){
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
    int currentCombo = 0;
    Board board = new Board(NUM_ROWS, NUM_COLS);
    Stack<RowCol> moveList = new Stack<RowCol>();
    int level = 1;
    boolean game_over = false;
    boolean abilityUsed = false;

    public Game(Main program, Player player){
        this.program = program;
        this.score = new Score("",0);
        this.player = player;
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
        
        pause = new GLabel("ESC:Pause", 310, 20);
        pause.setColor(Color.BLACK);
        pause.setFont("Arial-15");

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
        if(boardDisplay.isInBoard(e.getX(), e.getY()) && moveList.isEmpty() && canMove){ 
            start = boardDisplay.getTileAt(e.getX(),e.getY()).getPosition();
            moveList.push(start);
        } else if (canMove){
        	String abilityUse = combatDisplay.useAbility(e.getX(),e.getY());
        	if(abilityUse != null) {
        		this.abilityUsed = true;
        		System.out.println(abilityUse);
        		boardStep();
        	}
        }
    }
    public void mouseDragged(MouseEvent e) {
    	mouseMoved(e);
        if(boardDisplay.isInBoard(e.getX(), e.getY()) && start != null) {
            end = boardDisplay.getTileAt(e.getX(),e.getY()).getPosition(); 
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

    public void mouseMoved(MouseEvent e) {
    	// boardDisplay.setCurrentMousePosition(e.getX(), e.getY()-BOARD_Y);
    }
    @Override
        public void keyReleased(KeyEvent ke) 
        {
     
            if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                //code to execute if escape is pressed
            	program.switchToScreen(new Pause(program, this));
            	Sound.menuMusic.stop();
            	
            }
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
                        	boardDisplay.removeMultipliers();
                        	currentCombo = 0;
                            if(!checkWinFight() && !abilityUsed) {

                                combatDisplay.addProjectile(enemy,-PROJECTILE_SPEED, Color.WHITE);
                                enemy.setCurrentAnimation(AnimationState.ATTACK);

                                new Timer().schedule( new TimerTask(){
                                    public void run(){

                                        enemy.attack(player, enemy.getAttack());
                                        combatDisplay.addEffect(enemy, player, Color.WHITE);

                                        enemy.setCurrentAnimation(AnimationState.IDLE);
                                        combatDisplay.addEffect(enemy,player, Color.WHITE);

                                        if(checkLoseGame() && !game_over) 
                                        	endGame();
                                    }}, CombatDisplay.getTimeToDisplayProjectile(-PROJECTILE_SPEED));

                            }

                            new Timer().schedule(new TimerTask(){
                                public void run(){
                                    if(checkWinFight()) nextFight();
                                    canMove = true;
                                }}, CombatDisplay.getTimeToDisplayProjectile(PROJECTILE_SPEED));

                            player.changeAnimationAfter(150,AnimationState.IDLE);
                        	abilityUsed = false;
                        }
                    }
                }
        ,TILE_MOVE_DELAY);
    }

    private void endGame(){
        game_over = true;
        player.setCurrentAnimation(AnimationState.DEATH);
        combatDisplay.update();
        animator.stopAction();
        String name = "";
        while(name.equals("")) name = dialog.readLine("\tGame Over!\nEnter your name.");
        saveScore(name, score.getScore());
        Sound.fighting.stop();
        Sound.ending.play();
        program.switchToScreen(new ScoreScreen(program));
    }

    public void showContents(){
        program.add(boardDisplay);
        program.add(combatDisplay);
        program.add(scoreDisplay);
        program.add(moveListDisplay);
        program.add(pause);
    }

    public void hideContents(){
        program.remove(boardDisplay);
        program.remove(combatDisplay);
        program.remove(scoreDisplay);
        program.remove(moveListDisplay);
        program.remove(pause);
    }

    private boolean checkWinFight() {
        return enemy.getHp() <= 0;
    }

    private boolean checkLoseGame() {
        return player.getHp() <= 0;
    }

    private void nextFight() {
    	if(level%5==0) {
        	player.setDefense(player.getDefense() + level/5);
    	}
    	score.setScore(score.getScore() + level*10);
        enemy.setCurrentAnimation(AnimationState.DEATH);
        program.pause(800);
        System.out.println("NEXT FIGHT!");
        level++;
        enemy = generateEnemy(level);
        combatDisplay.updateEnemy(enemy);
    }

    private void playerScore(String name, int Score) {
        //Creates a new high score object, then adds it
        HighscoreList hm = new HighscoreList();
        hm.addScore(name, Score);
    }

    private void saveScore(String userName, int score){
        HighscoreList hm = new HighscoreList();
        hm.addScore(userName, score);
        System.out.println(hm.printScores());
    }

    // Helpers 

    private void matchEffect(Match m) {

        switch(m.getType()){
            case PINK: // Heal
            	Sound.healing.play();
                combatDisplay.addEffect(player, player, TileType.getColor(m.getType()));
                break;
            case YELLOW:
            	Sound.lightAttack.play();
            	break;
            case BLUE:
            	Sound.waterAttack.play();
            	break;
            case RED:
            	Sound.fireAttack.play();
            	break;
            case GREEN:
            	Sound.rockAttack.play();
            	break;
        }
        attackAnimations(m);

        score.setScore(score.getScore() + (int)(m.size()*player.getMultiplier(currentCombo)));
        System.out.println(m.getType()+" size: "+m.size() + " multiplier: x" + player.getMultiplier(currentCombo));
        boardDisplay.addMultiLabel(m, player.getMultiplier(currentCombo));
        currentCombo++;
    }

    private void attackAnimations(Match m) {
    	player.setCurrentAnimation(AnimationState.ATTACK);
        combatDisplay.addProjectile(player,PROJECTILE_SPEED, TileType.getColor(m.getType()));
        new Timer().schedule(new TimerTask(){
            public void run(){
                player.attack(enemy, m, currentCombo);
                combatDisplay.addEffect(player,enemy, TileType.getColor(m.getType()));
            }}, CombatDisplay.getTimeToDisplayProjectile(PROJECTILE_SPEED));
    }
}
