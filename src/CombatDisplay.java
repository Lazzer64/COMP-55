import java.awt.Color;
import java.awt.image.BufferedImage;

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
    public static final int UNIT_X = 3*Main.WINDOW_WIDTH/8;
    public static final int UNIT_Y = 180;
    public static final int DISTANCE = Main.WINDOW_WIDTH/4;

    Player player;
    Enemy enemy;

    HashMap<Unit, UnitInfo> unitInfo = new HashMap<Unit, UnitInfo>();
    HashMap<Animation,Integer> projectiles = new HashMap<Animation,Integer>();
    HashMap<Animation,Integer> effects = new HashMap<Animation,Integer>();

    public CombatDisplay(Player player, Enemy enemy){
        super();
        this.player = player;
        this.enemy = enemy;

        initBackground();
        initUnit(UNIT_X, UNIT_Y, player); 
        initUnit(UNIT_X+DISTANCE, UNIT_Y, enemy);
    }
    
    /**
     * Adds a projectile to be updated as part of combat field
     * @param x The x location of the projectile
     * @param y The y location of the projectile
     * @param speed The horizontal speed the projectile should travel (Positive to travel right and negative to travel left)
     * @param color The color of the projectile
     * @return The GRect that was created
     */
    public Animation addProjectile(int x, int y, int speed, BufferedImage[] animation){
        // FIXME remove projectiles when they have reached the enemy
        Animation proj = new Animation(animation, 7);
        proj.setLocation(x,y);
        proj.move(speed*2,0);

        HashMap<Animation, Integer> projs = (HashMap<Animation, Integer>) projectiles.clone();
        projs.put(proj, speed);
        
        projectiles = projs;

        addObject(proj);

        return proj;
    }
    
    public Animation addProjectile(Unit u, int speed, Color color){
        return addProjectile((int)unitInfo.get(u).animation.getX(),(int)unitInfo.get(u).animation.getY(), speed,u.getAttackAnimation(color));
    }

    public Animation addEffect(int x, int y, BufferedImage[] animation) {
    	Animation effect = new Animation(animation, 12);
    	effect.setLocation(x,y);
    	
    	HashMap<Animation, Integer> effectz = (HashMap<Animation, Integer>) effects.clone();
        effectz.put(effect, 0);
    	
        effects = effectz;
        
    	addObject(effect);
    	
    	return effect;
    }
    
    public Animation addEffect(Unit caster,Unit target, Color color) {
    	return addEffect((int) unitInfo.get(target).animation.getX(),(int)unitInfo.get(target).animation.getY(), caster.getAttackExplosionAnimation(color));
    }
    
    public static int getTimeToDisplayProjectile(int speed) {
    	int delay = 0;
    	delay += ((DISTANCE)/Math.abs(speed))*Game.FRAME_TIME;
    	//TODO add delay for effects time
        // System.out.println(delay);
    	return delay;
    }
    
    public static int getTimeToDisplayEffect(Animation effect) {
    	int delay = 0;
    	delay += effect.getFrameDelay()*effect.getNumberOfFrames()*Game.FRAME_TIME;
    	delay -= 1;
    	return delay;
    }
    
    public void updateEnemy(Enemy x){

        UnitInfo info = unitInfo.get(enemy);
        unitInfo.remove(enemy);

        info.animation.playAnimation(x.getAnimation(),20); 
        info.name.setLabel(x.getName());

        double nameX = info.animation.getX()-info.name.getWidth()/4;
        double nameY = info.name.getY();
        info.name.setLocation(nameX,nameY);

        unitInfo.put(x, info);

        enemy = x;
    }

    public void update(){
        for(Unit u: unitInfo.keySet()){
            updateAnimation(u);
            updateHp(u);
        }

        for(Animation p: projectiles.keySet()) {
        	p.update();
            p.move(projectiles.get(p),0);
            boolean lower = UNIT_X + DISTANCE < p.getX() ;
            boolean higher = UNIT_X-10 > p.getX();
            if(lower || higher) p.setVisible(false);
        }
        
        for(Animation e: effects.keySet()) {
        	e.update();
        	e.move(0, 0);
        }
    }

    public void initBackground() {
    	GImage background = new GImage("SpriteSheets/background.gif");
    	background.setLocation(0,25);
    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT/3);
    	addObject(background);
    }
    
    public GLabel initName(double x, double y, Unit unit){
        GLabel l = new GLabel(unit.getName());
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
