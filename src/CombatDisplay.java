import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.*;
import java.util.HashMap;

class UnitInfo {
    Animation animation;
    GRect hpBar;
    GLabel hpAmount;
    GRect energyBar;
    GLabel energyAmount;
    GLabel name;
}

public class CombatDisplay extends Display{

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final Color ENERGY_BAR_FILLED_COLOR = Color.YELLOW;
    public static final Color ENERGY_BAR_EMPTY_COLOR = Color.BLACK;
    public static final int HP_BAR_HEIGHT = 10;
    public static final int HP_BAR_WIDTH = 50;
    public static final int UNIT_X = 3*Main.WINDOW_WIDTH/8;
    public static final int UNIT_Y = 155;
    public static final int ABILITY_Y = Main.WINDOW_HEIGHT/3 + 25;
    public static final int ABILITY_X = 0;
    public static final int ABILITY_X_OFFSET = 25;
    public static final int DISTANCE = Main.WINDOW_WIDTH/4;
    public static final Font HP_FONT = new Font("Arial",Font.BOLD,12);

    public static final GImage BACKGROUND_IMAGE = new GImage("SpriteSheets/background.gif");

    private GLabel[] abilityLabels;
    
    Player player;
    Enemy enemy;

    HashMap<Unit, UnitInfo> unitInfo = new HashMap<Unit, UnitInfo>();

    public CombatDisplay(Player player, Enemy enemy){
        super();
        this.player = player;
        this.enemy = enemy;

        initBackground();
        initUnit(UNIT_X, UNIT_Y, player); 
        initUnit(UNIT_X+DISTANCE, UNIT_Y, enemy);
        initAbilities();
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

        Animation anim = new Animation(animation, 7);
        anim.setLocation(x,y);
        Projectile proj = new Projectile(anim, speed, DISTANCE);
        
        addUpdatable(proj);
        addObject(anim);

        return anim;
    }
    
    public Animation addProjectile(Unit u, int speed, Color color){
        return addProjectile((int)unitInfo.get(u).animation.getX(),(int)unitInfo.get(u).animation.getY(), speed,u.getAttackAnimation(color));
    }

    public Animation addEffect(int x, int y, BufferedImage[] animation) {
    	Animation effect = new Animation(animation, 12);
    	effect.setLocation(x,y);
    	
        addUpdatable(effect);
        addObject(effect); 

        new Timer().schedule( new TimerTask(){
            public void run(){
                removeUpdatable(effect);
                removeObject(effect);
            }}, getTimeToDisplayEffect(effect));

    	return effect;
    }
    
    public Animation addEffect(Unit caster,Unit target, Color color) {
    	return addEffect((int) unitInfo.get(target).animation.getX(),(int)unitInfo.get(target).animation.getY(), caster.getAttackExplosionAnimation(color));
    }
    
    public static int getTimeToDisplayProjectile(int speed) {
    	int delay = 0;
    	delay += ((DISTANCE)/Math.abs(speed))*Game.FRAME_TIME;
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
            if(u.equals(player)) updateEnergy(player);
        }
        super.update();
    }

    private void initBackground() {
    	GImage background = BACKGROUND_IMAGE;
    	background.setLocation(0,25);
    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT/3);
    	addObject(background);
     }
    
    private void initAbilities() {
    	abilityLabels = new GLabel[player.getNumAbilities()];
    	Ability[] abilities = player.getAbilities();
    	for(int i = 0; i < abilityLabels.length;i++) {
    		abilityLabels[i] = new GLabel(abilities[i].getName(),ABILITY_X+i*ABILITY_X_OFFSET,ABILITY_Y);
    		abilityLabels[i].setFont("Arial-18");
    		abilityLabels[i].move(0, abilityLabels[i].getHeight());
    		if(i > 0) abilityLabels[i].move(abilityLabels[i-1].getWidth()*i, 0);
    		addObject(abilityLabels[i]);
    	}
    }
    
    private GLabel initName(double x, double y, Unit unit){
        GLabel l = new GLabel(unit.getName());
        l.setLocation(x-l.getWidth()/2,y);
        l.setColor(Color.ORANGE);
        addObject(l);
        return l;
    }
    
    public String useAbility(int x, int y) {
    	for(GLabel ability : abilityLabels) {
    		if(ability.contains(x,y)) {
    			return player.getAbility(ability.getLabel()).use(player, enemy);
    		}
    	}
    	return null;
    }

    private void updateAnimation(Unit unit){
        Animation anim = unitInfo.get(unit).animation;
        anim.update();
        if(!anim.equals(unit.getAnimation())) {
            anim.playAnimation(unit.getAnimation(),20);
        } 

    }

    private void updateHp(Unit unit){
        GRect bar = unitInfo.get(unit).hpBar;
        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();
        double percentHp = 1.0*unitHp/unitMaxHp;
        bar.setSize(HP_BAR_WIDTH*percentHp,HP_BAR_HEIGHT);
        GLabel hp = unitInfo.get(unit).hpAmount;
        hp.setLabel("" + unitHp + "/" + unitMaxHp);
    }

    private GRect initHp(double x, double y, Unit unit){

        x -= HP_BAR_WIDTH/2;

        GRect hpMax = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(HP_BAR_EMPTY_COLOR);

        GRect hp = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(HP_BAR_FILLED_COLOR);

        GLabel hpTitle = new GLabel("HP:");
        hpTitle.setLocation(x-hpTitle.getWidth(),y + HP_BAR_HEIGHT);
        hpTitle.setFont(HP_FONT);
        hpTitle.setColor(HP_BAR_FILLED_COLOR);
        
        addObject(hpMax);
        addObject(hp);
        addObject(hpTitle);
        
        return hp;
    }
    
    private void updateEnergy(Player unit){
        GRect bar = unitInfo.get(unit).energyBar;
        int unitEnergy = unit.getEnergy();
        int unitMaxEnergy = unit.getMaxEnergy();
        double percentEnergy = 1.0*unitEnergy/unitMaxEnergy;
        bar.setSize(HP_BAR_WIDTH*percentEnergy,HP_BAR_HEIGHT);
        
        GLabel energy = unitInfo.get(unit).energyAmount;
        energy.setLabel("" + unitEnergy + "e/" + unitMaxEnergy + "e");
    }
    
    private GRect initEnergy(double x, double y, Player unit){

        x -= HP_BAR_WIDTH/2;

        GRect energyMax = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        energyMax.setFilled(true);
        energyMax.setColor(ENERGY_BAR_EMPTY_COLOR);

        GRect energy = new GRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
        energy.setFilled(true);
        energy.setColor(ENERGY_BAR_FILLED_COLOR);

        GLabel energyTitle = new GLabel("Energy:");
        energyTitle.setLocation(x-energyTitle.getWidth(),y + HP_BAR_HEIGHT);
        energyTitle.setFont(HP_FONT);
        energyTitle.setColor(ENERGY_BAR_FILLED_COLOR);
        
        addObject(energyMax);
        addObject(energy);
        addObject(energyTitle);
        
        return energy;
    }

    private Animation initAnimation(double x, double y, Unit unit){
        Animation anim = new Animation(unit.getAnimation(),20);
        anim.setLocation(x-anim.getWidth()/2,y-anim.getHeight()/2);
        addUpdatable(anim);
        addObject(anim);
        return anim;
    }
    
    private GLabel initHpAmount(double x, double y, Unit unit) {
    	GLabel hpValue = new GLabel("0/100");
        hpValue.setLocation(x - hpValue.getWidth()/2,y);
        hpValue.setFont(HP_FONT);
        hpValue.setColor(Color.BLACK);
        addObject(hpValue);
        return hpValue;
    }
    
    private GLabel initEnergyAmount(double x, double y, Unit unit) {
    	GLabel energyValue = new GLabel("0e/100e");
        energyValue.setLocation(x - energyValue.getWidth()/2,y);
        energyValue.setFont(HP_FONT);
        energyValue.setColor(Color.RED);
        addObject(energyValue);
        return energyValue;
    }

    private void initUnit(double x, double y, Unit unit) {

        UnitInfo info = new UnitInfo();
        unitInfo.put(unit, info);

        info.animation = initAnimation(x,y,unit);
        info.hpBar = initHp(x,y+info.animation.getHeight()/2,unit);
        info.hpAmount = initHpAmount(x,y+info.animation.getHeight()/2+info.hpBar.getHeight(),unit);
        if(unit.equals(player)) {
        	info.energyBar = initEnergy(x,y+info.animation.getHeight()/2+info.hpBar.getHeight() + 5,player);
        	info.energyAmount = initEnergyAmount(x,y+info.animation.getHeight()/2+(2*info.hpBar.getHeight()) + 5,player);
        }
        info.name = initName(x,y-info.animation.getHeight()/2,unit);
        
    }
}
