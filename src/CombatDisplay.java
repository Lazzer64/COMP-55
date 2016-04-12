import java.awt.Color;
import acm.graphics.*;
import java.util.HashMap;

class UnitInfo {
    Animation animation;
    GRect hpBar;
    GLabel name;
}

public class CombatDisplay extends Display{

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final int HP_BAR_HEIGHT = 10;
    public static final int HP_BAR_WIDTH = 50;
    public static final int DISTANCE = Main.WINDOW_WIDTH/4;

    Player player;
    Enemy enemy;

    HashMap<Unit, UnitInfo> unitInfo = new HashMap<Unit, UnitInfo>();
    HashMap<GRect,Integer> projectiles = new HashMap<GRect,Integer>();

    public CombatDisplay(Player player, Enemy enemy){
        super();
        this.player = player;
        this.enemy = enemy;

        initBackground();
        initUnit(3*Main.WINDOW_WIDTH/8, 180, player); 
        initUnit(3*Main.WINDOW_WIDTH/8+DISTANCE, 180, enemy);
    }

    /**
     * Adds a projectile to be updated as part of combat field
     * @param x The x location of the projectile
     * @param y The y location of the projectile
     * @param speed The horizontal speed the projectile should travel (Positive to travel right and negative to travel left)
     * @param color The color of the projectile
     * @return The GRect that was created
     */
    public GRect addProjectile(int x, int y, int speed, Color color){
        // FIXME remove projectiles when they have reached the enemy
        GRect proj = new GRect(x,y,10,10);
        proj.setFilled(true);
        proj.setColor(color);

        HashMap<GRect, Integer> projs = (HashMap<GRect, Integer>) projectiles.clone();
        projs.put(proj, speed);
        
        projectiles = projs;

        addObject(proj);

        return proj;
    }

    public void updateEnemy(Enemy x){

        UnitInfo info = unitInfo.get(enemy);
        unitInfo.remove(enemy);

        info.animation.playAnimation(x.getAnimation(),20); 
        info.name.setLabel(x.getType().toString());

        unitInfo.put(x, info);

        enemy = x;
    }

    public void update(){
        for(Unit u: unitInfo.keySet()){
            updateAnimation(u);
            updateHp(u);
        }

        for(GRect p: projectiles.keySet()) {
            p.move(projectiles.get(p),0);
            if(p.getX() > x_adj + DISTANCE || p.getX() < x_adj) p.setVisible(false);
        }
    }

    public void initBackground() {
    	GImage background = new GImage("SpriteSheets/background.gif");
    	background.setLocation(0,25);
    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT/3);
    	addObject(background);
    }
    
    public GLabel initName(double x, double y, Unit unit){
        GLabel l = new GLabel(unit.getType().toString());
        l.setLocation(x-l.getWidth()/2,y);
        l.setColor(Color.ORANGE);
        addObject(l);
        return l;
    }

    public void updateAnimation(Unit unit){
        Animation anim = unitInfo.get(unit).animation;
        anim.update();
        if(!anim.equals(unit.getAnimation())) {
            anim.playAnimation(unit.getAnimation(),20);
        } 

    }

    public void updateHp(Unit unit){
        GRect bar = unitInfo.get(unit).hpBar;
        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();
        double percentHp = 1.0*unitHp/unitMaxHp;
        bar.setSize(HP_BAR_WIDTH*percentHp,HP_BAR_HEIGHT);
    }

    public GRect initHp(double x, double y, Unit unit){

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

    public Animation initAnimation(double x, double y, Unit unit){
        Animation anim = new Animation(unit.getAnimation(),20);
        anim.setLocation(x-anim.getWidth()/2,y-anim.getHeight()/2);
        addAnimation(anim);
        return anim;
    }

    public void initUnit(double x, double y, Unit unit) {

        UnitInfo info = new UnitInfo();
        unitInfo.put(unit, info);

        info.animation = initAnimation(x,y,unit);
        info.hpBar = initHp(x,y+info.animation.getHeight(),unit);
        info.name = initName(x,y-info.animation.getHeight(),unit);
        
    }
}
