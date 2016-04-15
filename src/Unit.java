import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;
import java.awt.image.BufferedImage;
//mark's territory
enum AnimationState { 
	IDLE, ATTACK, DEATH;
}

enum UnitType {
    PLAYER, GOBLIN, GHOST, SKELETON, GIANT_SLIME, SKELETON_KING; // FIXME Feel free to change this to whatever

    public static UnitType randomEnemy(){
        Random r = new Random();
        int roll = r.nextInt(3);

        if(roll == 0) return UnitType.GOBLIN;
        if(roll == 1) return UnitType.GHOST;
        return UnitType.SKELETON;
    }
}
public abstract class Unit {

	private int hp;
	private int maxHp;
	private int attack;
	private int defense;
	private BufferedImage[] idleAnimation;
	private BufferedImage[] attackAnimation;
	private BufferedImage[] deathAnimation;
	private BufferedImage[] rangedAttack;
	
    private UnitType type;	
	private AnimationState state;

    public Unit(int hp, int maxHp, int attack, int defense, UnitType type) { 
		this.hp = hp;
		this.maxHp = maxHp;
		this.attack = attack;
		this.defense = defense;
        this.type = type;
		setAnimations();
		state = AnimationState.IDLE;
    }

    public void setAnimations(){
        switch(type){
            case PLAYER:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                deathAnimation = Animation.playerIdle;
                rangedAttack = Animation.playerRangedAttack;
                break;
            case GOBLIN: // TODO fill these in
            case GHOST:
            case SKELETON:
            case GIANT_SLIME:
            case SKELETON_KING:
                idleAnimation = Animation.enemy1Idle;
                attackAnimation = Animation.enemy1Attack;
                deathAnimation = Animation.enemy1Die;
            default:
        }
    }
    
    /**
     * Changes the animation based on the specified AnimationState.
     * @param state The AnimationState to change to
     */
    public void setCurrentAnimation(AnimationState state) {
    	this.state = state;
    }
    
    /**
     * Changes the animation to a given animation state after a specified time.
     * @param time The amount of time (in milliseconds) before the animation is changed
     * @param state The AnimationState that will be changed to
     */
    public void changeAnimationAfter(int time, AnimationState state){
        new Timer().schedule(new TimerTask(){
            public void run(){
                setCurrentAnimation(state);
            }
        }, time);
    }

    /**
     * Changes the animation to a given animation for a specified time and then to a second given animation.
     * @param time The amount of time (in milliseconds) before the animation is changed
     * @param state The AnimationState that will play for the duration
     * @param returnState The AnimationState that will be switched to after the duration is completed
     */
    public void playAnimationFor(int time, AnimationState state, AnimationState returnState){
        setCurrentAnimation(state);
        new Timer().schedule(new TimerTask(){
            public void run(){
                setCurrentAnimation(returnState);
            }
        }, time);
    }

    public BufferedImage[] getAnimation() {
    	switch(state) {
    		case IDLE:
    			return idleAnimation;
    		case ATTACK:
    			return attackAnimation;
    		case DEATH:
    			return deathAnimation;
    		default:
    			return idleAnimation;
    	}
    }
    
    public void attack(Unit target, int damage){
        int damageMitigation = target.defense;
        int totalDamage = (damage - damageMitigation);
        if(totalDamage > 0){
            target.hp -= totalDamage;
        }
    }

 
    public void heal(int amnt){
        if(amnt > 0){
            hp += amnt;
        }
        if(hp > maxHp){
            hp = maxHp;
        }
    }

    // Getters and Setters
	public void setHp(int hp) {
    	this.hp = hp;
	}

	public int getHp() {    
		return hp;
	}

	public void setMaxHp(int maxHp) {
    	this.maxHp = maxHp;
	}

	public int getMaxHp() {    
		return maxHp;
	}

	public void setAttack(int attack) {
    	this.attack = attack;
	}

	public int getAttack() {    
		return attack;
	}

	public void setDefense(int defense) {
    	this.defense = defense;
	}

	public int getDefense() {    
		return defense;
	}

    public UnitType getType(){
        return type;
    }

}
