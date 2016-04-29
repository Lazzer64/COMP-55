import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import acm.graphics.*;
import java.util.HashMap;
import java.util.Set;

class UnitInfo {
    Animation animation;
    GRect hpBar;
    GLabel hpAmount;
    GRect energyBar;
    GLabel energyAmount;
    GLabel name;
    GLabel attack;
    GLabel defense;
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
    public static final GImage PLAYER_ATTACK_SYMBOL = new GImage("Spritesheets/weapon_icon.png");
    public static final GImage ENEMY_ATTACK_SYMBOL = new GImage("Spritesheets/weapon_icon.png");
    public static final GImage PLAYER_DEFENSE_SYMBOL = new GImage("Spritesheets/armor_icon.png");
    public static final GImage ENEMY_DEFENSE_SYMBOL = new GImage("Spritesheets/armor_icon.png");
    
    public static final GImage NUKE_BUTTON_ACTIVE = new GImage("Spritesheets/nukeactive.png");
    public static final GImage NUKE_BUTTON_INACTIVE = new GImage("Spritesheets/nukeinactive.png");
    public static final GImage FULLHEAL_BUTTON_ACTIVE = new GImage("Spritesheets/fullhealactive.png");
    public static final GImage FULLHEAL_BUTTON_INACTIVE = new GImage("Spritesheets/fullhealinactive.png");
    public static final GImage LEVELUP_BUTTON_ACTIVE = new GImage("Spritesheets/levelupactive.png");
    public static final GImage LEVELUP_BUTTON_INACTIVE = new GImage("Spritesheets/levelupinactive.png");
    

    HashMap<GImage, String> abilityButtons = new HashMap<GImage, String>();
    
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

        HashMap<Unit, UnitInfo> uInfos = (HashMap<Unit, UnitInfo>) unitInfo.clone();

        UnitInfo info = uInfos.get(enemy);
        uInfos.remove(enemy);

        info.animation.playAnimation(x.getAnimation(),20); 
        info.name.setLabel(x.getName());

        double nameX = info.animation.getX()-info.name.getWidth()/4;
        double nameY = info.name.getY();
        info.name.setLocation(nameX,nameY);

        uInfos.put(x, info);

        enemy = x;
        unitInfo = uInfos;
    }

    public void update(){
        for(Unit u: unitInfo.keySet()){
            updateAnimation(u);
            updateHp(u);
            updateStats(u);
            if(u.equals(player)) {
            	updateEnergy(player);
            	updateAbilities(player);
            }
        }
        super.update();
    }

    public void updateStats(Unit u) {
    	GLabel attack = unitInfo.get(u).attack;
    	attack.setLabel("" + u.getAttack());
    	GLabel defense = unitInfo.get(u).defense;
    	defense.setLabel("" + u.getDefense());
    }
    
    public void updateAbilities(Player p) {
    	if(player.getAbility("Nuke").getEnergyCost()<= player.getEnergy()) {
    		NUKE_BUTTON_ACTIVE.setVisible(true);
    		NUKE_BUTTON_INACTIVE.setVisible(false);
    	} else {
    		NUKE_BUTTON_ACTIVE.setVisible(false);
    		NUKE_BUTTON_INACTIVE.setVisible(true);
    	}
    	
    	if(player.getAbility("Full Heal").getEnergyCost()<= player.getEnergy()) {
        	FULLHEAL_BUTTON_ACTIVE.setVisible(true);
        	FULLHEAL_BUTTON_INACTIVE.setVisible(false);
    	} else {
    		FULLHEAL_BUTTON_ACTIVE.setVisible(false);
    		FULLHEAL_BUTTON_INACTIVE.setVisible(true);
    	}

    	if(player.getAbility("Level Up").getEnergyCost()<= player.getEnergy()) {
        	LEVELUP_BUTTON_ACTIVE.setVisible(true);
        	LEVELUP_BUTTON_INACTIVE.setVisible(false);
    	} else {
    		LEVELUP_BUTTON_ACTIVE.setVisible(false);
    		LEVELUP_BUTTON_INACTIVE.setVisible(true);
    	}
    }
    
    private void initBackground() {
    	GImage background = BACKGROUND_IMAGE;
    	background.setLocation(0,25);
    	background.setSize(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT/3);
    	addObject(background);
     }
    
    private void initAbilities() {
    	NUKE_BUTTON_ACTIVE.setLocation(ABILITY_X,ABILITY_Y);
    	NUKE_BUTTON_INACTIVE.setLocation(ABILITY_X,ABILITY_Y);
    	FULLHEAL_BUTTON_ACTIVE.setLocation(ABILITY_X + ABILITY_X_OFFSET+NUKE_BUTTON_ACTIVE.getWidth(),ABILITY_Y);
    	FULLHEAL_BUTTON_INACTIVE.setLocation(ABILITY_X + ABILITY_X_OFFSET+NUKE_BUTTON_ACTIVE.getWidth(),ABILITY_Y);
    	LEVELUP_BUTTON_ACTIVE.setLocation(ABILITY_X + 2* ABILITY_X_OFFSET+NUKE_BUTTON_ACTIVE.getWidth()+LEVELUP_BUTTON_ACTIVE.getWidth(),
    										ABILITY_Y);
    	LEVELUP_BUTTON_INACTIVE.setLocation(ABILITY_X + 2* ABILITY_X_OFFSET+NUKE_BUTTON_ACTIVE.getWidth()+LEVELUP_BUTTON_ACTIVE.getWidth(),
    										ABILITY_Y);
    	
    	NUKE_BUTTON_ACTIVE.setVisible(false);
    	FULLHEAL_BUTTON_ACTIVE.setVisible(false);
    	LEVELUP_BUTTON_ACTIVE.setVisible(false);
    	
    	abilityButtons.put(NUKE_BUTTON_ACTIVE, "Nuke");
    	abilityButtons.put(FULLHEAL_BUTTON_ACTIVE, "Full Heal");
    	abilityButtons.put(LEVELUP_BUTTON_ACTIVE, "Level Up");
    	
    	addObject(NUKE_BUTTON_INACTIVE);
    	addObject(FULLHEAL_BUTTON_INACTIVE);
    	addObject(LEVELUP_BUTTON_INACTIVE);
    	addObject(NUKE_BUTTON_ACTIVE);
    	addObject(FULLHEAL_BUTTON_ACTIVE);
    	addObject(LEVELUP_BUTTON_ACTIVE);
    }
    
    private GLabel initName(double x, double y, Unit unit){
        GLabel l = new GLabel(unit.getName());
        l.setLocation(x-l.getWidth()/2,y);
        l.setColor(Color.ORANGE);
        addObject(l);
        return l;
    }
    
    public String useAbility(int x, int y) {
    	Set<GImage> abilities = abilityButtons.keySet();
    	for(GImage ability : abilities) {
    		if(ability.contains(x,y)) {
    			return player.getAbility(abilityButtons.get(ability)).use(player, enemy);
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
            info.attack = initAttack(x-info.animation.getWidth(),y-info.animation.getHeight()/3,unit);
            info.defense = initDefense(x-info.animation.getWidth(),y-info.animation.getHeight()/3+info.attack.getHeight(),unit);
        } else {
            info.attack = initAttack(x+info.animation.getWidth(),y-info.animation.getHeight()/3,unit);
            info.defense = initDefense(x+info.animation.getWidth(),y-info.animation.getHeight()/3+info.attack.getHeight(),unit);
        }
        info.name = initName(x,y-info.animation.getHeight()/2,unit);
    }
    
    private GLabel initAttack(double x, double y, Unit unit) {
    	GLabel attack = new GLabel("" + unit.getAttack());
    	attack.setLocation(x,y);
    	attack.setFont(HP_FONT);
    	attack.setColor(Color.WHITE);
    	
    	if(unit.getName().equals("Player")) {
    		PLAYER_ATTACK_SYMBOL.setLocation(x+attack.getWidth(),y-attack.getHeight());
        	addObject(PLAYER_ATTACK_SYMBOL);
    	}
    	else {
    		ENEMY_ATTACK_SYMBOL.setLocation(x-ENEMY_ATTACK_SYMBOL.getWidth(),y-attack.getHeight());
        	addObject(ENEMY_ATTACK_SYMBOL);
    	}
    	
    	addObject(attack);
    	return attack;
    }
    
    private GLabel initDefense(double x, double y, Unit unit) {
    	GLabel defense = new GLabel("" + unit.getDefense());
    	defense.setLocation(x,y);
    	defense.setFont(HP_FONT);
    	defense.setColor(Color.WHITE);
    	
    	if(unit.getName().equals("Player")) {
    		PLAYER_DEFENSE_SYMBOL.setLocation(x+defense.getWidth(),y-defense.getHeight());
        	addObject(PLAYER_DEFENSE_SYMBOL);
    	}
    	else {
    		ENEMY_DEFENSE_SYMBOL.setLocation(x-ENEMY_DEFENSE_SYMBOL.getWidth(),y-defense.getHeight());
        	addObject(ENEMY_DEFENSE_SYMBOL);
    	}
    	
    	addObject(defense);
    	return defense;
    }
}
