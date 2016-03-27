import java.io.File;

public abstract class Unit {

	private int hp;
	private int maxHp;
	private int attack;
	private int defense;
	private File image;

    public Unit(int hp, int maxHp, int attack, int defense, File image) { 
		this.hp = hp;
		this.maxHp = maxHp;
		this.attack = attack;
		this.defense = defense;
		this.image = image;
    }


    public void attack(Unit target){
        // TODO implement
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

	public void setImage(File image) {
    	this.image = image;
	}

	public File getImage() {    
		return image;
	}

}
