import java.awt.Color;
import acm.graphics.*;

public class CombatDisplay extends Display{

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final int HP_BAR_BUFFER = 0;
    public static final int HP_BAR_HEIGHT = 10;
    public static final int HP_BAR_WIDTH = 50;
    public static final int DISTANCE = Main.WINDOW_WIDTH/4;

    Player player;
    Enemy enemy;
    GRect playerHp, enemyHp;

    public CombatDisplay(Player player, Enemy enemy){
        super();
        this.player = player;
        this.enemy = enemy;

        initUnit(0, 0, player);
        playerHp = initHp(player, 0, 32+HP_BAR_BUFFER);

        initUnit(DISTANCE, 0, enemy);
        enemyHp = initHp(enemy, DISTANCE, 32+HP_BAR_BUFFER);
    }

    public void updateEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public void update(){
        for(Animation a: animations) a.update();
        updateHp(player, playerHp);
        updateHp(enemy, enemyHp);
    }

    public void updateHp(Unit unit, GRect bar){
        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();
        double percentHp = 1.0*unitHp/unitMaxHp;
        bar.setSize(HP_BAR_WIDTH*percentHp,HP_BAR_HEIGHT);
    }

    public GRect initHp(Unit unit, double x, double y){

        x -= HP_BAR_WIDTH/2;

        GRect hpMax = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(HP_BAR_EMPTY_COLOR);

        GRect hp = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(HP_BAR_FILLED_COLOR);

        addObject(hpMax);
        addObject(hp);
        return hp;
    }

    public void initUnit(double x, double y, Unit unit) {
        Animation u = new Animation(unit.getAnimation(),20);
        u.setLocation(x-u.getWidth()/2,y-u.getHeight()/2);
        addAnimation(u);
    }
}
