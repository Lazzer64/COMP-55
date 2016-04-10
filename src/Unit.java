import java.awt.image.BufferedImage;
//mark's territory
enum AnimationState { 
	IDLE, ATTACK;
}

public abstract class Unit {

	private int hp;
	private int maxHp;
	private int attack;
	private int defense;
	private String name;
	private BufferedImage[] idleAnimation;
	private BufferedImage[] attackAnimation;
	
	
	private AnimationState state;

    public Unit(int hp, int maxHp, int attack, int defense, String name) { 
		this.hp = hp;
		this.maxHp = maxHp;
		this.attack = attack;
		this.defense = defense;
		this.name = name;
		setAnimations();
		state = AnimationState.IDLE;
    }

    public void setAnimations() {
    	if (this.name == "player") {
    		idleAnimation = Animation.playerIdle;
    		attackAnimation = Animation.playerAttack;
    	} else if (this.name == "enemy1") {
    		idleAnimation = Animation.playerIdle;
    		attackAnimation = Animation.playerIdle;
    	}
    }
    
    public void setCurrentAnimation(AnimationState state) {
    	this.state = state;
    }
    
    public BufferedImage[] getAnimation() {
    	switch(state) {
    		case IDLE:
    			return idleAnimation;
    		case ATTACK:
    			return attackAnimation;
    		default:
    			return idleAnimation;
    	}
    }
    
    public void attack(Unit target, int size){
        // TODO implement
 
     target.hp = target.hp-(size-target.defense);
    }
    public void attack(Unit target){
    
    	target.hp = target.hp-(attack-target.defense);
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
