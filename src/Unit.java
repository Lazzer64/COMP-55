import java.util.TimerTask;
import java.util.Timer;
import java.awt.Color;
import java.awt.image.BufferedImage;
//mark's territory

enum AnimationState { 
	IDLE, ATTACK, DEATH;
}

public abstract class Unit {

    private String name;
	private int hp;
	private int maxHp;
	private int attack;
	private int defense;
	
	protected AnimationState state;

    public Unit(String name, int hp, int attack, int defense) { 
        this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.attack = attack;
		this.defense = defense;
		state = AnimationState.IDLE;
    }

    public abstract BufferedImage[] getAnimation(); 
    public abstract BufferedImage[] getAttackAnimation(Color color);
    public abstract BufferedImage[] getAttackExplosionAnimation(Color color);

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
    
    public void attack(Unit target, int damage){
        int damageMitigation = target.defense;
        int totalDamage = (int)(damage*(1-(damageMitigation/(10+damageMitigation))));
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
