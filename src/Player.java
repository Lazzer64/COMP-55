import java.awt.image.BufferedImage;
import java.awt.Color;
//mark's territory
abstract public class Player extends Unit {

	protected int energy;
	protected int maxEnergy;
	protected Ability[] abilities;

    abstract public BufferedImage[] getAnimation();
	
    public Player(int hp, int attack, int defense, int energy) {
    	super("Player", hp, attack, defense);
    	this.energy = 1;
    	this.maxEnergy = energy;
    	setupAbilities();
    }

    public double getMultiplier(int combo) {
        // return the combo scaled by a set multiplier
        return 1 + combo * 0.5;
    }

    public void attack(Unit target, Match match, int combo) {
        // Calculate a magnitude of the attack from the match size and current combo
        int damage = (int) (match.size() * this.getMultiplier(combo));

        if (match.getType() == TileType.PINK) {
            // Heal for the magnitude
            this.heal(this.getMaxHp() * 2 * damage);
        }
        else if (match.getType() == TileType.YELLOW) {
            // Gain energy equal to the magnitude
            this.increaseEnergy(damage);
        }
        else {
            // Damage the target equal to the magnitude
            this.attack(target, damage);
        }
    }

    public void setupAbilities() {
    	this.abilities = new Ability[3];
    	this.abilities[0] = new DamageAbility("Nuke", 25, 75);
    	this.abilities[1] = new HealAbility("Full Heal", 50);
    	this.abilities[2] = new RaiseStatsAbility("Level Up", 75, 20, 3);
    }

    public Ability getAbility(String name) {
    	for(Ability a:abilities) {
    		if(a.getName().equals(name)) {
    			return a;
    		}
    	}
    	return null;
    }
    
    public Ability[] getAbilities() {
    	return abilities;
    }
    
    public int getNumAbilities() {
    	return abilities.length;
    }
    
    public BufferedImage[] getAttackAnimation(Color color){
        if(color.equals(TileType.getColor(TileType.RED))) return Animation.playerRedAttack;
        else if(color.equals(TileType.getColor(TileType.BLUE))) return Animation.playerBlueAttack;
        else if(color.equals(TileType.getColor(TileType.GREEN))) return Animation.playerGreenAttack;
        return Animation.playerRedAttack;
    }

    public BufferedImage[] getAttackExplosionAnimation(Color color) {
        if(color.equals(TileType.getColor(TileType.RED))) return Animation.playerRedExplosion;
        else if(color.equals(TileType.getColor(TileType.BLUE))) return Animation.playerBlueExplosion;
        else if(color.equals(TileType.getColor(TileType.GREEN))) return Animation.playerGreenExplosion;
        else if(color.equals(TileType.getColor(TileType.PINK))) return Animation.playerHealingEffect;
        return Animation.playerRedExplosion;
    }
    
    public void increaseEnergy(int addedEnergy) {
    	energy += addedEnergy;
    	if(energy > maxEnergy) energy = maxEnergy;
    }
    
    public void decreaseEnergy(int subtractedEnergy) {
    	energy -= subtractedEnergy;
    	if(energy < 0) energy = 0;
    }
    
    public int getEnergy() {
    	return this.energy;
    }
    
    public int getMaxEnergy() { 
    	return this.maxEnergy;
    }
}
