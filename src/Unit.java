import java.util.Random;
import java.awt.image.BufferedImage;
//mark's territory
enum AnimationState { 
	IDLE, ATTACK;
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
                break;
            case GOBLIN:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                break;
            case GHOST:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                break;
            case SKELETON:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                break;
            case GIANT_SLIME:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                break;
            case SKELETON_KING:
                idleAnimation = Animation.playerIdle;
                attackAnimation = Animation.playerAttack;
                break;
            default:
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

    public UnitType getType(){
        return type;
    }

}
